package com.ordernow.orderservice.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ordernow.orderservice.dto.CreateOrderRequest;
import com.ordernow.orderservice.dto.OrderItemRequest;
import com.ordernow.orderservice.dto.OrderItemResponse;
import com.ordernow.orderservice.dto.OrderResponse;
import com.ordernow.orderservice.entity.Order;
import com.ordernow.orderservice.entity.OrderItem;
import com.ordernow.orderservice.entity.OrderStatus;
import com.ordernow.orderservice.entity.PaymentStatus;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderRequest request) {

        Order order = Order.builder()
                .userId(request.getUserId())
                .hotelId(request.getHotelId())
                .deliveryAddress(request.getDeliveryAddress())
                .paymentMethod(request.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .status(OrderStatus.PLACED)
                .totalAmount(BigDecimal.ZERO)
                .build();

        List<OrderItem> items = request.getOrderItems()
                .stream()
                .map(itemRequest -> toOrderItem(itemRequest, order))
                .toList();

        order.setOrderItems(items);

        BigDecimal total = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total);

        return order;
    }

    private OrderItem toOrderItem(
            OrderItemRequest request,
            Order order) {

        BigDecimal subtotal = request.getPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));

        return OrderItem.builder()
                .foodItemId(request.getFoodItemId())
                .foodItemName(request.getFoodItemName())
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .subtotal(subtotal)
                .order(order)
                .build();
    }

    public OrderResponse toResponse(Order order) {

        List<OrderItemResponse> itemResponses = order.getOrderItems()
                .stream()
                .map(this::toItemResponse)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getHotelId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getDeliveryAddress(),
                order.getOrderDate(),
                order.getPaymentMethod(),
                order.getPaymentStatus(),
                order.getPaymentId(),
                itemResponses
        );
    }

    private OrderItemResponse toItemResponse(OrderItem item) {

        return new OrderItemResponse(
                item.getId(),
                item.getFoodItemId(),
                item.getFoodItemName(),
                item.getQuantity(),
                item.getPrice(),
                item.getSubtotal()
        );
    }
}