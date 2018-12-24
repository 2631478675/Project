package com.example.deki.application.controller;

import com.example.deki.application.commond.Response;
import com.example.deki.application.pojo.User;
import com.example.deki.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpSession;

import static com.example.deki.application.commond.Const.CURRENT_USER;

@RestController
@EnableSwagger2
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * 新用户注册
     *
     * @param user(name,password,age)
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Response<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    /**
     * 用户登录
     *
     * @param user（name，password）
     *  @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Response<User> login(@RequestBody User user, HttpSession session) {
        Response<User> newUser = userService.login(user);
        System.out.println(newUser.getData());
        session.setAttribute(CURRENT_USER,newUser.getData());
        return newUser;
    }


    /**
     * 用户退出登录
     * @param session
     * @return
     */
    @GetMapping("exit")
    public Response<String> exit(HttpSession session){
        //根据map.key来删除session
        session.removeAttribute(CURRENT_USER);
        return Response.createBySuccessMessage("退出登录");
    }


}
