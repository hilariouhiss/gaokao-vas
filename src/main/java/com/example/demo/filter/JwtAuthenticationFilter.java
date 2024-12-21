package com.example.demo.filter;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

/**
 * 从请求中提取 JWT 并进行身份验证
 *
 * @author Lhy
 * @since 2023/11/10 16:40
 */
@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        if (authorization != null && !authorization.isBlank()) {
            if (JwtUtil.validateToken(authorization)) {
                Claims claims = JwtUtil.parseToken(authorization);
                if (claims == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                Integer id = Integer.valueOf(claims.getSubject());
                User user = userService.getUserById(id);

                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("用户 {} 登录", id);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(
                        objectMapper.writeValueAsString(Result.error(401, "请重新登录"))
                );
            }
        }
        filterChain.doFilter(request, response);
    }
}