package com.jay.cfs_cashbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.Socket;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig { // 配置swagger
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jay.cfs_cashbook.controller")) // 扫描包
//                .paths(PathSelectors.ant("/xxx/**"))  拦截器
                .build();
    }

    // 配置swagger信息=ApiInfo
    private ApiInfo apiInfo() {
        // 作者信息
        Contact contact = new Contact("JunjieZhang", "", "ZhangJunJie1107@qq.com");
        return new ApiInfo(
                "个人财务管理微信小程序API",
                "该系统的全部API接口调用及其实例",
                "1.0",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
