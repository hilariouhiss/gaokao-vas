package com.example.demo.repository;

import com.example.demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 专业门类 数据持久层
 *
 * @author Lhy
 * @since 2023/11/21 14:29
 */
public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {

    void deleteByName(String name);

    Optional<Category> findByName(String name);
}
