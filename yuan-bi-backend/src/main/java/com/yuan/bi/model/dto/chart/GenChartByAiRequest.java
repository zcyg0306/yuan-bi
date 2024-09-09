package com.yuan.bi.model.dto.chart;

import lombok.Data;

import java.io.Serializable;

/**
 * AI生成图表请求
 *
 * @author zcy
 */
@Data
public class GenChartByAiRequest implements Serializable {

    /**
     * 图表名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;

    /**
     * 图表类型
     */
    private String chartType;

    private static final long serialVersionUID = 1L;
}