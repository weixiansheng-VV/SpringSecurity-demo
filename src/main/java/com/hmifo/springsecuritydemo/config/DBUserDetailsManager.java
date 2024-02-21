package com.hmifo.springsecuritydemo.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hmifo.springsecuritydemo.entity.User;
import com.hmifo.springsecuritydemo.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

/**
 * @author 54220
 */
@Component
public class DBUserDetailsManager implements UserDetailsManager, UserDetailsPasswordService {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }

    /**
     * 向数据库中插入新的用户信息
     *
     * @param userDetails
     */
    @Override
    public void createUser(UserDetails userDetails) {
        User user = new User();
        user.setUsername(userDetails.getUsername());
        user.setPassword(userDetails.getPassword());
        user.setEnabled(true);
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    /**
     * 从数据库中获取用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
/*            Collection<GrantedAuthority> authorities = new ArrayList<>();
//            authorities.add(() -> "USER_LIST");
//            authorities.add(() -> "USER_ADD");
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEnabled(),
                    true, //用户账号是否未过期
                    true, //用户凭证是否未过期
                    true, //用户是否未被锁定
                    authorities //权限列表
            );*/
            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .disabled(!user.getEnabled()) //账号是否过期
                    .credentialsExpired(false) //凭证是否过期
                    .accountExpired(false) //账号是否被锁定
                    .roles("ADMIN")
                    .authorities("USER_ADD","USER_UPDATE")
                    .build();
        }
    }
}
