package com.yuan.bi.service;

import com.yuan.bi.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhangchenyuan
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2024-05-11 17:09:11
*/
public interface ChartService extends IService<Chart> {
    public void csvDataToTable(String csvData, Long chartId);
}
