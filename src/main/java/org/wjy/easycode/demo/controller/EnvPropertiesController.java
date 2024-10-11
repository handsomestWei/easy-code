package org.wjy.easycode.demo.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wjy.easycode.demo.config.CustomProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取配置变量
 */
@Slf4j
@RestController("/api/v1/value")
public class EnvPropertiesController implements InitializingBean {

    @Resource
    private CustomProperties ctmProperties;
    @Resource
    private Environment env;

    // 带有#号，使用SpEL表达式解析
    @Value("${val:defaultVal}") // 冒号后面定义默认值
    private String val;
    @Value("${val.array}") // 数组形式
    private String[] valArray;
    @Value("#{'${val.array}'.split(',')}") // 列表形式
    private List<String> valList;
    @Value("#{'${val.map}'.split(',')}") // 哈希形式
    private HashMap<String, String> valMap;

    // 注意：@Value无法注入静态变量
    @Value("${val}")
    @Getter
    @Setter
    public static String STATIC_VAL;

    @GetMapping(value = "/val")
    public void printVal() {
        log.debug("val=" + val);
        log.debug("val.array=" + valList);
        log.debug("val.map=" + valMap);
    }

    // 获取自定义配置文件的变量值
    /** @see org.wjy.easycode.demo.config.CustomProperties */
    @GetMapping(value = "/properties")
    public String getPropVal() {
        return ctmProperties.getVal();
    }

    // 获取环境变量的值
    /** @see org.wjy.easycode.demo.config.CustomEnvironmentPostProcessor */
    @GetMapping(value = "/env")
    public String getEnvVal() {
        return env.getProperty("ctm.env.val");
    }

    // 注入静态变量
    @Value("${val}")
    public void setStaticVal(String val) {
        STATIC_VAL = val;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 注入静态变量
        STATIC_VAL = val;
    }
}
