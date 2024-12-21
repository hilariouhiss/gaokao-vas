package com.example.demo.controller;

import com.example.demo.entity.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 权限控制器
 *
 * @author Lhy
 * @since 2023/10/24 21:39
 */
@RestController
@RequestMapping("/permission")
@PreAuthorize("hasRole('SYSTEM')")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(@Qualifier("PermissionService") PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // 查询所有权限
    @GetMapping("/all")
    public Result<List<Permission>> selectAll() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return Result.success("查询成功", permissions);
    }

    // 以权限名查询权限
    @GetMapping("/name/{name}")
    public Result<Permission> selectByPermissionName(@PathVariable @NotBlank String name) {
        Permission permission = permissionService.getPermissionByName(name);
        return Result.success("查询成功", permission);
    }

    // 删除权限
    @DeleteMapping("/{name}")
    public Result<Void> deleteByPermissionName(@PathVariable @NotBlank String name) {
        permissionService.deletePermission(name);
        return Result.success("删除成功");
    }

    // 更改权限
    @PutMapping("/update")
    public Result<Permission> updatePermission(@RequestBody @Valid Permission permission) {
        permission = permissionService.save(permission);
        return Result.success("更新成功", permission);
    }
}
