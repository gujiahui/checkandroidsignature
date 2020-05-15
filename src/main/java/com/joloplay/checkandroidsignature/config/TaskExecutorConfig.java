package com.joloplay.checkandroidsignature.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author gjh
 */
//@Configuration
//@ComponentScan("com.joloplay.checkandroidsignature")
//@EnableAsync //开启异步任务支持
public class TaskExecutorConfig implements AsyncConfigurer {

    @Value("${task-executor.core_pool_size}")
    private int corePoolSize;
    @Value("${task-executor.max_pool_size}")
    private int maxPoolSize;
    @Value("${task-executor.queue-capacity}")
    private int queueCapacity;
    @Value("${task-executor.name-prefix}")
    private String namePrefix;

    @Override
    public Executor getAsyncExecutor() {
        //实现AsyncConfigurer接口并重写getAsyncExecutor方法，并返回一个ThreadPoolTaskExecutor，这样我们就获得了一个基于线程池TaskExecutor
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //线程池核心线程数
        taskExecutor.setCorePoolSize(corePoolSize);
        //线程池最大线程数
        taskExecutor.setMaxPoolSize(maxPoolSize);
        //缓冲队列
        taskExecutor.setQueueCapacity(queueCapacity);
        //线程名称前缀
        taskExecutor.setThreadNamePrefix(namePrefix);
        //线程空闲后的最大存活时间
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
