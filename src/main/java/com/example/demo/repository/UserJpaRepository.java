package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 用户Dao接口
 *
 * @author Lhy
 * @since 2023/10/10 17:37
 */
public interface UserJpaRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    void deleteByUsername(String username);
}
