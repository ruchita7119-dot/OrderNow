package com.ordernow.orderservice.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long id;
    private Long foodItemId;
    private String foodItemName;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}