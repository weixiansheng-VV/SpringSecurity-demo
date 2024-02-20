package com.hmifo.springsecuritydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

/**
 * ClassName:MyAuthenticationSuccessHandler
 * Package:com.hmifo.springsecuritydemo.config
 * Description:
 *
 * @Author 施伟
 * @Create 2024/2/20 22:32
 * @Version 1.0
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();// 获取用户身份信息
//        Object credentials = authentication.getCredentials();// 获取用户凭证信息
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();// 获取用户权限信息


        HashMap result = new HashMap<>();
        result.put("code", 0);//成功
        result.put("message", "登录成功");
        result.put("data", principal);
        // 将结构对象准换成json字符串
        String json = JSON.toJSONString(result);
        // 返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
