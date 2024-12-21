package com.example.demo.service;

import com.example.demo.entity.Major;
import java.util.List;

/**
 * 具体专业 服务接口
 *
 * @author Lhy
 * @since 2023/11/24 15:15
 */
public interface MajorService {

    // 添加专业
    Major save(Major major);

    // 根据名称删除专业
    void deleteByName(String name);

    // 根据代码删除专业
    void deleteByCode(String code);

    // 获取所有专业
    List<Major> getAll();

    // 根据名称查询专业
    Major getByName(String name);

    // 根据代码查询专业
    Major getByCode(String code);
}
