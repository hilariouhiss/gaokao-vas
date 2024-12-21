package com.example.demo.listener;

import com.example.demo.entity.Class;
import com.example.demo.entity.*;
import com.example.demo.repository.CategoryJpaRepository;
import com.example.demo.repository.SchoolJpaRepository;
import com.example.demo.repository.ScoreLineJpaRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;

@Component
@Log4j2
public class ApplicationReadyListener {

    private ExecutorService executorService;

    private final int threadCount = Runtime.getRuntime().availableProcessors();

    private final SchoolJpaRepository schoolJpaRepository;

    private final CategoryJpaRepository categoryJpaRepository;

    private final ScoreLineJpaRepository scoreLineJpaRepository;

    private final ResourceLoader resourceLoader;

    public ApplicationReadyListener(SchoolJpaRepository schoolJpaRepository,
                                    CategoryJpaRepository categoryJpaRepository,
                                    ScoreLineJpaRepository scoreLineJpaRepository, ResourceLoader resourceLoader) {
        this.schoolJpaRepository = schoolJpaRepository;
        this.categoryJpaRepository = categoryJpaRepository;
        this.scoreLineJpaRepository = scoreLineJpaRepository;
        this.resourceLoader = resourceLoader;
    }

    // 打开 Excel 文件并返回第一个 Sheet
    private Sheet openExcelFile(String fileName) {
        Resource resource = resourceLoader.getResource("classpath:excel/" + fileName);
        Sheet sheet;
        try (FileInputStream excelFile = new FileInputStream(resource.getFile().getAbsolutePath());
             XSSFWorkbook workbook = new XSSFWorkbook(excelFile)) {
            sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                log.error("读取Sheet失败！");
                throw new RuntimeException("读取Sheet失败！");
            }
        } catch (IOException e) {
            log.error("Excel 文件打开失败！");
            throw new RuntimeException("Excel 文件打开失败！");
        }
        return sheet;
    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String[] files = {"categories.xlsx", "schools.xlsx", "scores.xlsx"};

        executorService = Executors.newVirtualThreadPerTaskExecutor();

