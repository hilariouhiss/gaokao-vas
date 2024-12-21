package com.example.demo.repository;

import com.example.demo.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 专业 持久层
 *
 * @author Lhy
 * @since 2023/11/24 15:13
 */
public interface MajorJpaRepository extends JpaRepository<Major, Integer> {

    void deleteByName(String name);

    Optional<Major> findByName(String name);

    void deleteByCode(String code);

    Optional<Major> findByCode(String code);
}
