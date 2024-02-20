package com.hmifo.springsecuritydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

/**
 * ClassName:MyLogoutSuccessHandler
 * Package:com.hmifo.springsecuritydemo.config
 * Description:
 *
 * @Author 施伟
 * @Create 2024/2/20 22:49
 * @Version 1.0
 */
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap result = new HashMap<>();
        result.put("code", 0);//成功
        result.put("message", "注销成功");
        // 将结构对象准换成json字符串
        String json = JSON.toJSONString(result);
        // 返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}