package org.wjy.easycode.modules.proxy.http.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.wjy.easycode.modules.proxy.http.constant.ProxyConstant;
import org.wjy.easycode.modules.proxy.http.dto.ApiResponse;
import org.wjy.easycode.modules.proxy.http.dto.ProxyParamDto;
import org.wjy.easycode.modules.proxy.http.service.ProxyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 接口代理，实现本地和本域微服务、外域网关接口透传调用
 * 
 * @author weijiayu
 * @date 2024/1/22 14:56
 */
@RestController
@Slf4j
@Api(tags = "接口代理")
@RequestMapping(ProxyConstant.URL_API_PREFIX)
public class ProxyController {

    @Resource
    private ProxyService proxyService;

    @ApiOperation(value = "转发")
    @PostMapping(ProxyConstant.URL_FORWARD)
    public Object forward(@RequestHeader HttpHeaders headers, @RequestBody @Valid ProxyParamDto proxyParamDto) {
        try {
            return proxyService.forward(headers, proxyParamDto);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiResponse.instanceSuccessApiResponse(null);
    }
}
