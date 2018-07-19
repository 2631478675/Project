package eCommerce.controller.portal;

import com.github.pagehelper.PageInfo;
import eCommerce.common.Const;
import eCommerce.common.Response;
import eCommerce.common.ResponseCode;
import eCommerce.pojo.Shipping;
import eCommerce.pojo.User;
import eCommerce.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping/")
public class ShippingController {
    @Autowired
    private IShippingService shippingService;


    @RequestMapping("add")
    @ResponseBody
    public Response add(HttpSession session, @RequestBody Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return Response.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.add(user.getId(),shipping);
    }


    @RequestMapping("delete")
    @ResponseBody
    public Response delete(HttpSession session,@RequestBody Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return Response.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.delete(user.getId(),shipping.getId());
    }

    @RequestMapping("update")
    @ResponseBody
    public Response update(HttpSession session,@RequestBody Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return Response.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.update(user.getId(),shipping);
    }


    @RequestMapping("select")
    @ResponseBody
    public Response<Shipping> select(HttpSession session,@RequestBody Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return Response.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.select(user.getId(),shipping.getId());
    }


    @RequestMapping("list")
    @ResponseBody
    public Response<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return Response.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return shippingService.list(user.getId(),pageNum,pageSize);
    }


}
