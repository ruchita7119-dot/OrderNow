package com.ordernow.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ordernow.orderservice.entity.Order;
import com.ordernow.orderservice.entity.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(String userId);

    List<Order> findByHotelId(Long hotelId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByUserIdOrderByOrderDateDesc(String userId);

    List<Order> findByHotelIdOrderByOrderDateDesc(Long hotelId);
}