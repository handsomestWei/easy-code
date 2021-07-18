package org.wjy.easycode.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wjy.springbootdemo.pojo.vo.JsonFmtReq;
import com.wjy.springbootdemo.pojo.vo.JsonFmtRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * request body json格式化处理
 */
@Slf4j
@RestController("/api/v1/js")
public class JsonFormatController {

    // 方法签名返回json格式，无需强制声明string类型，指定pojo即可
    @PostMapping("/fmt1")
    public JsonFmtRsp jsonFormat1(@RequestBody JsonFmtReq req) {
        try {
            System.out.println("/fmt1 pojo data = " + new ObjectMapper().writeValueAsString(req));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonFmtRsp rsp = new JsonFmtRsp();
        rsp.setEndTime(new Date());
        rsp.setSecurityData("security");
        return rsp;
    }

    // 使用produces声明返回数据为json格式，去掉字符串的双引号转义字符。或使用JSON.toJSONString
    @GetMapping(value = "/fmt2", produces = "application/json")
    public Object jsonFormat2() {
        String[] list = {"asd", "qwe", "ccc"};
        return list;
//        return JSON.toJSONString(list);
    }
}
