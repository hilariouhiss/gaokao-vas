package com.example.demo.service;

import com.example.demo.entity.Permission;

import java.util.List;

/**
 * 权限服务层
 *
 * @author Lhy
 * @since 2023/10/24 21:37
 */
public interface PermissionService {
    /**
     * 查找所有权限
     *
     * @return List<PermissionDto>
     * @author 刘海阳
     * @param:
     * @since 2023/11/13 19:26
     */
    List<Permission> getAllPermissions();


    // 根据权限名查找权限
    Permission getPermissionByName(String name);


    // 新增或更新权限
    Permission save(Permission permission);

    // 根据权限名删除权限
    void deletePermission(String name);
}
