package org.wjy.easycode.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取外部自定义文件配置，加入到环境变量中。要加入到META-INF/spring.factories中，使用了spi机制
 */
public class CustomEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final Properties properties = new Properties();

    private String[] profiles = {
            "ctm-env.properties",
    };

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        for (String profile : profiles) {
            Resource resource = new ClassPathResource(profile);
            // 加入到环境变量
            environment.getPropertySources().addLast(loadProfiles(resource));
        }
    }

    private PropertySource<?> loadProfiles(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("file " + resource + " not exist");
        }
        try {
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), properties);
        } catch (IOException ex) {
            throw new IllegalStateException("load resource exception" + resource, ex);
        }
    }
}
