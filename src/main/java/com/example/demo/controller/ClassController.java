package com.example.demo.controller;

import com.example.demo.entity.Class;
import com.example.demo.service.ClassService;
import com.example.demo.util.Result;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业类 控制器层
 *
 * @author Lhy
 * @since 2023/12/05 19:44
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    private final ClassService classService;

    public ClassController(@Qualifier("ClassService") ClassService classService) {
        this.classService = classService;
    }

    // 添加专业类
    @PreAuthorize("hasPermission(#clazz.id, 'class:add')")
    @PostMapping
    public Result<Class> save(@RequestBody @Valid Class clazz) {
        clazz = classService.save(clazz);
        return Result.success("添加成功",clazz);
    }

    // 根据名称删除专业类
    @PreAuthorize("hasPermission(#name, 'class:delete')")
    @DeleteMapping("/{name}")
    public Result<Void> deleteByName(@PathVariable @NotBlank String name) {
        classService.deleteByName(name);
        return Result.success("删除成功");
    }

    // 获取所有专业类
    @PermitAll
    @GetMapping("/all")
    public Result<List<Class>> getAll() {
        List<Class> classes = classService.getAll();
        return Result.success("查询成功",classes);
    }

    // 根据名称查询专业类
    @PermitAll
    @GetMapping("/{name}")
    public Result<Class> getByName(@PathVariable @NotBlank String name) {
        Class clazz = classService.getByName(name);
        return Result.success("查询成功",clazz);
    }

    // 更新专业类
    @PreAuthorize("hasPermission(#clazz.id, 'class:update')")
    @PutMapping
    public Result<Class> update(@RequestBody @Valid Class clazz) {
        clazz = classService.save(clazz);
        return Result.success("更新成功",clazz);
    }
}
