package com.ordernow.orderservice.service;

import java.util.List;

import com.ordernow.orderservice.dto.CreateOrderRequest;
import com.ordernow.orderservice.dto.OrderResponse;
import com.ordernow.orderservice.entity.OrderStatus;
import com.ordernow.orderservice.entity.PaymentStatus;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    List<OrderResponse> getOrdersByUserId(String userId);

    List<OrderResponse> getOrdersByHotelId(Long hotelId);

    List<OrderResponse> getOrdersByStatus(OrderStatus status);

    OrderResponse updateOrderStatus(Long id, OrderStatus status);

    OrderResponse updatePaymentStatus(
            Long id,
            PaymentStatus paymentStatus,
            String paymentId);

    void cancelOrder(Long id);

    void deleteOrder(Long id);
}