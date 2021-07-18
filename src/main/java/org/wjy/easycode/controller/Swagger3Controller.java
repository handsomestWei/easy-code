package org.wjy.easycode.controller;

import com.wjy.springbootdemo.pojo.vo.SwaggerRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @Api：用在请求的类上，表示对类的说明
 *     tags="说明该类的作用，可以在UI界面上看到的注解"
 *     value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
 *
 * @ApiOperation：用在请求的方法上，说明方法的用途、作用
 *     value="说明方法的用途、作用"
 *     notes="方法的备注说明"
 *
 * @ApiImplicitParams：用在请求的方法上，表示一组参数说明
 *     @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
 *         name：参数名
 *         value：参数的汉字说明、解释
 *         required：参数是否必须传
 *         paramType：参数放在哪个地方
 *             · header --> 请求参数的获取：@RequestHeader
 *             · query --> 请求参数的获取：@RequestParam
 *             · path（用于restful接口）--> 请求参数的获取：@PathVariable
 *             · div（不常用）
 *             · form（不常用）
 *         dataType：参数类型，默认String，其它值dataType="Integer"
 *         defaultValue：参数的默认值
 *
 * @ApiResponses：用在请求的方法上，表示一组响应
 *     @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
 *         code：数字，例如400
 *         message：信息，例如"请求参数没填好"
 *         response：抛出异常的类
 *
 * @ApiModel：用于响应类上，表示一个返回响应数据的信息
 *             （这种一般用在post创建的时候，使用@RequestBody这样的场景，
 *             请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 *     @ApiModelProperty：用在属性上，描述响应类的属性
 *
 */
@Api(tags = "demo控制器")
@RestController("/api/v1/demo")
public class Swagger3Controller {

    @ApiOperation(value = "sayHello1接口", notes = "备注说明，注意安全")
    @ApiImplicitParams({
            @ApiImplicitParam(name="userId", value="用户id", dataTypeClass = String.class, paramType = "query", example="12345"),
            @ApiImplicitParam(name="userName", value="用户名", dataTypeClass = String.class, paramType = "query", example="asd")
    })
    // 如果使用@RequestMapping，不指定请求方法，则swagger会展示put、delete等
    @GetMapping("/hello1")
    public String hello1(@ApiIgnore @RequestParam Map<String,String> params) {
        return "hello " + params.toString();
    }

    @ApiOperation(value = "sayHello2接口", notes = "无")
    @PostMapping("/hello2")
    public SwaggerRsp hello2(@Parameter(description = "用户id") @RequestParam("userId") String userId) {
        SwaggerRsp rsp = new SwaggerRsp();
        rsp.setUserId(userId);
        return rsp;
    }
}
