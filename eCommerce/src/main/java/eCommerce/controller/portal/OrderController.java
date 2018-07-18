package eCommerce.controller.portal;

import eCommerce.common.Const;
import eCommerce.common.Response;
import eCommerce.dao.OrderMapper;
import eCommerce.pojo.Order;
import eCommerce.pojo.User;
import eCommerce.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/order/")
public class OrderController {

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
     * 支付宝的回调将所有的东西都放到了request中去，需要我们自己去取
     * @param request
     * @return
     */
    @PostMapping("callback")
    @ResponseBody
    public Object callBack(HttpServletRequest request){
        Map map = request.getParameterMap();
        //将map中的值取出来，其中的values为数组：to do
        //将values数组中元素放入一个字符串中，并且各元素用","分割
        //验证回调的正确性，是不是支付宝的，同时还要避免重复通知的出现：reference ：https://docs.open.alipay.com/194/103296/

        return null;
    }
}
