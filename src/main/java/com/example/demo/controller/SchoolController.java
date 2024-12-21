package com.example.demo.controller;

import com.example.demo.entity.School;
import com.example.demo.service.SchoolService;
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
 * 学校 控制层
 *
 * @author Lhy
 * @since 2023/12/05 19:22
 */
@RestController
@RequestMapping("/school")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(@Qualifier("SchoolService") SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // 查询所有学校
    @PermitAll
    @GetMapping("/all")
    public Result<List<School>> findAll() {
        List<School> schools = schoolService.findAll();
        return Result.success("查询成功", schools);
    }

    // 保存学校
    @PreAuthorize("hasPermission(#school.id, 'school:add')")
    @PostMapping
    public Result<School> save(@RequestBody @Valid @NotNull School school) {
        school = schoolService.save(school);
        return Result.success("保存成功", school);
    }

    // 通过名称删除学校
    @PreAuthorize("hasPermission(#name, 'school:delete')")
    @DeleteMapping("/name/{name}")
    public Result<Void> deleteByName(@PathVariable @NotBlank String name) {
        schoolService.deleteByName(name);
        return Result.success("删除成功");
    }

    // 通过代码删除学校
    @PreAuthorize("hasPermission(#code, 'school:delete')")
    @DeleteMapping("/code/{code}")
    public Result<Void> deleteByCode(@PathVariable @NotBlank String code) {
        schoolService.deleteByCode(code);
        return Result.success("删除成功");
    }

    // 通过名称查询学校
    @PermitAll
    @GetMapping("/name/{name}")
    public Result<School> selectByName(@PathVariable @NotBlank String name) {
        School school = schoolService.getByName(name);
        return Result.success("查询成功", school);
    }

    // 通过代码查询学校
    @PermitAll
    @GetMapping("/code/{code}")
    public Result<School> selectByCode(@PathVariable @NotBlank String code) {
        School school = schoolService.getByCode(code);
        return Result.success("查询成功", school);
    }

    // 更新学校信息
    @PreAuthorize("hasPermission(#school, 'school:update')")
    @PutMapping
    public Result<School> update(@RequestBody @Valid @NotNull School school) {
        school = schoolService.save(school);
        return Result.success("更新成功", school);
    }
}
