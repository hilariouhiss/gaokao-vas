package com.example.demo.service;

import com.example.demo.entity.School;
import java.util.List;

/**
 * 学校 服务接口
 *
 * @author Lhy
 * @since 2023/11/21 16:21
 */
public interface SchoolService {

    // 添加学校
    School save(School school);

    // 添加学校列表
    List<School> saveList(List<School> schoolList);

    // 获取所有学校
    List<School> findAll();

    // 通过名称删除学校
    void deleteByName(String name);

    // 通过code删除学校
    void deleteByCode(String code);

    // 通过名称获取单个学校
    School getByName(String name);

    // 通过code获取单个学校
    School getByCode(String code);
}