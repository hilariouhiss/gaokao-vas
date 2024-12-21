package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.RoleJpaRepository;
import com.example.demo.repository.UserJpaRepository;
import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * user服务实现类
 *
 * @author Lhy
 * @since 2023/10/17 14:49
 */
@Service("UserService")
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;

    private final RoleJpaRepository roleJpaRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserJpaRepository userJpaRepository, PasswordEncoder passwordEncoder,
                           RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    @CachePut(value = "user", key = "#result.username")
    @Transactional
    public User register(User newUser) {
        // 判断用户名是否已存在
        userJpaRepository.findByUsername(newUser.getUsername())
                .ifPresent(u -> {
                    log.info("{}已被占用", u.getUsername());
                    throw new IllegalArgumentException("用户名已被占用");
                });

        // 密码加密
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        // 设置用户角色默认为ROLE_USER
        Role role = roleJpaRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ItemNotFoundException("角色不存在"));
        newUser.setRole(role);
        // 插入用户
        newUser = userJpaRepository.save(newUser);
        log.info("用户: {} 注册成功", newUser.getUsername());
        return newUser;
    }

    @Override
    @CachePut(value = "user", key = "#result.username")
    @Transactional
    public User update(User newUser) {
        // 判断用户是否存在
        User oldUser = userJpaRepository.findById(newUser.getId())
                .orElseThrow(() -> new ItemNotFoundException("用户不存在"));

        // 若密码为null，则不更新密码
        if (newUser.getPassword() == null) {
            newUser.setPassword(oldUser.getPassword());
        } else {
            // 密码加密
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }

        if (newUser.getRole() == null) {
            newUser.setRole(oldUser.getRole());
        } else {
            Role role = roleJpaRepository.findByName(newUser.getRole().getName())
                    .orElseThrow(() -> new ItemNotFoundException("角色不存在"));
            newUser.setRole(role);
        }

        newUser = userJpaRepository.save(newUser);
        log.info("用户: {} 更新成功", newUser.getUsername());

        return newUser;
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public User getUserByUsername(String username) {
        log.info("查询用户: {} by name", username);
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("用户不存在"));
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public User getUserById(Integer id) {
        log.info("查询用户: {} by id", id);
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("用户不存在"));
    }

    @Override
    @Cacheable(value = "user")
    public List<User> getAllUsers() {
        log.info("查询所有用户");
        return userJpaRepository.findAll();
    }

    @Override
    @CacheEvict(value = "user", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String username) {
        userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("用户不存在"));

        userJpaRepository.deleteByUsername(username);
        log.info("用户: {} 删除成功", username);
    }

    @Override
    @Cacheable(value = "user", key = "#username")
    public UserDetails loadUserByUsername(String username) {
        User user = userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new ItemNotFoundException("用户不存在"));

        log.info("用户: {} 加载成功", username);
        return user;
    }
}
