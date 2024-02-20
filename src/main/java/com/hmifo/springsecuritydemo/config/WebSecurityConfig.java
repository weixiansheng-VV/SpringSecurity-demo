package com.hmifo.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author 54220
 */
@Configuration
@EnableWebSecurity//Spring项目总需要添加此注解，SpringBoot项目中不需要
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //authorizeRequests()：开启授权保护
        //anyRequest()：对所有请求开启授权保护
        //authenticated()：已认证请求会自动被授权
        http.authorizeRequests(
                        authorize -> authorize
                                .anyRequest()
                                .authenticated()
                )
//                .formLogin(withDefaults());//表单授权方式
                .formLogin(form -> {
                    form.loginPage("/login").permitAll();//无需授权即可访问当前页面
                });
//                .httpBasic(withDefaults());//基本授权方式
        //关闭csrf攻击防御
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

/*
    @Bean
    public UserDetailsService userDetailsService() {
        // 创建基于内存的用户信息管理器
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        //使用manager管理serDetails对象
        manager.createUser(
                //创建UserDetails对象
                User.withDefaultPasswordEncoder()
                        .username("user")  //自定义用户名
                        .password("password") //自定义密码
                        .roles("USER") //自定义角色
                        .build()
        );
        return manager;
    }
*/

/*    @Bean
    public UserDetailsService userDetailsService() {
        // 创建基于数据库的用户信息管理器
        return new DBUserDetailsManager();
    }*/
}
