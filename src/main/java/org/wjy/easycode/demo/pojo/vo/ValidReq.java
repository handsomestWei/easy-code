package org.wjy.easycode.demo.pojo.vo;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class ValidReq {

    // 字符串空判断
    @NotBlank
    private String id;

    // 字段非空时，触发校验
    // 整型参数校验
    @Min(value = 1, message = "param sum min error")
    @Max(value = 3, message = "param sum max error")
    private Integer sum;

    // 字符串类型参数校验
    @Size(min = 10, max = 20, message = "param phone size error")
    @Pattern(regexp = "^[0-9]{10,20}$", message = "param phone format error")
    private String phone;
}
