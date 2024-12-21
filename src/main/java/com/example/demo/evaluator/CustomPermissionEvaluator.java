package com.example.demo.evaluator;

import com.example.demo.entity.Role;
import com.example.demo.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 * 用于定义如何根据用户的权限来进行鉴权
 *
 * @author Lhy
 * @since 2023/12/08 23:44
 */
@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final UserService userService;

    public CustomPermissionEvaluator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // 获取当前用户的用户名
        String username = authentication.getName();
        // 获取当前用户的角色
        Role role = userService.getUserByUsername(username).getRole();
        // 如果当前用户是系统管理员，则拥有所有权限
        if ("ROLE_SYSTEM".equals(role.getName())) {
            return true;
        }
        // 若当前用户不是系统管理员，则需要根据当前用户的权限来判断是否有权限
        // 获取当前用户的权限集合
        Set<String> permissions = role.getPermissions();
        // 判断当前用户的权限集合中是否包含传入的权限
        return permissions.contains(permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
