package org.wjy.easycode.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.wjy.easycode.pojo.vo.BaseReq;

/**
 * springMVC处理各种http请求
 */
@Slf4j
@RestController("/api/v1/reqMethod") // 注解包含@ResponseBody，返回字符串内容
public class RequestMethodController {

    // 处理post请求，body为json格式数据
    @PostMapping("/postJsonBody")
    public void hdlPostReqBodyJson(@RequestBody BaseReq req) {
        log.debug("hdlPostReqBodyJson>>>>" + req.toString());
    }

    // 处理post请求，多参数。前端使用application/x-www-form-urlencoded表单提交
    @PostMapping("/postParams")
    public void hdlPostReqParams(@RequestParam String id, @RequestParam String name) {
        log.debug("hdlPostReqParams");
    }

    // 处理get请求，body为json格式数据。@GetMapping和@RequestBody不能组合使用
    // 请求失败，不符合http协议。get请求参数拼接在url，不会放在body里
    @GetMapping("/getJsonBody1")
    public void hdlGetReqBodyJson1(@RequestBody BaseReq req) {
        log.debug("hdlGetReqBodyJson1>>>>" + req.toString());
    }

    // 处理get请求，body为json格式数据。使用@ModelAttribute接收对象
    @GetMapping("/getJsonBody2")
    public void hdlGetReqBodyJson2(@ModelAttribute BaseReq req) {
        log.debug("hdlGetReqBodyJson2>>>>" + req.toString());
    }

    // 处理get请求，多参数
    @PostMapping("/getParams")
    public void hdlGetReqParams(@RequestParam String id, @RequestParam String name) {
        log.debug("hdlGetReqParams");
    }

}
