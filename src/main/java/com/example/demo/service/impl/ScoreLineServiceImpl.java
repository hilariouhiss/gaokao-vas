package com.example.demo.service.impl;

import com.example.demo.entity.ScoreLine;
import com.example.demo.exception.ItemNotFoundException;
import com.example.demo.repository.ScoreLineJpaRepository;
import com.example.demo.service.ScoreLineService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 分数线 服务实现
 *
 * @author Lhy
 * @since 2023/12/09 15:29
 */
@Service("ScoreLineService")
@Log4j2
public class ScoreLineServiceImpl implements ScoreLineService {

    private final ScoreLineJpaRepository scoreLineJpaRepository;

    public ScoreLineServiceImpl(ScoreLineJpaRepository scoreLineJpaRepository) {
        this.scoreLineJpaRepository = scoreLineJpaRepository;
    }


    @Override
    public List<ScoreLine> matchScoreLine(Byte subject, Short totalScore) {
        String subjectStr = subject == 0 ? "文科" : "理科";
        log.info("匹配分数线，文理：{}，总分：{}", subjectStr, totalScore);
        // 查找对应subject的total_score在上下10分的分数线

        return scoreLineJpaRepository.findBySubjectAndTotalScoreBetween(subject,
                        (short) (totalScore - 10), (short) (totalScore + 10))
                .orElseThrow(() -> new ItemNotFoundException("没有找到对应的分数线"));
    }

    @Override
    @Transactional
    public ScoreLine saveScoreLine(ScoreLine scoreLine) {
        log.info("保存分数线：{}", scoreLine.getId());
        return scoreLineJpaRepository.save(scoreLine);
    }

    @Override
    @Transactional
    public List<ScoreLine> saveScoreLines(List<ScoreLine> scoreLines) {
        log.info("保存分数线列表");
        return scoreLineJpaRepository.saveAll(scoreLines);
    }

    @Override
    @Transactional
    public void deleteScoreLineById(Integer id) {
        log.info("删除分数线：{}", id);
        scoreLineJpaRepository.deleteById(id);
    }

    @Override
    @CacheEvict(value = "scoreLine", allEntries = true)
    @Transactional
    public void deleteScoreLineByYear(Short year) {
        log.info("删除{}年的分数线", year);
        scoreLineJpaRepository.deleteByYear(year);
    }

    @Override
    @Transactional
    public ScoreLine updateScoreLine(ScoreLine scoreLine) {
        log.info("更新分数线：{}", scoreLine.getId());
        return scoreLineJpaRepository.save(scoreLine);
    }

    @Override
    public List<ScoreLine> getScoreLineByYear(Short year) {
        log.info("获取{}年的分数线", year);
        return scoreLineJpaRepository.findByYear(year)
                .orElseThrow(() -> new ItemNotFoundException("没有找到对应的分数线"));
    }

    @Override
    public List<ScoreLine> getScoreLineByYearAndBatch(Short year, Byte batch) {
        log.info("获取{}年{}批次的分数线", year, batch);
        return scoreLineJpaRepository.findByYearAndBatch(year, batch)
                .orElseThrow(() -> new ItemNotFoundException("没有找到对应的分数线"));
    }

    @Override
    @Cacheable(value = "scoreLine")
    public List<ScoreLine> getAllScoreLine() {
        log.info("获取所有分数线");
        return scoreLineJpaRepository.findAll();
    }
}
