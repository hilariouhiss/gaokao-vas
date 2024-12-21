package com.example.demo.service.impl;

import com.example.demo.entity.Major;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.MajorJpaRepository;
import com.example.demo.service.MajorService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 具体专业 服务实现
 *
 * @author Lhy
 * @since 2023/11/24 15:45
 */
@Service("MajorService")
@Log4j2
public class MajorServiceImpl implements MajorService {


    private final MajorJpaRepository majorJpaRepository;

    public MajorServiceImpl(MajorJpaRepository majorJpaRepository) {
        this.majorJpaRepository = majorJpaRepository;
    }

    @Override
    @CachePut(value = "major", key = "#result.name")
    @Transactional
    public Major save(Major newMajor) {
        log.info("保存专业：{}", newMajor.getName());
        return majorJpaRepository.save(newMajor);
    }

    @Override
    @CacheEvict(value = "major", allEntries = true)
    @Transactional
    public void deleteByName(String name) {
        log.info("删除专业：{}", name);
        majorJpaRepository.deleteByName(name);
    }

    @Override
    @CacheEvict(value = "major", allEntries = true)
    @Transactional
    public void deleteByCode(String code) {
        log.info("删除专业：{}", code);
        majorJpaRepository.deleteByCode(code);
    }

    @Override
    @Cacheable(value = "major")
    public List<Major> getAll() {
        log.info("获取所有专业");
        return majorJpaRepository.findAll();
    }

    @Override
    @Cacheable(value = "major", key = "#name")
    public Major getByName(String name) {
        log.info("获取专业：{}", name);
        return majorJpaRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("专业不存在"));
    }

    @Override
    @Cacheable(value = "major", key = "#code")
    public Major getByCode(String code) {
        log.info("获取专业：{}", code);
        return majorJpaRepository.findByCode(code)
                .orElseThrow(() -> new ItemNotFoundException("专业不存在"));
    }
}
