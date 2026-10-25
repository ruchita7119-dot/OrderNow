package com.ordernow.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ordernow.orderservice.config.InternalServiceFeignConfig;

@FeignClient(
    name = "userservice",
    url = "${user.service.url}",
    configuration = InternalServiceFeignConfig.class
)
public interface UserClient {

    @GetMapping("/internal/api/v1/users/{id}")
    UserResponse getUserById(@PathVariable("id") String id);
}
