package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户控制器
 *
 * @author Lhy
 * @since 2023/10/10 17:39
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasPermission(null, 'user:get')")
    @GetMapping("/all")
    public Result<List<User>> selectAll() {
        List<User> userList = userService.getAllUsers();
        return Result.success("查询成功", userList);
    }


    @PreAuthorize("hasPermission(#username, 'user:get')")
    @GetMapping("/{username}")
    public Result<User> selectByUsername(@PathVariable @NotBlank String username) {
        User userDto = userService.getUserByUsername(username);
        return Result.success("查询成功", userDto);
    }

    @PreAuthorize("hasPermission(#username, 'user:delete')")
    @DeleteMapping("/{username}")
    public Result<Void> deleteByUsername(@PathVariable @NotBlank String username) {
        userService.deleteUser(username);
        return Result.success("删除成功");
    }

    @PreAuthorize("hasPermission(#user.id, 'user:update')")
    @PutMapping
    public Result<User> update(@RequestBody @Valid @NotNull User user) {
        user = userService.update(user);
        return Result.success("更新成功", user);
    }
}
