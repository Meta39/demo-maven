package com.fu.springboot3demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fu.springboot3demo.entity.InspectionTestRecords;
import com.fu.springboot3demo.entity.hibernate.*;
import com.fu.springboot3demo.mapper.InspectionTestRecordsMapper;
import com.fu.springboot3demo.util.JacksonUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

/**
 * 当使用Hibernate时，没有办法像MyBatis那样能多层级嵌套，实现只查询一次，循环一次实现返回多层级嵌套json数据。
 */
@Slf4j
@SpringBootTest
public class HibernateSortTests {
    @Resource
    private InspectionTestRecordsMapper inspectionTestRecordsMapper;

    @Test
    public void test() throws JsonProcessingException {
        // 第一层分组键：VisitOrganization + VisitOrganizationName + PatientType + Name
        Map<String, InspectionTestRecord> recordMap = new LinkedHashMap<>(); // 保持插入顺序
        //1.查询出来的结果，先在SQL中用ORDER BY 排好序，这样代码里只要保证顺序插入即可保证列表有序。如果排序错误就会导致丢失一部分，所以排序要小心。
        List<InspectionTestRecords> sqlResults = inspectionTestRecordsMapper.selectList(new LambdaQueryWrapper<InspectionTestRecords>()
                .orderByAsc(InspectionTestRecords::getVisitOrganization,
                        InspectionTestRecords::getPatientType,
                        InspectionTestRecords::getTestId,
                        InspectionTestRecords::getPlantTestId,
                        InspectionTestRecords::getBioId
                )
        );

        InspectionTestRecord currentRecord = null;//临时存放当前记录
        String previousKey = null;//临时存放当前key到下一条记录用于判断上一条记录和当前记录是否是一样的key
        for (InspectionTestRecords sqlResult : sqlResults) {
            String visitOrganization = sqlResult.getVisitOrganization();
            String visitOrganizationName = sqlResult.getVisitOrganizationName();
            String patientType = sqlResult.getPatientType();
            String name = sqlResult.getName();

            //2.生成当前记录的分组键
            String currentKey = String.join("|", visitOrganization, visitOrganizationName, patientType, name);
            //如果切换到新分组，创建新记录
            if (!currentKey.equals(previousKey)) {
                currentRecord = new InspectionTestRecord(visitOrganization, visitOrganizationName, patientType, name, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                recordMap.put(currentKey, currentRecord);
                previousKey = currentKey;
            }

            // TestResults 按顺序添加子对象（依赖数据库排序）
            String testId = sqlResult.getTestId();
            String testName = sqlResult.getTestName();
            if (testId != null) {
                currentRecord.getTestResults().add(new TestResult(testId, testName));
            }

            // PlantResults 按顺序添加子对象（依赖数据库排序）
            String plantTestId = sqlResult.getPlantTestId();
            String plantTestName = sqlResult.getPlantTestName();
            if (plantTestId != null) {
                currentRecord.getPlantResults().add(new PlantResult(plantTestId, plantTestName));
            }

            // 处理 BioResults（包含嵌套的 AntiResults）
            String bioId = sqlResult.getBioId();
            if (bioId == null) {
                continue;
            }

            String bioName = sqlResult.getBioName();
            String antiEaxmMethod = sqlResult.getAntiExamMethod();
            String antiResultUnit = sqlResult.getAntiResultUnit();
            String antiResultBioId = sqlResult.getAntiResultBioId();
            String etestResult = sqlResult.getEtestResult();

            // 获取当前 InspectionTestRecord 的 BioResults 列表
            List<BioResult> bioResults = currentRecord.getBioResults();
            // 判断是否需要创建新的 BioResult，前提是bioId是唯一
            if (bioResults.isEmpty() || !bioResults.getLast().getBioId().equals(bioId)) {
                // 创建新的 AntiResults 和 AntiResult
                AntiResults antiResults = new AntiResults(antiEaxmMethod, antiResultUnit, new ArrayList<>());
                antiResults.getAntiResult().add(
                        new AntiResult(antiResultBioId, etestResult)
                );

                // 创建并添加新的 BioResult
                BioResult newBioResult = new BioResult(bioId, bioName, antiResults);
                bioResults.add(newBioResult);
            } else {
                // 获取最后一个 BioResult，直接添加 AntiResult
                BioResult lastBioResult = bioResults.getLast();
                lastBioResult.getAntiResults().getAntiResult().add(
                        new AntiResult(antiResultBioId, etestResult)
                );
            }
        }
        // 将分组结果存入顶层报告
        List<InspectionTestRecord> inspectionTestRecords = new ArrayList<>(recordMap.values());

        log.info("{}", JacksonUtils.JSON.writeValueAsString(inspectionTestRecords));
    }

}
