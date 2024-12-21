package com.example.demo.entity;

import java.time.LocalDateTime;

/**
 * 登录日志 实体类
 *
 * @author Lhy
 * @since 2023/12/16 17:19
 */
public record Login(LocalDateTime loginTime, String userId) {
}
