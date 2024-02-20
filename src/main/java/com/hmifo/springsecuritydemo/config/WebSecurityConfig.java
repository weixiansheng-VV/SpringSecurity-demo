package com.hmifo.springsecuritydemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

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

        http.logout(logout -> {
            logout.logoutSuccessHandler(new MyLogoutSuccessHandler());// 注销成功时的处理
        });

        http.exceptionHandling(exception -> {
            exception.authenticationEntryPoint(new MyAuthenticationEntryPoint());
        });

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
