package com.example.deki.application.service;

import com.example.deki.application.commond.Response;
import com.example.deki.application.pojo.User;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {
    Response<User> addUser(@RequestBody User user);

    Response<User> login(User user);
}