        Future<?> schoolFuture = null;
        for (var file : files) {
            switch (file) {
                case "schools.xlsx" -> schoolFuture = executorService.submit(() -> saveSchools(openExcelFile(file)));
                case "categories.xlsx" -> executorService.execute(() -> saveCategories(openExcelFile(file)));
            }
        }
        if (schoolFuture != null) {
            try {
                schoolFuture.get(); // 等待 saveSchools 执行完成
            } catch (InterruptedException | ExecutionException e) {
                log.error("等待 saveSchools 时异常", e);
            }
        }
        // 执行 saveScoreLines
        executorService.execute(() -> saveScoreLines(openExcelFile("scores.xlsx")));
    }

    private void saveCategories(Sheet sheet) {
        saveEntities(sheet, (startRow, endRow) -> {
            Map<String, Category> categoryMap = new HashMap<>(); // 存储已有的 Category
            List<Category> categories = new ArrayList<>();
            for (int i = startRow; i < endRow; i++) {
                Row row = sheet.getRow(i);
                // 从Excel文件中读取数据
                final String categoryName = row.getCell(0).getStringCellValue();
                final String className = row.getCell(1).getStringCellValue();
                final String majorName = row.getCell(2).getStringCellValue();
                final String majorCode = row.getCell(3).getStringCellValue();

                // 创建专业
                Major newMajor = new Major();
                newMajor.setCode(majorCode);
                newMajor.setName(majorName);

                // 尝试从 Map 中获取
                Category category = categoryMap.get(categoryName);
                // 若 category 不存在, 则创建
                if (category == null) {
                    category = new Category();
                    category.setName(categoryName);
                    category.setClasses(new HashSet<>());
                    categoryMap.put(categoryName, category);
                    categories.add(category);
                }
                Class currentClass = null;
                // 尝试从 category 的 Set<class> 中获取
                for (Class clazz : category.getClasses()) {
                    if (clazz.getName().equals(className)) {
                        currentClass = clazz;
                        break;
                    }
                }
                // 若 class 不存在, 则创建
                if (currentClass == null) {
                    currentClass = new Class();
                    currentClass.setName(className);
                    category.getClasses().add(currentClass);
                }
                if (currentClass.getMajors() == null) {
                    currentClass.setMajors(new HashSet<>());
                }
                currentClass.getMajors().add(newMajor);
            }
            return categories;
        }, categoryJpaRepository::saveAll);
    }

    private void saveSchools(Sheet sheet) {
        saveEntities(sheet, (startRow, endRow) -> {
            List<School> schools = new ArrayList<>();
            for (int i = startRow; i < endRow; i++) {
                Row row = sheet.getRow(i);
                // 从Excel文件中读取数据
                final String code = row.getCell(0).getStringCellValue();
                final String name = row.getCell(1).getStringCellValue();
                final String province = row.getCell(2).getStringCellValue();
                final String department = row.getCell(3).getStringCellValue();
                final String imgUrl = row.getCell(4).getStringCellValue();
                final String mold = row.getCell(5).getStringCellValue();
                final float evaluation = (float) row.getCell(6).getNumericCellValue();
                // 创建 school
                School newSchool = new School();
                newSchool.setCode(code);
                newSchool.setName(name);
                newSchool.setProvince(province);
                newSchool.setDepartment(department);
                newSchool.setImgUrl(imgUrl);
                newSchool.setMold(mold);
                newSchool.setEvaluation(evaluation);
                schools.add(newSchool);
            }
            return schools;
        }, schoolJpaRepository::saveAll);
    }

    private void saveScoreLines(Sheet sheet) {
        saveEntities(sheet, (startRow, endRow) -> {
            List<ScoreLine> scoreLines = new ArrayList<>();
            for (int i = startRow; i < endRow; i++) {
                Row row = sheet.getRow(i);
                // 从Excel文件中读取数据
                final String schoolName = row.getCell(0).getStringCellValue();
                // 若 school 不存在, 则跳过
                School school = schoolJpaRepository.findByName(schoolName).orElse(null);
                if (school == null) {
                    log.warn("学校不存在");
                    continue;
                }
                // 获取 batch, subject, total_score, chinese, math, comprehensive, year
                final byte batch = (byte) row.getCell(1).getNumericCellValue();
                final byte subject = (byte) row.getCell(2).getNumericCellValue();
                final short totalScore = (short) row.getCell(3).getNumericCellValue();
                if (totalScore < 0 || totalScore > 750) {
                    log.warn("总分不合法");
                    continue;
                }
                final short chinese = (short) row.getCell(4).getNumericCellValue();
                if (chinese < 0 || chinese > 150) {
                    log.warn("语文分数不合法");
                    continue;
                }
                final short math = (short) row.getCell(5).getNumericCellValue();
                if (math < 0 || math > 150) {
                    log.warn("数学分数不合法");
                    continue;
                }
                final short comprehensive = (short) row.getCell(6).getNumericCellValue();
                if (comprehensive < 0 || comprehensive > 300) {
                    log.warn("综合分数不合法");
                    continue;
                }
                final short year = (short) row.getCell(7).getNumericCellValue();
                if (year < 2021 || year > 2023) {
                    log.warn("年份不合法");
                    continue;
                }
                // 创建 scoreLine
                ScoreLine newScoreLine = new ScoreLine();
                newScoreLine.setSchoolName(school.getName());
                newScoreLine.setYear(year);
                newScoreLine.setBatch(batch);
                newScoreLine.setSubject(subject);
                newScoreLine.setTotalScore(totalScore);
                newScoreLine.setChinese(chinese);
                newScoreLine.setMath(math);
                newScoreLine.setComprehensive(comprehensive);
                scoreLines.add(newScoreLine);
            }
            return scoreLines;
        }, scoreLineJpaRepository::saveAll);
    }

    private <T> void saveEntities(Sheet sheet,
                                  BiFunction<Integer, Integer, List<T>> readFunction,
                                  Consumer<List<T>> saveAllFunction) {
        int Rowcount = (int) categoryJpaRepository.count();
        if (Rowcount > 0) {
            log.info("数据已存在，跳过读取！");
            return;
        }
        int totalRows = sheet.getPhysicalNumberOfRows();
        int rowsPerThread = totalRows / threadCount;
        int batchSize = 500;
        for (int i = 0; i < threadCount; i++) {
            int startRow = i * rowsPerThread;
            int endRow = (i == threadCount - 1) ? totalRows : startRow + rowsPerThread;
            executorService.submit(() -> {
                List<T> entities = readFunction.apply(startRow, endRow);
                if (entities.size() == batchSize) {
                    saveAllFunction.accept(entities);
                    entities.clear();
                }
                if (!entities.isEmpty()) {
                    saveAllFunction.accept(entities);
                }
            });
        }
    }
}
