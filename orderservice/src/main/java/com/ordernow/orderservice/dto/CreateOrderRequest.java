
package com.ordernow.orderservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordernow.orderservice.entity.PaymentMethod;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    // Set internally by OrderController from the Gateway header.
    // Ignore any userId supplied by the frontend.
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String userId;

    @NotNull(message = "Hotel ID is required")
    private Long hotelId;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemRequest> orderItems;
}
