package com.yuan.bi.controller;


import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 队列测试
 *
 * @author zhangchenyuan
 * @date 2024/06/12
 */
@RestController
@RequestMapping("/queue")
@Slf4j
@Profile({"dev","local"})
public class QueueController {

    // 自动注入一个线程池实例
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    // 接受一个参数name，然后将任务添加到线程池中
    @GetMapping("/add")
    public void add(String name) {
        // 使用CompletableFuture运行一个异步任务
        CompletableFuture.runAsync(() -> {
            // 打印一条日志信息包括任务名称和执行线程的名称
            log.info("任务执行中：" + name + "，执行人；" + Thread.currentThread().getName());
            try {
                // 任务休眠10分钟，模拟长时间运行的任务
                Thread.sleep(600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        // 异步任务在threadPoolExecutor线程池中执行
        }, threadPoolExecutor);
    }

    // 该方法返回线程池的状态信息
    @GetMapping("/get")
    public String get() {
        // 创建一个HashMap存储线程池的状态信息
        Map<String,Object> map = new HashMap<>();
        // 获取线程池的队列长度
        int size = threadPoolExecutor.getQueue().size();
        // 将队列长度放入map中
        map.put("队列长度",size);
        // 获取线程池已经接受的任务数量
        long taskCount = threadPoolExecutor.getTaskCount();
        // 将任务数量放入map中
        map.put("任务数量",taskCount);
        // 获取线程池已经完成的任务数量
        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        // 将已完成的任务数量放入map中
        map.put("已完成任务数量",completedTaskCount);
        // 获取线程池的活跃线程数
        int activeCount = threadPoolExecutor.getActiveCount();
        // 将活跃线程数放入map中
        map.put("正在工作活跃的线程数",activeCount);
        // 将线程池的状态信息转换为JSON字符串返回
        return JSONUtil.toJsonStr(map);
    }


}
