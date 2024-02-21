package com.hmifo.springsecuritydemo.controller;

import com.hmifo.springsecuritydemo.entity.User;
import com.hmifo.springsecuritydemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    public UserService userService;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN') and authentication.name == 'admin'")
    public List<User> getList() {
        return userService.list();
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('USER')")
    @PreAuthorize("hasAuthority('USER_ADD')")
    public void add(@RequestBody User user) {
        userService.saveUserDetails(user);
    }
}