package org.wjy.easycode.pojo.vo;

import com.wjy.springbootdemo.service.IRspCode;
import lombok.Data;
import lombok.experimental.Accessors;

// restful风格的pojo返回对象
// 使用泛型
@Data
@Accessors(chain = true)
public class BaseRsp<T> {

    // 返回码
    private String code;
    // 返回消息
    private String msg;
    // 数据
    private T data;

    public BaseRsp(IRspCode code, T data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }
}
