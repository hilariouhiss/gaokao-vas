package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * user服务接口
 *
 * @author Lhy
 * @since 2023/10/17 14:49
 */
public interface UserService extends UserDetailsService {
    // 用户注册
    User register(User user);

    // 更新用户
    User update(User user);

    // 根据用户名查询用户
    User getUserByUsername(String username);

    User getUserById(Integer id);

    // 查询所有用户
    List<User> getAllUsers();

    // 删除用户
    void deleteUser(String username);
}
