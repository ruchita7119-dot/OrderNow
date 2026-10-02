package com.ordernow.paymentservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ordernow.paymentservice.entity.PaymentMethod;
import com.ordernow.paymentservice.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private Long id;

    private Long orderId;

    private String userId;

    private BigDecimal amount;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private String upiId;

    private LocalDateTime paymentDate;
}