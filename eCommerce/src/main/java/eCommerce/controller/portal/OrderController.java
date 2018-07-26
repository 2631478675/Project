package eCommerce.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import eCommerce.common.Const;
import eCommerce.common.Response;
import eCommerce.common.ResponseCode;
import eCommerce.pojo.Order;
import eCommerce.pojo.Shipping;
import eCommerce.pojo.User;
import eCommerce.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {

    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;


    /**
     * 将订单编号orderNo传递过来，返回支付二维码地址
     * 前端将其展示成付款的二维码
     * @param session
     * @param request
     * @return
     */
    @PostMapping("pay")
    @ResponseBody
    public Response pay(HttpSession session, HttpServletRequest request, @RequestBody Order order){
        //判断用户是否登录
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return Response.createByErrorMessage("未登录");
        }
        if(order.getOrderNo() == null){
            return Response.createByErrorMessage("订单号为空");
        }
        String path = request.getSession().getServletContext().getRealPath("upload");
        return orderService.pay(order.getOrderNo(),path,user.getId());
    }


    /**
     * 支付宝的回调将所有的东西都放到了request中去，需要我们自己去取，这是支付宝去调用的（本机使用内网穿透，或者将这个项目部署到服务器上）
     * @param request
     * @return
     */
    @PostMapping("callback")
    @ResponseBody
    public Object callBack(HttpServletRequest request){
        Map<String,String> stringStringMap = new HashMap<String,String>();
        Map map = request.getParameterMap();
        //将map中的值取出来，其中的values为数组：to do
        //将values数组中元素放入一个字符串中，并且各元素用","分割
        //验证回调的正确性，是不是支付宝的，同时还要避免重复通知的出现：reference ：https://docs.open.alipay.com/194/103296/
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            String key = (String) iterator.next();
            String[] values = (String[]) map.get(key);
            String valueStr = "";
            for(int i=0;i<values.length;i++){
                valueStr = ( i == values.length-1)?(valueStr + values[i]):(valueStr+values[i]);
            }
            stringStringMap.put(key,valueStr);
        }
        //第一步： 在通知返回参数列表中，除去sign、sign_type两个参数外，凡是通知返回回来的参数皆是待验签的参数。:官方已经除去了sign
        stringStringMap.remove("sign_type");
        //第二步： 将剩下参数进行url_decode, 然后进行字典排序，组成字符串，得到待签名字符串：(官方已做)
        //第三步： 将签名参数（sign）使用base64解码为字节码串。（官方已做）
        //第四步： 使用RSA的验签方法，通过签名字符串、签名参数（经过base64解码）及支付宝公钥验证签名。
        try {
            //Map<String, String> params, String publicKey, String charset, String signType
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(stringStringMap, Configs.getAlipayPublicKey(),"utf-8", Configs.getSignType());
            if(!alipayRSACheckedV2){
                return Response.createByErrorMessage("非法请求，验证不通过");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常",e);
            e.printStackTrace();
        }
        //第五步：需要严格按照如下描述校验通知数据的正确性。
        //to do

        Response response = orderService.callBack(stringStringMap);

        if(response.isSuccess()){
//            return "success"; 使用常量
            return Const.Callback.RESPONSE_SUCCESS;
        }
        return Const.Callback.RESPONSE_FAILED;
    }














    //创建订单，参数为收货地址
    @RequestMapping("create")
    @ResponseBody
    public Response createOrder(HttpSession session, @RequestBody Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return Response.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return orderService.createOrder(user.getId(),shipping.getId());
    }
}
