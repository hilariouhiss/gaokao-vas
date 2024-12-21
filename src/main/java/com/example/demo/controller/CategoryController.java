package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import com.example.demo.util.Result;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业门类 控制层
 *
 * @author Lhy
 * @since 2023/12/05 19:34
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController(@Qualifier("CategoryService") CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // 添加专业门类
    @PreAuthorize("hasPermission(#category.id, 'category:add')")
    @PostMapping
    public Result<Category> save(@RequestBody @Valid @NotNull Category category) {
        category = categoryService.save(category);
        return Result.success("添加成功", category);
    }

    // 根据名称删除专业门类
    @PreAuthorize("hasPermission(#name,'category:delete')")
    @DeleteMapping("/{name}")
    public Result<Void> deleteByName(@PathVariable @NotBlank String name) {
        categoryService.deleteByName(name);
        return Result.success("删除成功");
    }

    // 获取所有专业门类
    @PermitAll
    @GetMapping("/all")
    public Result<List<Category>> getAll() {
        List<Category> categories = categoryService.getAll();
        return Result.success("查询成功", categories);
    }

    // 根据名称查询专业门类
    @PermitAll
    @GetMapping("/{name}")
    public Result<Category> getByName(@PathVariable @NotBlank String name) {
        Category category = categoryService.getByName(name);
        return Result.success("查询成功", category);
    }

    // 更新专业门类
    @PreAuthorize("hasPermission(#category.id,'category:update')")
    @PutMapping
    public Result<Category> update(@RequestBody @Valid @NotNull Category category) {
        category = categoryService.save(category);
        return Result.success("更新成功", category);
    }
}
