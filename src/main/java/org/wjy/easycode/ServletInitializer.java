package org.wjy.easycode;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// war包部署用
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 此处对象为带有@SpringBootApplication注解的启动类
        return builder.sources(EasycodeApplication.class);
    }
}
