package com.ordernow.paymentservice.mapper;

import org.springframework.stereotype.Component;

import com.ordernow.paymentservice.dto.CreatePaymentRequest;
import com.ordernow.paymentservice.dto.PaymentResponse;
import com.ordernow.paymentservice.entity.Payment;
import com.ordernow.paymentservice.entity.PaymentStatus;

@Component
public class PaymentMapper {

    public Payment toEntity(CreatePaymentRequest request) {

        return Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .upiId(request.getUpiId())
                .build();
    }

    public PaymentResponse toResponse(Payment payment) {

        return new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getUserId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getUpiId(),
                payment.getPaymentDate()
        );
    }
}