package com.ordernow.hotelservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MenuItemResponse(

        Long id,

        Long hotelId,

        String itemName,

        String description,

        BigDecimal price,

        String category,

        Boolean isAvailable,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
