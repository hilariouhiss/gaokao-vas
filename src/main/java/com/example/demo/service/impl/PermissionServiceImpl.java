package com.example.demo.service.impl;

import com.example.demo.entity.Permission;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.PermissionJpaRepository;
import com.example.demo.service.PermissionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 权限服务实现层
 *
 * @author Lhy
 * @since 2023/10/24 21:38
 */
@Service("PermissionService")
@Log4j2
public class PermissionServiceImpl implements PermissionService {


    private final PermissionJpaRepository permissionJpaRepository;

    public PermissionServiceImpl(PermissionJpaRepository permissionJpaRepository) {
        this.permissionJpaRepository = permissionJpaRepository;
    }

    @Override
    @Cacheable(value = "permission")
    public List<Permission> getAllPermissions() {
        log.info("查找所有权限");
        return permissionJpaRepository.findAll();
    }

    @Override
    @Cacheable(value = "permission", key = "#name")
    public Permission getPermissionByName(String name) {
        log.info("查找权限: {}", name);
        return permissionJpaRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("权限不存在"));
    }

    @Override
    @Transactional
    public Permission save(Permission newPermission) {
        log.info("保存权限：{}", newPermission.getName());
        return permissionJpaRepository.save(newPermission);
    }

    @Override
    @CacheEvict(value = "permission", allEntries = true)
    @Transactional
    public void deletePermission(String name) {
        log.info("删除权限：{}", name);
        permissionJpaRepository.deleteByName(name);
    }
}
