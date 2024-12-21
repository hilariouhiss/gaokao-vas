package com.example.demo.service;

import com.example.demo.entity.Category;

import java.util.List;

/**
 * 专业门类 服务接口
 *
 * @author Lhy
 * @since 2023/11/21 16:24
 */
public interface CategoryService {

    // 添加专业门类
    Category save(Category category);

    // 根据名称删除专业门类
    void deleteByName(String name);

    // 获取所有专业门类
    List<Category> getAll();

    // 根据名称查询专业门类
    Category getByName(String name);
}
