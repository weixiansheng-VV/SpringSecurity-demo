package com.hmifo.springsecuritydemo.config;

import com.alibaba.fastjson2.JSON;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author 54220
 */
@Configuration
@EnableWebSecurity// Spring项目总需要添加此注解，SpringBoot项目中不需要
@EnableMethodSecurity // 开启基于方法的授权
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //authorizeRequests()：开启授权保护
        //anyRequest()：对所有请求开启授权保护
        //authenticated()：已认证请求会自动被授权
        http.authorizeRequests(
                authorize -> authorize
/*                      基于用户的权限配置
                        //具有USER_LIST权限的用户可以访问/user/list
                        .requestMatchers("/user/list").hasAuthority("USER_LIST")
                        //具有USER_ADD权限的用户可以访问/user/add
                        .requestMatchers("/user/add").hasAuthority("USER_ADD")*/
//                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
        );
//                .formLogin(withDefaults());//表单授权方式
//                .httpBasic(withDefaults());//基本授权方式

        http.formLogin(form -> {
            form.loginPage("/login").permitAll()//无需授权即可访问当前页面
                    .usernameParameter("myusername") //配置自定义的表单用户名参数，默认值是username
                    .passwordParameter("mypassword") //配置自定义的表单密码参数，默认值是password
                    .failureUrl("/login?failure") // 校验失败时跳转的地址，默认值是error
                    .successHandler(new MyAuthenticationSuccessHandler())// 认证成功时的处理
                    .failureHandler(new MyAuthenticationFailureHandler())// 认证失败时的处理
            ;
        });

        // 注销成功时的处理
        http.logout(logout -> {
            logout.logoutSuccessHandler(new MyLogoutSuccessHandler());
        });

        // 请求未认证的接口
        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(new MyAuthenticationEntryPoint());
//            exception.accessDeniedHandler(new MyAccessDeniedHandler());
            //匿名内部类
            /*exception.accessDeniedHandler(new AccessDeniedHandler() {
                @Override
                public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                    //创建结果对象
                    HashMap result = new HashMap();
                    result.put("code", -1);
                    result.put("message", "没有权限");

                    //转换成json字符串
                    String json = JSON.toJSONString(result);

                    //返回响应
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().println(json);
                }
            });*/
            //lambda表达式
            exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                //创建结果对象
                HashMap result = new HashMap();
                result.put("code", -1);
                result.put("message", "没有权限");

                //转换成json字符串
                String json = JSON.toJSONString(result);

                //返回响应
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
            });
        });

        //会话管理
        http.sessionManagement(session -> {
            session.maximumSessions(1).expiredSessionStrategy(new MySessionInformationExpiredStrategy());
        });

        //跨域
        http.cors(withDefaults());

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
