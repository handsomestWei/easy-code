package org.wjy.easycode.modules.proxy.http.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpMethod;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author weijiayu
 * @date 2024/1/22 15:05
 */
@Data
public class ProxyParamDto {

    @NotBlank
    @ApiModelProperty(name = "路由类型", example = "0-本级，1-转发")
    private String routeType = "0";

    @NotBlank
    @ApiModelProperty(name = "路由key", example = "一般为微服务名称")
    private String routeKey = "";

    @NotBlank
    @ApiModelProperty("请求Url")
    private String url;

    public void setReqMethod(String reqMethod) {
        if (null == reqMethod || "".equals(reqMethod)) {
            this.reqMethod = HttpMethod.POST.name();
        } else {
            this.reqMethod = reqMethod.toUpperCase();
        }
    }

    @ApiModelProperty("请求方式")
    private String reqMethod = HttpMethod.POST.name();

    @ApiModelProperty("请求参数")
    private Object param = "";
}
