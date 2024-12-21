package com.example.demo.repository;

import com.example.demo.entity.ScoreLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 分数线 持久层
 *
 * @author Lhy
 * @since 2023/12/09 15:30
 */
public interface ScoreLineJpaRepository extends JpaRepository<ScoreLine, Integer> {

    Optional<List<ScoreLine>> findBySubjectAndTotalScoreBetween(Byte subject, Short totalScore, Short totalScore2);

    void deleteByYear(Short year);

    Optional<List<ScoreLine>> findByYear(Short year);

    Optional<List<ScoreLine>> findByYearAndBatch(Short year, Byte batch);
}
