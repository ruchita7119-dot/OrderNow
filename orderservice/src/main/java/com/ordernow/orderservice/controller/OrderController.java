
package com.ordernow.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ordernow.orderservice.dto.CreateOrderRequest;
import com.ordernow.orderservice.dto.OrderResponse;
import com.ordernow.orderservice.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        request.setUserId(userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        return ResponseEntity.ok(
                orderService.getOrderById(id, userId));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUserId(userId));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable Long id,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        orderService.cancelOrder(id, userId);

        return ResponseEntity.noContent().build();
    }
}
