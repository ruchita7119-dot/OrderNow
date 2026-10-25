
package com.ordernow.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class InternalServiceFeignConfig {

    @Value("${internal.service.secret}")
    private String internalSecret;

    @Bean
    public RequestInterceptor internalServiceInterceptor() {
        return requestTemplate -> requestTemplate.header(
            "X-Internal-Service-Secret",
            internalSecret
        );
    }
}
