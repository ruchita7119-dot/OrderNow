package com.ordernow.paymentservice.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordernow.paymentservice.client.OrderClient;
import com.ordernow.paymentservice.client.OrderResponse;
import com.ordernow.paymentservice.dto.CreatePaymentRequest;
import com.ordernow.paymentservice.dto.PaymentResponse;
import com.ordernow.paymentservice.entity.Payment;
import com.ordernow.paymentservice.entity.PaymentMethod;
import com.ordernow.paymentservice.entity.PaymentStatus;
import com.ordernow.paymentservice.exception.ResourceNotFoundException;
import com.ordernow.paymentservice.mapper.PaymentMapper;
import com.ordernow.paymentservice.repository.PaymentRepository;
import com.ordernow.paymentservice.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderClient orderClient;

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest request) {

        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new IllegalArgumentException(
                    "Payment already exists for order: "
                            + request.getOrderId());
        }

        OrderResponse order = getOrder(request.getOrderId());

        if (order.getUserId() == null
                || !order.getUserId().equals(request.getUserId())) {

            throw new IllegalArgumentException(
                    "User does not match the order");
        }

        if (order.getTotalAmount() == null
                || request.getAmount() == null
                || order.getTotalAmount()
                        .compareTo(request.getAmount()) != 0) {

            throw new IllegalArgumentException(
                    "Payment amount does not match order amount");
        }

        if (order.getPaymentMethod() != request.getPaymentMethod()) {

            throw new IllegalArgumentException(
                    "Payment method does not match the order");
        }

        if (request.getPaymentMethod() == PaymentMethod.UPI) {

            if (request.getUpiId() == null
                    || request.getUpiId().isBlank()) {

                throw new IllegalArgumentException(
                        "UPI ID is required for UPI payment");
            }

        } else if (request.getPaymentMethod() == PaymentMethod.COD) {

            request.setUpiId(null);
        }

        Payment payment = paymentMapper.toEntity(request);

        payment.setPaymentStatus(PaymentStatus.PENDING);

        Payment savedPayment =
                paymentRepository.save(payment);

        // Synchronize Order Service
        orderClient.updatePaymentStatus(
                savedPayment.getOrderId(),
                PaymentStatus.PENDING.name(),
                null);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id));

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found for order: "
                                        + orderId));

        return paymentMapper.toResponse(payment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentResponse processUpiPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: "
                                        + paymentId));

        if (payment.getPaymentMethod() != PaymentMethod.UPI) {
            throw new IllegalArgumentException(
                    "Only UPI payments can be processed through this endpoint");
        }

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return paymentMapper.toResponse(payment);
        }

        if (payment.getPaymentStatus() == PaymentStatus.FAILED) {
            throw new IllegalArgumentException(
                    "Payment has already failed");
        }

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        payment.setTransactionId(
                "UPI-"
                        + UUID.randomUUID()
                                .toString()
                                .replace("-", "")
                                .substring(0, 16)
                                .toUpperCase());

        Payment updatedPayment =
                paymentRepository.save(payment);

        // Synchronize Order Service
        orderClient.updatePaymentStatus(
                payment.getOrderId(),
                PaymentStatus.SUCCESS.name(),
                payment.getTransactionId());

        return paymentMapper.toResponse(updatedPayment);
    }

    @Override
    public PaymentResponse completeCodPayment(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: "
                                        + paymentId));

        if (payment.getPaymentMethod() != PaymentMethod.COD) {
            throw new IllegalArgumentException(
                    "Only COD payments can be completed through this endpoint");
        }

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return paymentMapper.toResponse(payment);
        }

        if (payment.getPaymentStatus() == PaymentStatus.FAILED) {
            throw new IllegalArgumentException(
                    "COD payment has already failed");
        }

        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        payment.setTransactionId(
                "COD-"
                        + UUID.randomUUID()
                                .toString()
                                .replace("-", "")
                                .substring(0, 16)
                                .toUpperCase());

        Payment updatedPayment =
                paymentRepository.save(payment);

        // Synchronize Order Service
        orderClient.updatePaymentStatus(
                payment.getOrderId(),
                PaymentStatus.SUCCESS.name(),
                payment.getTransactionId());

        return paymentMapper.toResponse(updatedPayment);
    }

    @Override
    public PaymentResponse updatePaymentStatus(
            Long id,
            PaymentStatus status) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment not found with id: " + id));

        if (payment.getPaymentStatus() == PaymentStatus.SUCCESS
                && status != PaymentStatus.SUCCESS) {

            throw new IllegalArgumentException(
                    "Successful payment status cannot be changed");
        }

        payment.setPaymentStatus(status);

        if (status == PaymentStatus.SUCCESS
                && payment.getTransactionId() == null) {

            String prefix =
                    payment.getPaymentMethod() == PaymentMethod.UPI
                            ? "UPI-"
                            : "COD-";

            payment.setTransactionId(
                    prefix
                            + UUID.randomUUID()
                                    .toString()
                                    .replace("-", "")
                                    .substring(0, 16)
                                    .toUpperCase());
        }

        Payment updatedPayment =
                paymentRepository.save(payment);

        // Synchronize Order Service
        orderClient.updatePaymentStatus(
                payment.getOrderId(),
                status.name(),
                payment.getTransactionId());

        return paymentMapper.toResponse(updatedPayment);
    }

    private OrderResponse getOrder(Long orderId) {

        try {

            OrderResponse order =
                    orderClient.getOrderById(orderId);

            if (order == null || order.getId() == null) {

                throw new ResourceNotFoundException(
                        "Order not found with id: " + orderId);
            }

            return order;

        } catch (ResourceNotFoundException ex) {

            throw ex;

        } catch (Exception ex) {

            throw new ResourceNotFoundException(
                    "Unable to find order with id: " + orderId);
        }
    }
}