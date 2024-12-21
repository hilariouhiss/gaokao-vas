package com.example.demo.repository;

import com.example.demo.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 权限
 *
 * @author Lhy
 * @since 2023/10/24 21:33
 */
@Repository
public interface PermissionJpaRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(String name);

    void deleteByName(String name);
}
