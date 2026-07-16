package com.payflow.wallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI payflowOpenApi(){

        return new OpenAPI()
                .info(new Info()
                        .title("Payflow Wallet Api")
                        .description("Wallet Service Api")
                        .version("v1.0"));

    }
}
