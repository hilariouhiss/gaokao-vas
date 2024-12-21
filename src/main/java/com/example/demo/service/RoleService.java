package com.example.demo.service;

import com.example.demo.entity.Role;
import java.util.List;

/**
 * 角色服务层
 *
 * @author Lhy
 * @since 2023/10/24 21:36
 */
public interface RoleService {

    // 查找所有角色
    List<Role> getAllRoles();

    // 根据角色名查找角色
    Role getRoleByName(String name);

    // 新增或更新角色
    Role saveRole(Role role);

    // 根据角色名删除角色
    void deleteRole(String name);
}
