package com.ordernow.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.ordernow.paymentservice.config.InternalServiceFeignConfig;

@FeignClient(
    name = "orderservice",
    url = "${order.service.url}",
    configuration = InternalServiceFeignConfig.class
)
public interface OrderClient {

    @GetMapping("/internal/api/v1/orders/{id}")
    OrderResponse getOrderById(
            @PathVariable("id") Long id);

    @PatchMapping("/internal/api/v1/orders/{id}/payment")
    OrderResponse updatePaymentStatus(
            @PathVariable("id") Long id,
            @RequestParam("paymentStatus") String paymentStatus,
            @RequestParam(value = "paymentId", required = false)
            String paymentId);
}
