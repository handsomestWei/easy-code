package org.wjy.easycode.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * aop打印方法执行前后日志
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    private Long startTime;
    private Long endTime;

    public WebLogAspect() {
    }

    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(public * org.wjy.easycode.controller.*.*(..))")
    public void webLogPointcut() {
    }

    /**
     * 在执行目标方法之前执行，比如请求接口之前的登录验证;
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLogPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //打印请求的内容
        log.debug("请求Url={},请求方法={},请求参数={}", request.getRequestURL().toString(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
        startTime = System.currentTimeMillis();
    }

    /**
     * 目标方法正常结束之后执行
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLogPointcut()")
    public void doAfterReturning(Object ret) throws Throwable {
        endTime = System.currentTimeMillis();
        log.debug("请求耗时={},请求返回={}", endTime - startTime, ret);
    }

    /**
     * 目标方法非正常结束，发生异常或者抛出异常时执行
     * @param throwable
     */
    @AfterThrowing(value = "webLogPointcut()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        log.debug("发生异常时间={},抛出异常={}", LocalDateTime.now(), throwable.getMessage());
    }
}
