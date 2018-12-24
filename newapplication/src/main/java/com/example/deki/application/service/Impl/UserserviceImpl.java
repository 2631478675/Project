package com.example.deki.application.service.Impl;

import com.example.deki.application.commond.Response;
import com.example.deki.application.jpa.UserJpa;
import com.example.deki.application.pojo.User;
import com.example.deki.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl implements UserService {
    @Autowired
    private UserJpa userJpa;

    @Override
    public Response<User> addUser(User user) {
        //用户需要输入名字，年纪，如果未输入是否是学生，则默认不是
        if(user.getAge() != null && user.getName() !=null && user.getPassword() != null){
            if(user.getIfStudent() == null){ //不是学生
                user.setIfStudent(0);
            }
            user.setIfSuper(0);
            userJpa.save(user);
        }
        else {
            return Response.createByErrorMessage("字段不规范");
        }
        return Response.createBySuccessMessage("注册成功");
    }

    @Override
    public Response<User> login(User user) {
        if(user.getName() != null && user.getPassword() != null){
            User user1 = userJpa.findByName(user.getName());
            if(user.getPassword() .equals(user1.getPassword())){
                return Response.createBySuccess("登录成功",user1);
            }
            return  Response.createByErrorMessage("密码错误");
        }
        return Response.createByErrorMessage("登录失败");
    }


}
