package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.RoleJpaRepository;
import com.example.demo.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色服务实现层
 *
 * @author Lhy
 * @since 2023/10/24 21:36
 */
@Service("RoleService")
@Log4j2
public class RoleServiceImpl implements RoleService {

    private final RoleJpaRepository roleJpaRepository;

    public RoleServiceImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        log.info("查找所有角色");
        return roleJpaRepository.findAll();
    }

    @Override
    @Cacheable(value = "role", key = "#result.id")
    public Role getRoleByName(String name) {
        log.info("查找角色: {}", name);
        return roleJpaRepository.findByName(name)
                .orElseThrow(() -> new ItemNotFoundException("角色不存在"));
    }

    @Override
    @CachePut(value = "role", key = "#result.id")
    @Transactional
    public Role saveRole(Role newRole) {
        log.info("保存角色：{}", newRole.getName());
        return roleJpaRepository.save(newRole);
    }

    @Override
    @CacheEvict(value = "role", allEntries = true)
    @Transactional
    public void deleteRole(String name) {
        log.info("删除角色：{}", name);
        roleJpaRepository.deleteByName(name);
    }
}
