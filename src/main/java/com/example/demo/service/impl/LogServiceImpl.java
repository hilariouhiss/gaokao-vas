package com.example.demo.service.impl;

import com.example.demo.entity.Login;
import com.example.demo.service.LogService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service("LogService")
@Log4j2
public class LogServiceImpl implements LogService {

    @Override
    public List<Login> getAllLogin() {
        String tomcatBaseDir = System.getProperty("catalina.base");
        String loginPath = tomcatBaseDir + "/logs/login.log";
        List<Login> loginList;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}).*用户 (\\d+) 登录$");
        try {
            List<String> lines = Files.readAllLines(Paths.get(loginPath));
            Collections.reverse(lines);
            loginList = lines.stream().map(line -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    LocalDateTime loginTime = LocalDateTime.parse(matcher.group(1), formatter);
                    String userId = matcher.group(2);
                    return new Login(loginTime, userId);
                } else {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        } catch (IOException e) {
            log.error("读取 Login 日志失败", e);
            throw new RuntimeException("读取 Login 日志失败", e);
        }
        return loginList;
    }

    @Override
    public Login getLoginByUserId(String userId) {
        String loginPath = "logs/login.log";
        Login login;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        Pattern pattern = Pattern.compile("^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2},\\d{3}).*用户 (\\d+) 登录$");
        try {
            List<String> lines = Files.readAllLines(Paths.get(loginPath));
            Collections.reverse(lines);
            login = lines.stream().map(line -> {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    LocalDateTime loginTime = LocalDateTime.parse(matcher.group(1), formatter);
                    String logUserId = matcher.group(2);
                    return new Login(loginTime, logUserId);
                } else {
                    return null;
                }
            }).filter(login1 -> login1 != null && login1.userId().equals(userId)).findFirst().orElse(null);
        } catch (IOException e) {
            log.error("读取 Login 日志失败", e);
            throw new RuntimeException("读取 Login 日志失败", e);
        }
        return login;
    }
}
