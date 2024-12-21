package com.example.demo.controller;

import com.example.demo.entity.Login;
import com.example.demo.service.LogService;
import com.example.demo.util.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {


    private final LogService logService;

    LogController(@Qualifier("LogService") LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/all")
    public Result<List<Login>> getLogins() {
        List<Login> loginList = logService.getAllLogin();
        return Result.success("查询登录日志成功", loginList);
    }

    @GetMapping("/userId")
    public Result<Login> getLoginByUserId(@RequestParam String userId) {
        Login login = logService.getLoginByUserId(userId);
        return Result.success("查询登录日志成功", login);
    }
}
