package com.example.demo.service.impl;

import com.example.demo.entity.School;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.SchoolJpaRepository;
import com.example.demo.service.SchoolService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学校服务接口实现
 *
 * @author Lhy
 * @since 2023/11/24 15:27
 */
@Service("SchoolService")
@Log4j2
public class SchoolServiceImpl implements SchoolService {

    private final SchoolJpaRepository schoolJpaRepository;

    public SchoolServiceImpl(SchoolJpaRepository schoolJpaRepository) {
        this.schoolJpaRepository = schoolJpaRepository;
    }

    @Override
    @CachePut(value = "school", key = "#result.code")
    @Transactional
    public School save(School newSchool) {
        log.info("保存学校：{}", newSchool.getName());
        return schoolJpaRepository.save(newSchool);
    }

    @Override
    public List<School> findAll() {
        log.info("查询所有学校");
        return schoolJpaRepository.findAll();
    }

    @Override
    @CacheEvict(value = "school", allEntries = true)
    @Transactional
    public void deleteByName(String name) {
        log.info("删除学校, 名称为: {}", name);
        schoolJpaRepository.deleteByName(name);
    }

    @Override
    @CacheEvict(value = "school", allEntries = true)
    @Transactional
    public void deleteByCode(String code) {
        log.info("删除学校, 代码为: {}", code);
        schoolJpaRepository.deleteByCode(code);
    }

    @Override
    @Cacheable(value = "school", key = "#result.code")
    public School getByName(String name) {
        log.info("查询学校, 名称为: {}", name);
        return schoolJpaRepository.findByName(name).orElseThrow(() -> new ItemNotFoundException("学校不存在"));
    }

    @Override
    @Cacheable(value = "school", key = "#result.code")
    public School getByCode(String code) {
        log.info("查询学校, 代码为: {}", code);
        return schoolJpaRepository.findByCode(code).orElseThrow(() -> new ItemNotFoundException("学校不存在"));
    }

    @Override
    public List<School> saveList(List<School> schoolList) {
        log.info("保存学校列表");
        return schoolJpaRepository.saveAll(schoolList);
    }
}
