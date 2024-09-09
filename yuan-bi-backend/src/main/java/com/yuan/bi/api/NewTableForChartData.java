package com.yuan.bi.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewTableForChartData {
    public static void main(String[] args) {
        String csvData = "日期,用户数\n" +
                "1号,10\n" +
                "2号,20\n" +
                "3号,30";

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


        // 打印读取的数据
        System.out.println("Fields: " + fields);
        System.out.println("Data: ");
        for (Map<Integer, String> row : data) {
            System.out.println(row);
        }

        Long chatId = 1L;
        String tableName = "chart_" + chatId;
        //根据fields生成sql创建表语句
        StringBuilder createTableSql = new StringBuilder("CREATE TABLE " + tableName + " (");
        for (int i = 0; i < fields.size(); i++) {
            createTableSql.append(fields.get(i) + " VARCHAR(255)");
            if (i != fields.size() - 1) {
                createTableSql.append(",");
            }
        }
        createTableSql.append(");");

        System.out.println("Create table sql: " + createTableSql);


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
        System.out.println("Insert sql: " + insertSql);

    }
}
