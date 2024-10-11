package org.wjy.easycode.demo.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL) // 若pojo对象字段的值为null，则不转换为json字符串。减少无用数据
@Data
public class JsonFmtRsp {

    // pojo时间对象，转换为json时间格式字符串
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    private String nonData;

    @JsonIgnore // 返回的json数据不包含指定字段。有些与接口业务无关的数据不必要返回给前端
    private String securityData;
}
