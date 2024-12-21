package com.example.demo.service;

import com.example.demo.entity.ScoreLine;

import java.util.List;

/**
 * 分数线 服务层
 *
 * @author Lhy
 * @since 2023/12/09 15:22
 */
public interface ScoreLineService {

    // 根据学生的文理科，总分，语文，数学和综合匹配分数线
    List<ScoreLine> matchScoreLine(Byte subject, Short totalScore);

    // 保存分数线
    ScoreLine saveScoreLine(ScoreLine scoreLine);

    // 保存分数线列表
    List<ScoreLine> saveScoreLines(List<ScoreLine> scoreLines);

    // 删除分数线
    void deleteScoreLineById(Integer id);

    // 删除某一年的分数线
    void deleteScoreLineByYear(Short year);

    // 更新学校分数线
    ScoreLine updateScoreLine(ScoreLine scoreLine);

    // 获取某一年的分数线
    List<ScoreLine> getScoreLineByYear(Short year);

    // 获取某一年某一批次的分数线
    List<ScoreLine> getScoreLineByYearAndBatch(Short year, Byte batch);

    // 分页获取所有学校分数线
    List<ScoreLine> getAllScoreLine();
}
