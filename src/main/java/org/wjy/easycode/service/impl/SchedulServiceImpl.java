package org.wjy.easycode.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 使用@EnableScheduling声明启用springboot定时任务。如果不配置，默认单线程顺序执行所有定时任务
 * 使用@EnableAsync声明启用springboot自带的异步线程池
 * @see com.wjy.springbootdemo.config.ScheduleConfig
 * @see com.wjy.springbootdemo.config.ControlSchedulingConfiguration
 */
@Service
public class SchedulServiceImpl {

    @Async
    @Scheduled(cron="0/5 * * * * ? ")   //每5秒执行一次
    public void exeTask1() throws InterruptedException {
        // 如果不使用@Async，由于@EnableScheduling默认单线程，将阻塞后续任务
        Thread.currentThread().sleep(3* 1000);
        System.out.println(new Date().toGMTString() + "===>>>>>>>>>>>>>>>>task1");
    }

    @Async
    @Scheduled(cron="0/6 * * * * ? ")   //每6秒执行一次
    public void exeTask2() {
        System.out.println(new Date().toGMTString() + "===>>>task2");
    }
}
