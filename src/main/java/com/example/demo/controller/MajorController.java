package com.example.demo.controller;

import com.example.demo.entity.Major;
import com.example.demo.service.MajorService;
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
 * 专业 控制层
 *
 * @author Lhy
 * @since 2023/12/05 21:46
 */
@RestController
@RequestMapping("/major")
public class MajorController {

    private final MajorService majorService;

    public MajorController(@Qualifier("MajorService") MajorService majorService) {
        this.majorService = majorService;
    }

    // 保存专业
    @PreAuthorize("hasPermission(#major.id, 'major:add')")
    @PostMapping
    public Result<Major> save(@RequestBody @Valid @NotNull Major major) {
        major = majorService.save(major);
        return Result.success("添加成功", major);
    }

    // 通过名称删除专业
    @PreAuthorize("hasPermission(#name, 'major:delete')")
    @DeleteMapping("/name/{name}")
    public Result<Void> deleteByName(@PathVariable @NotBlank String name) {
        majorService.deleteByName(name);
        return Result.success("删除成功");
    }

    // 通过代码删除专业
    @PreAuthorize("hasPermission(#code, 'major:delete')")
    @DeleteMapping("/code/{code}")
    public Result<Void> deleteByCode(@PathVariable @NotBlank String code) {
        majorService.deleteByCode(code);
        return Result.success("删除成功");
    }

    // 查询所有专业
    @PermitAll
    @GetMapping("/all")
    public Result<List<Major>> getAll() {
        List<Major> majors = majorService.getAll();
        return Result.success("查询成功", majors);
    }

    // 通过名称查询专业
    @PermitAll
    @GetMapping("/name/{name}")
    public Result<Major> getByName(@PathVariable @NotBlank String name) {
        Major major = majorService.getByName(name);
        return Result.success("查询成功", major);
    }

    // 通过代码查询专业
    @PermitAll
    @GetMapping("/code/{code}")
    public Result<Major> getByCode(@PathVariable @NotBlank String code) {
        Major major = majorService.getByCode(code);
        return Result.success("查询成功", major);
    }

    @PreAuthorize("hasPermission(#major.id, 'major:update')")
    @PutMapping
    public Result<Major> update(@RequestBody @Valid @NotNull Major major) {
        major = majorService.save(major);
        return Result.success("更新成功", major);
    }
}
