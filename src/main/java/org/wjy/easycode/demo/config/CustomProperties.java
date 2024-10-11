package org.wjy.easycode.demo.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.wjy.easycode.demo.pojo.vo.DemoProp;

import lombok.Data;

/**
 * 自定义配置文件
 */
@Configuration
@ConfigurationProperties(prefix = "ctm", ignoreUnknownFields = false) // 配置文件属性前缀，忽略不匹配的字段
@PropertySource(value = "classpath:config/ctm.properties", encoding = "utf-8") // 配置文件路径，读取文件时按utf-8读取
@Data
@Component
public class CustomProperties {
    private String val;
    private Map<String, DemoProp> propMap;
    private List<DemoProp> propList;
}
