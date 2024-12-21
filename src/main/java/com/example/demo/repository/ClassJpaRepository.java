package com.example.demo.repository;

import com.example.demo.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 专业类 数据持久层
 *
 * @author Lhy
 * @since 2023/11/21 14:30
 */
public interface ClassJpaRepository extends JpaRepository<Class, Integer> {

    void deleteByName(String name);

    Optional<Class> findByName(String name);
}
