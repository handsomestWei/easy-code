package org.wjy.easycode.demo.config;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 启用异步线程，并配置@Async线程池
 */
@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

    @Value("${thread.pool.corePoolSize:10}")
    private int corePoolSize;

    @Value("${thread.pool.maxPoolSize:500}")
    private int maxPoolSize;

    @Value("${thread.pool.keepAliveSeconds:300}")
    private int keepAliveSeconds;

    @Value("${thread.pool.queueCapacity:2000}")
    private int queueCapacity;

    @Override
    public Executor getAsyncExecutor() {
        return this.getExecutor(corePoolSize, maxPoolSize, keepAliveSeconds, queueCapacity);
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> log.error("Async ERROR: throwable={}，method={},params={}", throwable,
            method, objects);
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize
     *            最小线程数
     * @param maxPoolSize
     *            最大线程数
     * @param keepAliveSeconds
     *            允许空闲时间(秒)
     * @param queueCapacity
     *            缓冲队列数
     * @return 返回队列
     */
    protected ThreadPoolTaskExecutor getExecutor(int corePoolSize, int maxPoolSize, int keepAliveSeconds,
        int queueCapacity) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 最小线程数
        executor.setCorePoolSize(corePoolSize);
        // 缓冲队列数,corePoolSize满时启用缓冲
        executor.setQueueCapacity(queueCapacity);
        // 最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        // 允许空闲时间(秒)
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 指定用于新创建的线程名称的前缀
        executor.setThreadNamePrefix("AsyncExecutor-");
        // 指定拒绝策略
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor exe) {
                log.error("异步任务线程池队列已满.");
            }
        });
        executor.initialize();
        return executor;
    }
}
