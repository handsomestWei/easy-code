package org.wjy.easycode.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

// 启用swaggers
@EnableOpenApi
@Configuration
public class Swagger3Config {

    @Bean
    public Docket createRestApi() {
        // 返回文档摘要信息
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) // 扫描全部ApiOperation声明的接口
//                .apis(RequestHandlerSelectors.basePackage("org.wjy.xx2.xxx").or(
//                        RequestHandlerSelectors.basePackage("org.wjy.xx1.xxx")
//                )) // 按指定包名扫描，在不同包下可用or连接，展示需要暴露的接口
                .paths(PathSelectors.any())
                .build();
//                .globalRequestParameters(getGlobalRequestParameters())
//                .globalResponses(HttpMethod.GET, getGlobalResonseMessage())
//                .globalResponses(HttpMethod.POST, getGlobalResonseMessage());
    }

    // 生成接口信息，包括标题、联系人等
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("如有疑问，请联系xxx")
                .contact(new Contact("wjy", "https://xxx", "xxx@qq.com"))
                .version("1.0")
                .build();
    }

    // 生成全局通用参数
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("version")
                .description("客户端的版本号")
                .required(true)
                .in(ParameterType.QUERY)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return parameters;
    }

    // 生成通用响应信息
    private List<Response> getGlobalResonseMessage() {
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder().code("404").description("找不到资源").build());
        return responseList;
    }
}
