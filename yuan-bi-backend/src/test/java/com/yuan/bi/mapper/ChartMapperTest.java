package com.yuan.bi.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@SpringBootTest
class ChartMapperTest {

    @Resource
    private ChartMapper chartMapper;

    @Test
    void queryChartData() {
        String chartId = "123";
        String querySql = String.format("select * from chart_%s",chartId);
        List<Map<String,Object>> resultData = chartMapper.queryChartData(querySql);
        System.out.println(resultData);
    }

    @Test
    void createChartDataTableById() {
        //String chartId = "1659210482555121666";
        String createTableSql = "CREATE TABLE chart_12345 (日期 VARCHAR(255),用户数 VARCHAR(255));";
        chartMapper.createChartDataTableById(createTableSql);
    }

    @Test
    void insertChartDataTableById(){
        //String chartId = "1659210482555121666";
        String insertTableSql = "INSERT INTO chart_12345 (日期,用户数) VALUES ('1号','10');";
        chartMapper.insertChartDataTableById(insertTableSql);
    }
}