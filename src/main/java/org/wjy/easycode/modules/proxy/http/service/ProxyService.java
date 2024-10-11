package org.wjy.easycode.modules.proxy.http.service;

import org.springframework.http.HttpHeaders;
import org.wjy.easycode.modules.proxy.http.dto.ProxyParamDto;

/**
 * http接口代理模式实现本级、跨平台微服务接口互访，减少胶水代码开发的工作量
 * 
 * @author weijiayu
 * @date 2024/1/22 15:09
 */
public interface ProxyService {

    /**
     * 转发请求
     * 
     * @author weijiayu
     * @date 2024/1/22 15:14
     * @param headers
     * @param proxyParamDto
     * @throws Exception
     * @return java.lang.Object
     */
    Object forward(HttpHeaders headers, ProxyParamDto proxyParamDto) throws Exception;
}
