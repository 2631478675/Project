package com.example.deki.application.jpa;

import com.example.deki.application.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User,Long>{
    User findByName(String name);
}
