package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 角色repository接口
 *
 * @author Lhy
 * @since 2023/10/12 20:12
 */
public interface RoleJpaRepository extends JpaRepository<Role, Integer> {

    // 通过角色名查询角色
    Optional<Role> findByName(String name);

    // 通过角色名删除角色
    void deleteByName(String name);
}
