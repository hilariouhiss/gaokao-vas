package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.service.RoleService;
import com.example.demo.util.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 *
 * @author Lhy
 * @since 2023/10/24 21:34
 */
@RestController
@RequestMapping("/role")
@PreAuthorize("hasRole('SYSTEM')")
public class RoleController {
    private final RoleService roleService;

    public RoleController(@Qualifier("RoleService")RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/all")
    public Result<List<Role>> selectAll() {
        List<Role> roles = roleService.getAllRoles();
        return Result.success("查询成功", roles);
    }

    //以角色名查询角色
    @GetMapping("/{name}")
    public Result<Role> selectByRoleName(@PathVariable @NotBlank String name) {
        Role role = roleService.getRoleByName(name);
        return Result.success("查询成功", role);
    }

    @PostMapping
    public Result<Role> save(@RequestBody @Valid @NotNull Role role) {
        role = roleService.saveRole(role);
        return Result.success("添加成功", role);
    }

    //删除角色
    @DeleteMapping("/{name}")
    public Result<Void> deleteByRoleName(@PathVariable String name) {
        roleService.deleteRole(name);
        return Result.success("删除成功");
    }

    //更改角色
    @PutMapping
    public Result<Role> updateRole(@RequestBody @Valid Role role) {
        role = roleService.saveRole(role);
        return Result.success("更新成功", role);
    }

}
