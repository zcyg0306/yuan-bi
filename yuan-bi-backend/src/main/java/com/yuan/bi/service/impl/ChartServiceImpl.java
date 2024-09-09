package com.yuan.bi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.bi.model.entity.Chart;
import com.yuan.bi.service.ChartService;
import com.yuan.bi.mapper.ChartMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author zhangchenyuan
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2024-05-11 17:09:11
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    @Resource
    private ChartMapper chartMapper;

    @Override
    public void csvDataToTable(String csvData, Long chartId) {
        // 将csv数据进行分割
        String[] lines = csvData.split("\n");

        List<String> fields = new ArrayList<>();
        List<Map<Integer, String>> data = new ArrayList<>();


        // 读取第一行作为fields
        if (lines.length > 0) {
            String[] fieldArray = lines[0].split(",");
            for (String field : fieldArray) {
                fields.add(field.trim());
            }
        }

        // 读取剩余行作为数据
        for (int i = 1; i < lines.length; i++) {
            String[] dataArray = lines[i].split(",");
            Map<Integer, String> row = new HashMap<>();
            for (int j = 0; j < dataArray.length; j++) {
                row.put(j, dataArray[j].trim());
            }
            data.add(row);
        }



        String tableName = "chart_" + chartId;
        //根据fields生成sql创建表语句
        StringBuilder createTableSql = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (int i = 0; i < fields.size(); i++) {
            createTableSql.append(fields.get(i) + " VARCHAR(255)");
            if (i != fields.size() - 1) {
                createTableSql.append(",");
            }
        }
        createTableSql.append(");");

        chartMapper.createChartDataTableById(createTableSql.toString());

        //根据data生成sql插入语句
        StringBuilder insertSql = new StringBuilder("INSERT INTO " + tableName + " (");
        for (int i = 0; i < fields.size(); i++) {
            insertSql.append(fields.get(i));
            if (i != fields.size() - 1) {
                insertSql.append(",");
            }
        }
        insertSql.append(") VALUES ");
        for (int i = 0; i < data.size(); i++) {
            Map<Integer, String> row = data.get(i);
            insertSql.append("(");
            for (int j = 0; j < fields.size(); j++) {
                insertSql.append("'" + row.get(j) + "'");
                if (j != fields.size() - 1) {
                    insertSql.append(",");
                }
            }
            insertSql.append(")");
            if (i != data.size() - 1) {
                insertSql.append(",");
            }
        }
        insertSql.append(";");
        chartMapper.insertChartDataTableById(insertSql.toString());
    }

}




