package com.hmifo.springsecuritydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmifo.springsecuritydemo.config.DBUserDetailsManager;
import com.hmifo.springsecuritydemo.entity.User;
import com.hmifo.springsecuritydemo.mapper.UserMapper;
import com.hmifo.springsecuritydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private DBUserDetailsManager dbUserDetailsManager;

    @Override
    public void saveUserDetails(User user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withDefaultPasswordEncoder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        dbUserDetailsManager.createUser(userDetails);
    }
}