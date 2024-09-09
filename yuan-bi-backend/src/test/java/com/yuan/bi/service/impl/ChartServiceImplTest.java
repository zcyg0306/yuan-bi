package com.yuan.bi.service.impl;

import com.yuan.bi.service.ChartService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ChartServiceImplTest {

    @Resource
    private ChartService chartServiceImpl;

    @Test
    void csvDataToTable() {
        String csvData = "日期,用户数\n" +
                "1号,10\n" +
                "2号,20\n" +
                "3号,30";
        Long chatId = 1999L;
        chartServiceImpl.csvDataToTable(csvData, chatId);

    }
}