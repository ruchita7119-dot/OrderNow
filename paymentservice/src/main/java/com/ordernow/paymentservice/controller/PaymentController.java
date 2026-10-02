package com.ordernow.paymentservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ordernow.paymentservice.dto.CreatePaymentRequest;
import com.ordernow.paymentservice.dto.PaymentResponse;
import com.ordernow.paymentservice.entity.PaymentStatus;
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
            @Valid @RequestBody CreatePaymentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(id));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                paymentService.getPaymentByOrderId(orderId));
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments());
    }

    @PostMapping("/{id}/upi")
    public ResponseEntity<PaymentResponse> processUpiPayment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.processUpiPayment(id));
    }

    @PostMapping("/{id}/cod/complete")
    public ResponseEntity<PaymentResponse> completeCodPayment(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentService.completeCodPayment(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {

        return ResponseEntity.ok(
                paymentService.updatePaymentStatus(id, status));
    }
}