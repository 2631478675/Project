package order.service.Impl;

import order.dto.OrderDTO;
import order.repository.OrderDetailRepository;
import order.repository.OrderMasterRepository;
import order.service.OrderService;
import order.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;
//
//    @Autowired
//    private ProductClient productClient;
    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //创建一个唯一的ID
        String orderId = KeyUtil.genUniqueKey();

        //查询商品信息(调用商品服务)
//        List<String> productIdList = orderDTO.getOrderDetailList().stream()
//                .map(OrderDetail::getProductId)
//                .collect(Collectors.toList());
//        List<ProductInfoOutput> productInfoList = productClient.listForOrder(productIdList);
//
//        //计算总价
//        BigDecimal orderAmout = new BigDecimal(BigInteger.ZERO);
//        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
//            for (ProductInfoOutput productInfo: productInfoList) {
//                if (productInfo.getProductId().equals(orderDetail.getProductId())) {
//                    //单价*数量
//                    orderAmout = productInfo.getProductPrice()
//                            .multiply(new BigDecimal(orderDetail.getProductQuantity()))
//                            .add(orderAmout);
//                    BeanUtils.copyProperties(productInfo, orderDetail);
//                    orderDetail.setOrderId(orderId);
//                    orderDetail.setDetailId(KeyUtil.genUniqueKey());
//                    //订单详情入库
//                    orderDetailRepository.save(orderDetail);
//                }
//            }
//        }
//
//        //扣库存(调用商品服务)
//        List<DecreaseStockInput> decreaseStockInputList = orderDTO.getOrderDetailList().stream()
//                .map(e -> new DecreaseStockInput(e.getProductId(), e.getProductQuantity()))
//                .collect(Collectors.toList());
//        productClient.decreaseStock(decreaseStockInputList);
//
//        //订单入库
//        OrderMaster orderMaster = new OrderMaster();
//        orderDTO.setOrderId(orderId);
//        BeanUtils.copyProperties(orderDTO, orderMaster);
//        orderMaster.setOrderAmount(orderAmout);
//        orderMaster.setOrderStatus(1);
//        orderMaster.setPayStatus(1);
//        orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

}
