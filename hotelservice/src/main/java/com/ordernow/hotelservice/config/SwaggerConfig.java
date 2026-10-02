package com.ordernow.hotelservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI hotelServiceOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("OrderNow Hotel Service API")
                        .description(
                                "REST APIs for managing hotels in the OrderNow application."
                        )
                        .version("1.0.0"));
    }
}