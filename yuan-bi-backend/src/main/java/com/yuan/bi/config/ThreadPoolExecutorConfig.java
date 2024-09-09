package com.yuan.bi.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 创建一个线程工厂
        ThreadFactory threadFactory = new ThreadFactory(){
            // 初始化线程为1
            private int count = 1;

            // 每当线程池需要创建新线程时，就会调用newThread方法
            // @NotNULL Runnable r 表示方法参数r不能为null
            // 如果这个方法被调用的时候传递了一个null参数，那么就会抛出一个NullPointerException异常
            @Override
            public Thread newThread(@NotNull Runnable r) {
                // 创建一个线程，线程名为"线程" + count++
                // 每次创建线程，count都会自增1
                // 比如第一次创建线程，线程名为"线程1"，第二次创建线程，线程名为"线程2"，以此类推
                return new Thread(r, "线程" + count++);
            }
        };
        // 创建一个新的线程池，线程池的核心线程数为2，最大线程数为4，非核心线程空闲时间为100秒
        // 线程池的任务队列为一个容量为4的阻塞队列，线程工厂为上面创建的线程工厂
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4), threadFactory);
        // 返回创建的线程池
        return threadPoolExecutor;
    }
}
