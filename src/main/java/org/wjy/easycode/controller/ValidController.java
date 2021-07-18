package org.wjy.easycode.controller;

import com.wjy.springbootdemo.enums.RspCodeEnum;
import com.wjy.springbootdemo.pojo.vo.BaseRsp;
import com.wjy.springbootdemo.pojo.vo.ValidReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 使用注解校验请求参数
 */
@Validated // 注解作用在控制器级别才能对@RequestParam的校验生效，抛出异常
@RestController("api/v1/valid")
@Slf4j // 日志
public class ValidController {

    // 在不配置异常捕获时，方法签名入参要声明BindingResult一起使用。否则校验不生效
    @PostMapping("/body1")
    public String body1(@Valid @RequestBody ValidReq validReq, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldError().getDefaultMessage();
        }
        return "body1 = " + validReq.toString();
    }

    // 配置了全局异常捕获
    @PostMapping("/body2")
    public BaseRsp body2(@Valid @RequestBody ValidReq validReq) {
        log.debug("/body2 req data = " + validReq.toString());

        BaseRsp<String> rsp = new BaseRsp(RspCodeEnum.RSP_SUCCESS, "data");
        return rsp;
    }

    // 需要把注解@Validated放在控制器级别，并捕获ConstraintViolationException异常才生效
    // Integer整型参数空校验使用@NotNull
    @GetMapping("/param")
    public String param(@NotNull @RequestParam("id") Integer id) {
        return "param = " + id;
    }

    // List<E>泛型参数空校验使用@NotNull
    @GetMapping("/list")
    public String list(@NotEmpty @RequestParam("ids") List<String> ids) {
        return "param = " + ids;
    }
}
