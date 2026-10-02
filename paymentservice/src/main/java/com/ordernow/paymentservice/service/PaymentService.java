package com.ordernow.paymentservice.service;

import java.util.List;

import com.ordernow.paymentservice.dto.CreatePaymentRequest;
import com.ordernow.paymentservice.dto.PaymentResponse;
import com.ordernow.paymentservice.entity.PaymentStatus;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentById(Long id);

    PaymentResponse getPaymentByOrderId(Long orderId);

    List<PaymentResponse> getAllPayments();

    PaymentResponse updatePaymentStatus(Long id, PaymentStatus status);

    PaymentResponse processUpiPayment(Long paymentId);

    PaymentResponse completeCodPayment(Long paymentId);
}