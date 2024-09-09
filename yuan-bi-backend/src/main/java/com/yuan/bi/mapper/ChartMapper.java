package com.yuan.bi.mapper;

import com.yuan.bi.model.entity.Chart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
* @author zhangchenyuan
* @description 针对表【chart(图表信息表)】的数据库操作Mapper
* @createDate 2024-05-11 17:09:11
* @Entity com.yuan.bi.model.entity.Chart
*/
public interface ChartMapper extends BaseMapper<Chart> {
    /*
    * 方法的返回类型是 List<Map<String,Object>>,
    * 表示返回的是一个由多个map组成的集合，每个map对应一条记录
    * 并将其封装成了一组键值对形式的对象。其中，String类型代表了键的类型是字符串
    * Object类型代表了值的类型为任意对象，使得这个方法可以适应不同类型的数据查询
     */
    List<Map<String,Object>> queryChartData(String querySql);

    void createChartDataTableById(String createTableSql);

    void insertChartDataTableById(String insertTableSql);

}




