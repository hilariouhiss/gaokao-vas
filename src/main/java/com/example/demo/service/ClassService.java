package com.example.demo.service;

import com.example.demo.entity.Class;

import java.util.List;

/**
 * 专业类 服务接口
 *
 * @author Lhy
 * @since 2023/11/21 16:29
 */
public interface ClassService {

    // 添加专业类
    Class save(Class clazz);

    // 根据名称删除专业类
    void deleteByName(String name);

    // 获取所有专业类
    List<Class> getAll();

    // 根据名称查询专业类
    Class getByName(String name);
}
