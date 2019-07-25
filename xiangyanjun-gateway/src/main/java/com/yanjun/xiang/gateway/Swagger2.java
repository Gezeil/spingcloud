//package com.yanjun.xiang.gateway;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.UiConfiguration;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @author XiangYanJun
// * @Date 2019/7/25 0025.
// */
//@Configuration
//@EnableSwagger2
//public class Swagger2 {
//
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo());
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("分布式购物系统")
//                .description("购物系统接口文档说明")
//                .termsOfServiceUrl("http://localhost:8884")
//                .contact(new Contact("vker", "", "6492178@gmail.com"))
//                .version("1.0")
//                .build();
//    }
//
//    @Bean
//    UiConfiguration uiConfig() {
//        return new UiConfiguration(null, "list", "alpha", "schema",
//                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
//    }
//
//
//
//
//}
