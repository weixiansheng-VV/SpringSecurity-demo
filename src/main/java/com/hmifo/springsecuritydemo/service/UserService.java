package com.hmifo.springsecuritydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmifo.springsecuritydemo.entity.User;

public interface UserService extends IService<User> {
    void saveUserDetails(User user);
}