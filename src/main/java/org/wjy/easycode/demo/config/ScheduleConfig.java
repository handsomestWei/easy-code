package org.wjy.easycode.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TaskManagementConfigUtils;

import java.util.concurrent.*;

/**
 * 启用定时任务，并配置@Scheduled线程池
 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Value("${thread.pool.corePoolSize:10}")
    private int corePoolSize;

    @Value("${thread.pool.maxPoolSize:500}")
    private int maxPoolSize;

    @Value("${thread.pool.keepAliveSeconds:300}")
    private int keepAliveSeconds;

    // 注解的后置处理器
    @Bean(name = TaskManagementConfigUtils.SCHEDULED_ANNOTATION_PROCESSOR_BEAN_NAME)
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ScheduledAnnotationBeanPostProcessor scheduledAnnotationProcessor() {
        return new ScheduledAnnotationBeanPostProcessor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(getExecutor(corePoolSize,maxPoolSize,keepAliveSeconds));
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize     最小线程数
     * @param maxPoolSize      最大线程数
     * @param keepAliveSeconds 允许空闲时间(秒)
     * @return 返回队列
     */
    protected ScheduledExecutorService getExecutor(int corePoolSize, int maxPoolSize, long keepAliveSeconds) {
        // 线程名称
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("schedule-pool-%d").build();
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(corePoolSize, namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        // 最小线程数
        executor.setCorePoolSize(corePoolSize);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 最大线程数
        executor.setMaximumPoolSize(maxPoolSize);
        // 允许空闲时间(秒)
        executor.setKeepAliveTime(keepAliveSeconds, TimeUnit.SECONDS);
        return executor;
    }
}
