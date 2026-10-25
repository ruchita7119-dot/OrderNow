
package com.ordernow.paymentservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ordernow.paymentservice.dto.CreatePaymentRequest;
import com.ordernow.paymentservice.dto.PaymentResponse;
import com.ordernow.paymentservice.service.PaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        request.setUserId(userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id, userId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId, userId));
    }

    @GetMapping("/my-payments")
    public ResponseEntity<?> getMyPayments(
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByUserId(userId));
    }

    @PostMapping("/{id}/upi")
    public ResponseEntity<PaymentResponse> processUpiPayment(
            @PathVariable Long id,
            @RequestHeader("X-Authenticated-User-Id") String userId) {

        return ResponseEntity.ok(
                paymentService.processUpiPayment(id, userId));
    }
}
