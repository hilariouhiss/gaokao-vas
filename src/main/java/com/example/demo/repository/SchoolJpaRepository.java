package com.example.demo.repository;

import com.example.demo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 学校数据持久层
 *
 * @author Lhy
 * @since 2023/11/21 14:26
 */
public interface SchoolJpaRepository extends JpaRepository<School, Integer> {

    void deleteByName(String name);

    Optional<School> findByName(String name);

    void deleteByCode(String code);

    Optional<School> findByCode(String code);
}
