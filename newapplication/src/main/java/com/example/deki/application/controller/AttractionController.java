package com.example.deki.application.controller;

import com.example.deki.application.commond.Const;
import com.example.deki.application.commond.Response;
import com.example.deki.application.pojo.Attraction;
import com.example.deki.application.pojo.Ticket;
import com.example.deki.application.pojo.User;
import com.example.deki.application.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@EnableSwagger2
@RequestMapping("/ticket")
public class AttractionController {

    @Autowired
    private AttractionService attractionService;


    /**
     * 用户买票（输入景区名字和票数(成人票，儿童票，学生票)）（已登陆，session）
     * @param session
     * @param attraction
     * @return
     */
    @PostMapping("/buyTicket")
    @ResponseBody
    public Response<List<Ticket>> buyTicket(HttpSession session, @RequestBody Attraction attraction) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Response.createByErrorMessage("未登录");
        }
        return attractionService.buyTicket(attraction);
    }


    /**
     * admin修改票价
     * @param session
     * @param attraction (景区名字，新票价)
     * @return
     */
    @PostMapping("/updatePrice")
    public Response<Attraction> updatePrice(HttpSession session,
                                        @RequestBody Attraction attraction){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Response.createByErrorMessage("未登录");
        }
        if(user.getIfSuper() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        return attractionService.updatePrice(attraction);

    }

    /**
     * admin修改票数
     * @param session
     * @param attraction (景区名字，新票数)
     * @return
     */
    @PostMapping("/updateNum")
    public Response<Attraction> updateNum(HttpSession session, @RequestBody Attraction attraction){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Response.createByErrorMessage("未登录");
        }
        if(user.getIfSuper() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        return attractionService.updateNum(attraction);
    }

    /**
     *
     * @param session
     * @return
     */
    /**
     * admin增加景区
     * @param session
     * @param attraction (景区名字，票价，余票，描述)
     * @return
     */
    @PostMapping("/saveAttraction")
    public Response<Attraction> saveAttraction(HttpSession session,
                                        @RequestBody Attraction attraction){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return Response.createByErrorMessage("未登录");
        }
        if(user.getIfSuper() == 0){
            return Response.createByErrorMessage("无权限操作");
        }
        return attractionService.saveAttraction(attraction);
    }
}
