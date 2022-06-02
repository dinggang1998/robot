package com.learn.robot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置类
 */
//@ConditionalOnExpression("'${swagger.enable}' == 'true'")
@Configuration
@EnableSwagger2
public class RestApiConfig {

    private @Value("${swagger.base-package}")
    String basePackage;
    private @Value("${swagger.title}")
    String title;
    private @Value("${swagger.description}")
    String description;
    private @Value("${swagger.terms-of-service-url}")
    String termsOfServiceUrl;
    private @Value("${swagger.contact}")
    String contact;
    private @Value("${swagger.version}")
    String version;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(contact)
                .version(version)
                .build();
    }

}
