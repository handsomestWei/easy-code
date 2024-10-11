package org.wjy.easycode.modules.proxy.http.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.wjy.easycode.modules.proxy.http.constant.ProxyConstant;
import org.wjy.easycode.modules.proxy.http.dto.ApiResponse;
import org.wjy.easycode.modules.proxy.http.dto.ProxyParamDto;
import org.wjy.easycode.modules.proxy.http.enums.RouteTypeEnum;
import org.wjy.easycode.modules.proxy.http.service.ProxyService;
import org.wjy.easycode.modules.proxy.http.util.MethodInvokeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author weijiayu
 * @date 2024/1/22 15:15
 */
@Service
@Slf4j
public class ProxyServiceImpl implements ProxyService {

    /** 本地微服务名称 */
    @Value("${spring.application.name:}")
    private String svcName = "";

    /** url校验开关 */
    @Value("${proxy_url_valid_flag:false}")
    private Boolean proxyUrlValidFlag = false;
    /** 支持代理的url */
    @Value("#{'${proxy_url_support:}'.split(',')}")
    private HashSet<String> proxyUrlSupportSet = new HashSet<>();
    /** 支持代理的header key */
    @Value("#{'${proxy_header_support:}'.split(',')}")
    private HashSet<String> proxyHeaderSupportSet = new HashSet<>();

    @Override
    public Object forward(HttpHeaders headers, ProxyParamDto proxyParamDto) throws Exception {
        // 代理url校验
        if (!validProxyUrl(proxyParamDto.getUrl())) {
            return ApiResponse.instanceSuccessApiResponse(null);
        }
        HashMap<String, String> httpHeaderMap = wrapperHttpHeader(headers);
        switch (RouteTypeEnum.getByTypeCode(proxyParamDto.getRouteType())) {
            case LOCAL:
                return forwardLocal(httpHeaderMap, proxyParamDto);
            case FORWARD:
                return forwardWithOpenApi(httpHeaderMap, proxyParamDto);
            default:
                break;
        }
        return ApiResponse.instanceSuccessApiResponse(null);
    }

    /** 本级 */
    private Object forwardLocal(HashMap<String, String> httpHeaderMap, ProxyParamDto proxyParamDto) throws Exception {
        if (svcName.equals(proxyParamDto.getRouteKey())) {
            return handleWithLocal(httpHeaderMap, proxyParamDto);
        } else {
            return handleWithOtherSvc(httpHeaderMap, proxyParamDto);
        }
    }

    /** openApi转发 */
    private Object forwardWithOpenApi(HashMap<String, String> httpHeaderMap, ProxyParamDto proxyParamDto) {
        // TODO
        return null;
    }

    /** 本地处理 */
    private Object handleWithLocal(HashMap<String, String> httpHeaderMap, ProxyParamDto proxyParamDto)
        throws Exception {
        Object rsp = MethodInvokeUtil.invokeControllerMethod(HttpMethod.valueOf(proxyParamDto.getReqMethod()),
            proxyParamDto.getUrl(), proxyParamDto.getParam());
        if (rsp == null) {
            return ApiResponse.instanceSuccessApiResponse(null);
        } else {
            return rsp;
        }
    }

    /** 本级其他微服务处理 */
    private Object handleWithOtherSvc(HashMap<String, String> httpHeaderMap, ProxyParamDto proxyParamDto)
        throws Exception {
        Object rsp = MethodInvokeUtil.invokeFeignClientMethod(proxyParamDto.getRouteKey(),
            HttpMethod.valueOf(proxyParamDto.getReqMethod()), proxyParamDto.getUrl(), proxyParamDto.getParam());
        if (rsp == null) {
            return ApiResponse.instanceSuccessApiResponse(null);
        } else {
            return rsp;
        }
    }

    /** 请求头代理 */
    private HashMap<String, String> wrapperHttpHeader(HttpHeaders headers) {
        HashMap<String, String> headerMap = new HashMap<>();
        if (headers == null) {
            return headerMap;
        }
        for (String headerKey : proxyHeaderSupportSet) {
            List<String> headerValues = headers.get(headerKey);
            if (CollectionUtils.isEmpty(headerValues)) {
                continue;
            }
            headerMap.put(headerKey, headerValues.get(0));
        }
        return headerMap;
    }

    /** 代理url校验。权限、安全性等 */
    private Boolean validProxyUrl(String proxyUrl) {
        if (StringUtils.isEmpty(proxyUrl)) {
            return false;
        }
        if ((ProxyConstant.URL_API_PREFIX + ProxyConstant.URL_FORWARD).equals(proxyUrl)) {
            // 避免套娃
            return false;
        }
        if (!proxyUrlValidFlag) {
            return true;
        }
        if (proxyUrlSupportSet.contains(proxyUrl)) {
            return true;
        }
        return false;
    }
}
