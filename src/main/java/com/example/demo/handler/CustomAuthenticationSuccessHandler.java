package com.example.demo.handler;

import com.example.demo.entity.User;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义用户认证成功处理器
 *
 * @author Lhy
 * @since 2023/11/02 12:15
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        User user = (User) authentication.getPrincipal();
        String accessToken;
        try {
            accessToken = JwtUtil.generateTokens(user.getId());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Result<Void> errorResult = Result.error(500, "无法生成 JWT：" + e.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(errorResult));
            return;
        }
        // 将 JWT 添加到响应头
        response.addHeader("Authorization", accessToken);
        Result<User> result = Result.success("登录成功", user);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}