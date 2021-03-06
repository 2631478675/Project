package eCommerce.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import eCommerce.common.Const;
import eCommerce.common.Response;
import eCommerce.dao.*;
import eCommerce.pojo.*;
import eCommerce.service.IOrderService;
import eCommerce.util.BigDecimalUtil;
import eCommerce.util.FTPUtil;
import eCommerce.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("IOrderService")
public class OrderServiceImpl implements IOrderService {

    private final static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static AlipayTradeService tradeService;

    @Autowired
    private payInfoMapper infoMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    static {

        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public Response pay(Long orderNo, String path, Integer id) {
        //返回给前端的是订单号和二维码的URL，所以新建一个map对象作为返回
        Map<String ,String> resultMap = Maps.newHashMap();

        //根据订单号和用户拿到用户订单
        Order order = orderMapper.selectByUserIdAndOrderNo(id,orderNo);

        if(order == null){
            return Response.createByErrorMessage("用户没有该订单");
        }
        resultMap.put("orderNo",String.valueOf(order.getOrderNo()));



        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = order.getOrderNo().toString();


        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = new StringBuilder().append("扫码支付,订单号:").append(outTradeNo).toString();


        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = order.getPayment().toString();


        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";



        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();


        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");




        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        //将订单详情中的东西填充到商品明细中去
        List<OrderItem> orderItemList = orderItemMapper.getByOrderNoUserId(orderNo,id);
        for(OrderItem orderItem : orderItemList){
            GoodsDetail goods = GoodsDetail.newInstance(orderItem.getProductId().toString(), orderItem.getProductName(),
                    BigDecimalUtil.mul(orderItem.getCurrentUnitPrice().doubleValue(),new Double(100).doubleValue()).longValue(),
                    orderItem.getQuantity());
            goodsDetailList.add(goods);
        }

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(PropertiesUtil.getProperty("alipay.callback.url"))//沙箱测试的回调地址
                .setGoodsDetailList(goodsDetailList);


        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                logger.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);


                //创建生成二维码的文件夹，如果有就直接生成至目标文件夹，如果没有新建文件夹并赋予写权限
                File folder = new File(path);
                if(!folder.exists()){
                    folder.setWritable(true);
                    folder.mkdirs();
                }

                // 需要修改为运行机器上的路径
                //细节细节细节
                //生成的二维码的路径
                String qrPath = String.format(path+"/qr-%s.png",response.getOutTradeNo());
                //二维码的名字
                String qrFileName = String.format("qr-%s.png",response.getOutTradeNo());
                //无关紧要的一行，根据二维码的路径生成二维码
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);

                File targetFile = new File(path,qrFileName);
                try {
                    FTPUtil.uploadFile(Lists.newArrayList(targetFile));
                } catch (IOException e) {
                    logger.error("上传二维码异常",e);
                }

                logger.info("qrPath:" + qrPath);
                //在ftp服务器上支付二维码的地址为qrUrl
                String qrUrl = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFile.getName();

                resultMap.put("qrUrl",qrUrl);
                return Response.createBySuccess(resultMap);

            case FAILED:
                logger.error("支付宝预下单失败!!!");
                return Response.createByErrorMessage("支付宝预下单失败!!!");

            case UNKNOWN:
                logger.error("系统异常，预下单状态未知!!!");
                return Response.createByErrorMessage("系统异常，预下单状态未知!!!");

            default:
                logger.error("不支持的交易状态，交易返回异常!!!");
                return Response.createByErrorMessage("不支持的交易状态，交易返回异常!!!");
        }
    }

    /**
     * 第五步：需要严格按照如下描述校验通知数据的正确性。
     *商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，并判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），同时需要校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），上述有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。
     *在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。
     *在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
     * @param stringStringMap
     * @return
     */
    @Override
    public Response callBack(Map<String, String> stringStringMap) {
        Long orderNo = Long.parseLong(stringStringMap.get("out_trade_no"));
        String tradeNo = stringStringMap.get("trade_no");
        //此交易状态来自支付宝的动态生成
        String status = stringStringMap.get("trade_status");
        //根据订单号查看相关订单是否存在
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order ==null){
            return Response.createByErrorMessage("订单为空，回调忽略");
        }
        //已支付，重复通知
        if(order.getStatus()>= Const.OrderStatusEnum.PAID.getCode()){
            return Response.createByErrorMessage("订单已支付,回调忽略");
        }
        //当交易通知为TRADE_SUCCESS时，买家付款成功，后端将status置成已支付，并更新相应的order
        if(Const.Callback.TRADE_STATUS_TRADE_SUCCESS.equals(status)){
            order.setStatus(Const.OrderStatusEnum.PAID.getCode());
            orderMapper.updateByPrimaryKeySelective(order);
        }
        //订单信息，不懂这有啥用？
        payInfo payInfo = new payInfo();
        payInfo.setUserId(order.getUserId());
        payInfo.setOrderNo(order.getOrderNo());
        //支付平台
        payInfo.setPayPlatform(Const.payPlatformEnum.ALIPAY.getCode());
        payInfo.setPlatformNumber(tradeNo);
        payInfo.setPlatformStatus(status);

        infoMapper.insert(payInfo);
        return Response.createBySuccess();
    }

    @Override
    public Response createOrder(Integer id, Integer shippingId) {
        //从购物车中获取所有商品
        List<Cart> carts = cartMapper.selectAllCartByUserId(id);

        //查看仓库中有关购物车中商品的状态
        Response response = this.getCartOrderItem(id,carts);
        if(!response.isSuccess()){
            return response;
        }
        List<OrderItem> orderItems = (List<OrderItem>) response.getData();

        //计算总价
//        BigDecimal payment = this.getOrderTotalPrice
    }



    /**
     * 商店中的商品的信息：orderItem
     * @param id
     * @param carts
     * @return
     */
    private Response getCartOrderItem(Integer id, List<Cart> carts) {
        List<OrderItem> orderItems = new ArrayList<>();
        if(CollectionUtils.isEmpty(carts)){
            return Response.createByErrorMessage("购物车为空");
        }
        //每件商品的数量和商店中产品的状态
        for(Cart cartItem : carts){

            //orderItem和product的区别product为仓库中的商品包括数量等等
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if(Const.ProductStatusEnum.ON_SALE.getCode() != product.getStatus()){
                return Response.createByErrorMessage("产品"+product.getName()+"不是在线售卖状态");
            }
            if(cartItem.getQuantity()>product.getStock()){
                return Response.createByErrorMessage("产品"+product.getName()+"库存不足");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setUserId(id);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
            orderItems.add(orderItem);
        }
        return Response.createBySuccess(orderItems);
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            logger.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                logger.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            logger.info("body:" + response.getBody());
        }
    }
}
