package com.ordernow.paymentservice.client;

import java.math.BigDecimal;

import com.ordernow.paymentservice.entity.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private String userId;

    private Long hotelId;

    private BigDecimal totalAmount;

    private String status;

    private String deliveryAddress;

    private PaymentMethod paymentMethod;

    private String paymentStatus;

    private String paymentId;
}