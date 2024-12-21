package com.example.demo.service.impl;

import com.example.demo.entity.Category;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.CategoryJpaRepository;
import com.example.demo.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("CategoryService")
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    private final CategoryJpaRepository categoryJpaRepository;

    public CategoryServiceImpl(CategoryJpaRepository categoryJpaRepository) {
        this.categoryJpaRepository = categoryJpaRepository;
    }

    @Override
    @CachePut(value = "category", key = "#newCategory.name")
    @Transactional
    public Category save(Category newCategory) {
        log.info("保存专业门类：{}", newCategory.getName());
        return categoryJpaRepository.save(newCategory);
    }

    @Override
    @CacheEvict(value = "category", allEntries = true)
    @Transactional
    public void deleteByName(String name) {
        log.info("删除专业门类：{}", name);
        categoryJpaRepository.deleteByName(name);
    }

    @Override
    @CachePut(value = "category")
    public List<Category> getAll( ) {
        log.info("获取所有专业门类");
        return categoryJpaRepository.findAll();
    }

    @Override
    @Cacheable(value = "category", key = "#name")
    public Category getByName(String name) {
        log.info("获取专业门类：{}", name);
        return categoryJpaRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("专业门类不存在"));
    }
}
