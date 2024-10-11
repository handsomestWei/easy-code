package org.wjy.easycode.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.SchedulingConfiguration;

// 控制注解@EnableScheduling是否启用生效
@Configuration
@ConditionalOnExpression(value = "${enable.scheduling:true}")
@Import(SchedulingConfiguration.class)
public class ControlSchedulingConfiguration {}
