
package com.ordernow.paymentservice.service;

import java.util.List;

import com.ordernow.paymentservice.dto.CreatePaymentRequest;
import com.ordernow.paymentservice.dto.PaymentResponse;
import com.ordernow.paymentservice.entity.PaymentStatus;

public interface PaymentService {

    PaymentResponse createPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentById(Long id, String userId);

    PaymentResponse getPaymentByOrderId(Long orderId, String userId);

    List<PaymentResponse> getPaymentsByUserId(String userId);

    PaymentResponse processUpiPayment(Long paymentId, String userId);

    PaymentResponse completeCodPayment(Long paymentId, String userId);

    // Keep this for trusted administrative/internal operations only.
    PaymentResponse updatePaymentStatus(Long id, PaymentStatus status);
}
