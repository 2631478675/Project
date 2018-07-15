package eCommerce.controller;

import eCommerce.common.Response;
import eCommerce.pojo.User;
import eCommerce.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static eCommerce.common.Const.CURRENT_USER;

@RestController
@RequestMapping("/user/")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    //后台管理员登录接口,之后做JSR3.0校验
    @PostMapping("login")
    @ResponseBody
    public Response<User> login(@RequestBody  User user, HttpSession session){
        Response<User> userSelect = userService.login(user);
        //用户退出，根据session退出
        if(userSelect.isSuccess()){
            session.setAttribute(CURRENT_USER,userSelect.getData());
        }
        return userSelect;
    }

    //后台管理员退出登录
    @GetMapping("exit")
    public Response<String> exit(HttpSession session){
        //根据map.key来删除session
        session.removeAttribute(CURRENT_USER);
        return Response.createBySuccessMessage("退出登录");
    }

    //用户注册
    @PostMapping("logon")
    @ResponseBody
    public Response<String> logon(@RequestBody User user){

        return userService.logon(user);
    }

    //用户忘记密码，通过回答问题来重置密码
        //第一步，根据用户名，返回相关问题
        //第二步，拿到用户的答案，判断正确是否
        //第三部，通过用户传递的密码来update密码

    //第一步，根据用户名返回相关问题
    @PostMapping("forgetPassword")
    @ResponseBody
    public Response<String> forgetPassword(@RequestBody User user){
        return userService.selectQuestionByUsername(user);
    }


    //第二步，根据用户的答案，判断正确与否
    @PostMapping("checkAnswer")
    @ResponseBody
    public Response<String> checkAnswer(@RequestBody User user){
        return userService.judgeAnswerIsRight(user);
    }

    @PostMapping("resetPassword")
    @ResponseBody
    //第三步，通过新密码重置密码
    public Response<String> resetPassword(@RequestBody User user){
        return userService.resetPassword(user);
    }
}
