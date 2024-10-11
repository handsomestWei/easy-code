package org.wjy.easycode.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("demo模型")
@Data
public class SwaggerRsp {

    @ApiModelProperty("用户id")
    private String userId;

    private Integer sex;
}
