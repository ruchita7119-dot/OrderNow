package com.ordernow.paymentservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordernow.paymentservice.entity.Payment;
import com.ordernow.paymentservice.entity.PaymentStatus;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);

    Optional<Payment> findByTransactionId(String transactionId);

    boolean existsByOrderId(Long orderId);

    boolean existsByTransactionId(String transactionId);

    java.util.List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}