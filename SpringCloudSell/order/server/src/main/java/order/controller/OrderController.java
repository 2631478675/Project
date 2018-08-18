package order.controller;


import lombok.extern.slf4j.Slf4j;
import order.VO.Response;
import order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 1. 参数检验
     * 2. 查询商品信息(调用商品服务)
     * 3. 计算总价
     * 4. 扣库存(调用商品服务)
     * 5. 订单入库
     */
    @PostMapping("/create")
    public Response<Map<String, String>> create() {
//        @Valid OrderForm orderForm,
//        BindingResult bindingResult
//        if (bindingResult.hasErrors()){
//            log.error("【创建订单】参数不正确, orderForm={}", orderForm);
//            throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),
//                    bindingResult.getFieldError().getDefaultMessage());
//        }
//        // orderForm -> orderDTO
//        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
//        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
//            log.error("【创建订单】购物车信息为空");
//            throw new OrderException(ResultEnum.CART_EMPTY);
//        }
//
//        OrderDTO result = orderService.create(orderDTO);
//
//        Map<String, String> map = new HashMap<>();
//        map.put("orderId", result.getOrderId());
//        return ResultVOUtil.success(map);
//    }
        return null;
    }
}
