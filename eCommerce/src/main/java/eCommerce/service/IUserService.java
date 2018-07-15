package eCommerce.service;

import eCommerce.common.Response;
import eCommerce.pojo.User;

public interface IUserService {
    Response<User> login(User user);

    Response<String> logon(User user);


    Response<String> selectQuestionByUsername(User user);

    Response<String> judgeAnswerIsRight(User user);

    Response<String> resetPassword(User user);
 }
