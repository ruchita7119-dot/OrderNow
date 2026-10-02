package com.ordernow.orderservice.service.impl;

import java.time.Duration;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ordernow.orderservice.client.HotelClient;
import com.ordernow.orderservice.client.HotelResponse;
import com.ordernow.orderservice.client.UserClient;
import com.ordernow.orderservice.client.UserResponse;
import com.ordernow.orderservice.dto.CreateOrderRequest;
import com.ordernow.orderservice.dto.OrderResponse;
import com.ordernow.orderservice.entity.Order;
import com.ordernow.orderservice.entity.OrderStatus;
import com.ordernow.orderservice.entity.PaymentMethod;
import com.ordernow.orderservice.entity.PaymentStatus;
import com.ordernow.orderservice.exception.ResourceNotFoundException;
import com.ordernow.orderservice.mapper.OrderMapper;
import com.ordernow.orderservice.repository.OrderRepository;
import com.ordernow.orderservice.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserClient userClient;
    private final HotelClient hotelClient;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String ORDER_CACHE_PREFIX = "order:";
    private static final Duration CACHE_DURATION = Duration.ofMinutes(10);

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {

        validateUser(request.getUserId());
        validateHotel(request.getHotelId());

        Order order = orderMapper.toEntity(request);

        if (request.getPaymentMethod() == PaymentMethod.COD) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        } else if (request.getPaymentMethod() == PaymentMethod.UPI) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        }

        Order savedOrder = orderRepository.save(order);

        OrderResponse response = orderMapper.toResponse(savedOrder);

        String cacheKey = ORDER_CACHE_PREFIX + savedOrder.getId();

        redisTemplate.opsForValue()
                .set(cacheKey, response, CACHE_DURATION);

        return response;
    }

    private void validateUser(String userId) {

        try {
            UserResponse user = userClient.getUserById(userId);

            if (user == null || user.getId() == null) {
                throw new ResourceNotFoundException(
                        "User not found with id: " + userId);
            }

        } catch (ResourceNotFoundException ex) {
            throw ex;

        } catch (Exception ex) {
            throw new ResourceNotFoundException(
                    "Unable to find user with id: " + userId);
        }
    }
    private void validateHotel(Long hotelId) {

        try {
            HotelResponse hotel = hotelClient.getHotelById(hotelId);

            if (hotel == null || hotel.getId() == null) {
                throw new ResourceNotFoundException(
                        "Hotel not found with id: " + hotelId);
            }

            if (!hotel.isActive()) {
                throw new IllegalArgumentException(
                        "Hotel is currently inactive");
            }

        } catch (ResourceNotFoundException ex) {
            throw ex;

        } catch (IllegalArgumentException ex) {
            throw ex;

        } catch (Exception ex) {
            ex.printStackTrace();

            throw new RuntimeException(
                    "Hotel service call failed: " + ex.getMessage(), ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long id) {

        String cacheKey = ORDER_CACHE_PREFIX + id;

        Object cachedOrder =
                redisTemplate.opsForValue().get(cacheKey);

        if (cachedOrder instanceof OrderResponse response) {
            return response;
        }

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));

        OrderResponse response = orderMapper.toResponse(order);

        redisTemplate.opsForValue()
                .set(cacheKey, response, CACHE_DURATION);

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByUserId(String userId) {

        return orderRepository
                .findByUserIdOrderByOrderDateDesc(userId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByHotelId(Long hotelId) {

        return orderRepository
                .findByHotelIdOrderByOrderDateDesc(hotelId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {

        return orderRepository
                .findByStatus(status)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse updateOrderStatus(
            Long id,
            OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalArgumentException(
                    "Delivered order status cannot be changed");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Cancelled order status cannot be changed");
        }

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        String cacheKey = ORDER_CACHE_PREFIX + id;

        redisTemplate.delete(cacheKey);

        OrderResponse response =
                orderMapper.toResponse(updatedOrder);

        redisTemplate.opsForValue()
                .set(cacheKey, response, CACHE_DURATION);

        return response;
    }

    @Override
    public OrderResponse updatePaymentStatus(
            Long id,
            PaymentStatus paymentStatus,
            String paymentId) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Cancelled order payment cannot be updated");
        }

        order.setPaymentStatus(paymentStatus);

        if (paymentId != null && !paymentId.isBlank()) {
            order.setPaymentId(paymentId);
        }

        Order updatedOrder = orderRepository.save(order);

        String cacheKey = ORDER_CACHE_PREFIX + id;

        redisTemplate.delete(cacheKey);

        OrderResponse response =
                orderMapper.toResponse(updatedOrder);

        redisTemplate.opsForValue()
                .set(cacheKey, response, CACHE_DURATION);

        return response;
    }

    @Override
    public void cancelOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));

        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new IllegalArgumentException(
                    "Delivered order cannot be cancelled");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalArgumentException(
                    "Order is already cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        redisTemplate.delete(ORDER_CACHE_PREFIX + id);
    }

    @Override
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id));

        orderRepository.delete(order);

        redisTemplate.delete(ORDER_CACHE_PREFIX + id);
    }
}