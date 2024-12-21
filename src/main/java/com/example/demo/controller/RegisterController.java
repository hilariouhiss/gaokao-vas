package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册控制器
 *
 * @author Lhy
 * @since 2023/10/26 19:39
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @PermitAll
    @PostMapping
    public Result<User> register(@RequestBody @Valid @NotNull User user) {
        // 调用注册服务并记录返回结果
        user = userService.register(user);
        // 返回成功结果
        return Result.success("注册成功", user);
    }
}
