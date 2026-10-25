package com.ordernow.hotelservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateMenuItemRequest(

        @NotBlank(message = "Item name is required")
        String itemName,

        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than 0")
        BigDecimal price,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Availability is required")
        Boolean isAvailable
) {
}
