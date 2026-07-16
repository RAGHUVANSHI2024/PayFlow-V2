package com.payflow.auth.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI payFlowOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("PayFlow Auth API")
                        .description("Auth Service APIs")
                        .version("v1.0"));
    }
}