package org.wjy.easycode.demo.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 参数校验配置 依赖spring-boot-starter-validation
 * 
 * @see org.wjy.easycode.demo.config.ValidGlobalExceptionHandler
 */
@Configuration
public class ValidConfig {

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure()
            // 快速失败：只要出现校验失败的情况，就立即结束校验，不再进行后续的校验。
            .failFast(true).buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }

}
