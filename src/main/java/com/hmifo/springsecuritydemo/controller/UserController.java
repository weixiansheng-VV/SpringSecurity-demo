package com.hmifo.springsecuritydemo.controller;

import com.hmifo.springsecuritydemo.entity.User;
import com.hmifo.springsecuritydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/list")
    public List<User> getList() {
        return userService.list();
    }

    @PostMapping("/add")
    public void add(@RequestBody User user) {
        userService.saveUserDetails(user);
    }
}