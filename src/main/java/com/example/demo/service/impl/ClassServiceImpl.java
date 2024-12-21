package com.example.demo.service.impl;

import com.example.demo.entity.Class;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.ClassJpaRepository;
import com.example.demo.service.ClassService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ClassService")
@Log4j2
public class ClassServiceImpl implements ClassService {

    private final ClassJpaRepository classJpaRepository;

    public ClassServiceImpl(ClassJpaRepository classJpaRepository) {
        this.classJpaRepository = classJpaRepository;
    }

    @Override
    @Transactional
    public Class save(Class newClass) {
        log.info("保存专业类：{}", newClass.getName());
        return classJpaRepository.save(newClass);
    }

    @Override
    @CacheEvict(value = "class", allEntries = true)
    @Transactional
    public void deleteByName(String name) {
        log.info("删除专业类：{}", name);
        classJpaRepository.deleteByName(name);
    }

    @Override
    @Cacheable(value = "class")
    public List<Class> getAll() {
        log.info("获取所有专业类");
        return classJpaRepository.findAll();
    }

    @Override
    @Cacheable(value = "class", key = "#name")
    public Class getByName(String name) {
        log.info("获取专业类：{}", name);
        return classJpaRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("专业类不存在"));
    }
}
