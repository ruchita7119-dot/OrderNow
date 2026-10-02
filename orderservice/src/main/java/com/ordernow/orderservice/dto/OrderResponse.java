package com.ordernow.orderservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ordernow.orderservice.entity.OrderStatus;
import com.ordernow.orderservice.entity.PaymentMethod;
import com.ordernow.orderservice.entity.PaymentStatus;

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

    private OrderStatus status;

    private String deliveryAddress;

    private LocalDateTime orderDate;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    private String paymentId;

    private List<OrderItemResponse> orderItems;
}