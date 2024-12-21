package com.example.demo.controller;

import com.example.demo.entity.ScoreLine;
import com.example.demo.service.ScoreLineService;
import com.example.demo.util.Result;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分数线 控制层
 *
 * @author Lhy
 * @since 2023/12/10 21:27
 */
@RestController
@RequestMapping("/scoreLine")
public class ScoreLineConroller {
    private final ScoreLineService scoreLineService;

    public ScoreLineConroller(ScoreLineService scoreLineService) {this.scoreLineService = scoreLineService;}

    @PermitAll
    @GetMapping("/match")
    public Result<List<ScoreLine>> match(@RequestParam Byte subject, @RequestParam Short totalScore) {
        List<ScoreLine> scoreLines = scoreLineService.matchScoreLine(subject, totalScore);
        return Result.success("获取成功", scoreLines);
    }

    @PreAuthorize("hasPermission(#scoreLine.id, 'score_line:add')")
    @PostMapping("/add")
    public Result<ScoreLine> addScoreLine(@RequestBody ScoreLine scoreLine) {
        ScoreLine scoreLine1 = scoreLineService.saveScoreLine(scoreLine);
        return Result.success("添加成功", scoreLine1);
    }

    @PreAuthorize("hasPermission(#scoreLines.size(), 'score_line:add')")
    @PostMapping("/addAll")
    public Result<List<ScoreLine>> addScoreLines(@RequestBody List<ScoreLine> scoreLines) {
        List<ScoreLine> scoreLines1 = scoreLineService.saveScoreLines(scoreLines);
        return Result.success("添加成功", scoreLines1);
    }

    @PreAuthorize("hasPermission(#id, 'score_line:delete')")
    @DeleteMapping("/delete")
    public Result<String> deleteScoreLine(@RequestParam Integer id) {
        scoreLineService.deleteScoreLineById(id);
        return Result.success("删除成功");
    }

    @PreAuthorize("hasPermission(#year, 'score_line:delete')")
    @DeleteMapping("/delete/year")
    public Result<String> deleteScoreLines(@RequestParam Short year) {
        scoreLineService.deleteScoreLineByYear(year);
        return Result.success("删除成功");
    }

    @PreAuthorize("hasPermission(#scoreLine.id, 'score_line:update')")
    @PutMapping
    public Result<ScoreLine> updateScoreLine(@RequestBody ScoreLine scoreLine) {
        ScoreLine scoreLine1 = scoreLineService.updateScoreLine(scoreLine);
        return Result.success("更新成功", scoreLine1);
    }

    @PermitAll
    @GetMapping("/year")
    public Result<List<ScoreLine>> getScoreLineByYear(@RequestParam Short year) {
        List<ScoreLine> scoreLines = scoreLineService.getScoreLineByYear(year);
        return Result.success("获取成功", scoreLines);
    }

    @PermitAll
    @GetMapping("/year_batch")
    public Result<List<ScoreLine>> getScoreLineByYearAndBatch(@RequestParam Short year, @RequestParam Byte batch) {
        List<ScoreLine> scoreLines = scoreLineService.getScoreLineByYearAndBatch(year, batch);
        return Result.success("获取成功", scoreLines);
    }

    @PermitAll
    @GetMapping("/all")
    public Result<List<ScoreLine>> getScoreLine() {
        List<ScoreLine> scoreLines = scoreLineService.getAllScoreLine();
        return Result.success("获取成功", scoreLines);
    }
}
