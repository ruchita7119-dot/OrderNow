package com.ordernow.hotelservice.mapper;

import com.ordernow.hotelservice.dto.request.CreateMenuItemRequest;
import com.ordernow.hotelservice.dto.request.UpdateMenuItemRequest;
import com.ordernow.hotelservice.dto.response.MenuItemResponse;
import com.ordernow.hotelservice.entity.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItem toEntity(CreateMenuItemRequest request, Long hotelId) {

        return MenuItem.builder()
                .hotelId(hotelId)
                .itemName(request.itemName())
                .description(request.description())
                .price(request.price())
                .category(request.category())
                .isAvailable(
                        request.isAvailable() != null
                                ? request.isAvailable()
                                : true
                )
                .build();
    }

    public void updateEntity(MenuItem menuItem, UpdateMenuItemRequest request) {

        menuItem.setItemName(request.itemName());
        menuItem.setDescription(request.description());
        menuItem.setPrice(request.price());
        menuItem.setCategory(request.category());
        menuItem.setIsAvailable(request.isAvailable());
    }

    public MenuItemResponse toResponse(MenuItem menuItem) {

        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getHotelId(),
                menuItem.getItemName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.getCategory(),
                menuItem.getIsAvailable(),
                menuItem.getCreatedAt(),
                menuItem.getUpdatedAt()
        );
    }
}
