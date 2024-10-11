package org.wjy.easycode.modules.proxy.http.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.cloud.openfeign.FeignContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSONObject;

/**
 * @author weijiayu
 * @date 2024/1/22 18:07
 */
@Component
public class MethodInvokeUtil implements ApplicationContextAware {

    /** 映射关系缓存 */
    private static HashMap<String, HandlerMethod> controllerMethodCacheMap = new HashMap();

    /**
     * Spring应用上下文环境
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        MethodInvokeUtil.applicationContext = applicationContext;
    }

    public static Object invokeControllerMethod(HttpMethod httpMethod, String reqUrl, Object... args) throws Exception {
        HandlerMethod handlerMethod = findMethodInController(httpMethod, reqUrl);
        if (handlerMethod == null) {
            return null;
        }
        // 按方法入参类型逐个转换
        Method md = handlerMethod.getMethod();
        Object[] paramArray = null;
        if (HttpMethod.GET.equals(httpMethod)) {
            // 对于get请求，从url里按&&截取k1=v1的参数的值填充到args中
            paramArray = wrapperParamWithUrl(md.getParameterTypes(), reqUrl);
        } else {
            paramArray = wrapperParamWithArgs(md.getParameterTypes(), args);
        }
        // 反射调用
        String beanName = handlerMethod.getBean().toString();
        return handlerMethod.getMethod().invoke(applicationContext.getBean(beanName), paramArray);
    }

    /** 从controller层查找匹配url和请求方式的方法 */
    public static HandlerMethod findMethodInController(HttpMethod httpMethod, String reqUrl) {
        reqUrl = UrlUtil.getUrlPath(reqUrl);
        String hdlKey = httpMethod.name() + "-" + reqUrl;
        if (!controllerMethodCacheMap.containsKey(hdlKey)) {
            controllerMethodCacheMap.put(hdlKey, null);
            RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> reqMethodMap = mapping.getHandlerMethods();
            for (RequestMappingInfo mapInfo : reqMethodMap.keySet()) {
                if (!isMatchHttpMethod(httpMethod, mapInfo)) {
                    continue;
                }
                String mapUrl = mapInfo.getPatternsCondition().toString();
                mapUrl = UrlUtil.getUrlPath(mapUrl);
                mapUrl = mapUrl.replace("[", "").replace("]", "");
                if (mapUrl.equals(reqUrl)) {
                    controllerMethodCacheMap.put(hdlKey, reqMethodMap.get(mapInfo));
                    break;
                }
            }
        }
        return controllerMethodCacheMap.get(hdlKey);
    }

    public static Object invokeFeignClientMethod(String svcName, HttpMethod httpMethod, String reqUrl, Object... args)
        throws Exception {
        // TODO
        HandlerMethod handlerMethod = findMethodInFeignClient(httpMethod, reqUrl);
        return null;
    }

    /** 从feignContext容器查找匹配url和请求方式的方法 */
    public static HandlerMethod findMethodInFeignClient(HttpMethod httpMethod, String reqUrl) {
        // TODO
        FeignContext feignContext = getFeignContext();
        return null;
    }

    private static FeignContext getFeignContext() {
        // applicationContext.getBeanNamesForAnnotation(FeignClient.class);
        return applicationContext.getBean("feignContext", FeignContext.class);
    }

    private static Object[] wrapperParamWithUrl(Class<?>[] parameterTypes, String reqUrl) {
        int len = parameterTypes.length;
        Object[] paramArray = new Object[len];
        Object[] urlParamArray = UrlUtil.getUrlParams(reqUrl);
        len = Math.min(len, urlParamArray.length);
        for (int i = 0; i < len; i++) {
            // TODO 类型转换
            paramArray[i] = urlParamArray[i];
        }
        return paramArray;
    }

    private static Object[] wrapperParamWithArgs(Class<?>[] parameterTypes, Object... args) {
        int len = parameterTypes.length;
        Object[] paramArray = new Object[len];
        if (args == null) {
            return paramArray;
        }
        len = Math.min(len, args.length);
        for (int i = 0; i < len; i++) {
            paramArray[i] = JSONObject.parseObject(JSONObject.toJSONString(args[i]), parameterTypes[i]);
        }
        return paramArray;
    }

    private static Boolean isMatchHttpMethod(HttpMethod httpMethod, RequestMappingInfo mapInfo) {
        Iterator<RequestMethod> it = mapInfo.getMethodsCondition().getMethods().iterator();
        while (it.hasNext()) {
            if (it.next().toString().equals(httpMethod.toString())) {
                return true;
            }
        }
        return false;
    }
}
