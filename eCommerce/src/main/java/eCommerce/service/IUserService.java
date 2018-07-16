package eCommerce.service;

import eCommerce.common.Response;
import eCommerce.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpSession;

public interface IUserService {
    Response<User> login(User user);

    Response<String> logon(User user);


    Response<String> selectQuestionByUsername(User user);

    Response<String> judgeAnswerIsRight(User user);

    Response<String> resetPassword(User user,String token);

    Response<String> resetPasswordAfterLogin(String password ,String newPassword,String oldPassword);

    Response<User> updateInfo(User user);
 }
