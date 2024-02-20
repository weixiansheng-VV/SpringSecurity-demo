package com.hmifo.springsecuritydemo.config;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;

/**
 * ClassName:MyAuthenticationEntryPoint
 * Package:com.hmifo.springsecuritydemo.config
 * Description:
 *
 * @Author 施伟
 * @Create 2024/2/20 22:54
 * @Version 1.0
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String localizedMessage = "需要登录";// authException.getLocalizedMessage();
        HashMap result = new HashMap<>();
        result.put("code", -1);//失败
        result.put("message", localizedMessage);
        // 将结构对象准换成json字符串
        String json = JSON.toJSONString(result);
        // 返回json数据到前端
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
    }
}
