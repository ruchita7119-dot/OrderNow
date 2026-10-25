package com.ordernow.orderservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ordernow.orderservice.dto.OrderResponse;
import com.ordernow.orderservice.entity.PaymentStatus;
import com.ordernow.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/internal/api/v1/orders")
@RequiredArgsConstructor
public class InternalOrderController {

    private final OrderService orderService;

    @Value("${internal.service.secret}")
    private String internalSecret;

    private boolean isValidSecret(String secret) {
        return internalSecret.equals(secret);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            @RequestHeader("X-Internal-Service-Secret") String secret) {

        if (!isValidSecret(secret)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PatchMapping("/{id}/payment")
    public ResponseEntity<OrderResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus paymentStatus,
            @RequestParam(required = false) String paymentId,
            @RequestHeader("X-Internal-Service-Secret") String secret) {

        if (!isValidSecret(secret)) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(
                orderService.updatePaymentStatus(
                        id,
                        paymentStatus,
                        paymentId));
    }
}
