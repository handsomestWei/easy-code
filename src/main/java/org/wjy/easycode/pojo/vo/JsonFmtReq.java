package org.wjy.easycode.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@JsonIgnoreProperties // json字符串转pojo时忽略不存在的字段。有时字符串里的字段，在声明的pojo里没有，会抛转换异常
@Accessors(chain = true) // 支持链式调用
@AllArgsConstructor // 全参数的构造函数。链式赋值太多时，写法可以更优雅
@NoArgsConstructor // 无参构造函数
@ToString(callSuper = true) // toString同时输出父类字段，默认false不输出
@Data
public class JsonFmtReq extends BaseReq {

    // json字符串的userName字段，转换为pojo的name字段
    // pojo的name字段，转换为json字符串的userName字段
    @JsonProperty(value = "userName")
    private String name;

    private Integer sex;

    // json时间格式字符串，转pojo时间对象
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

}
