package com.ordernow.hotelservice.service;

import com.ordernow.hotelservice.dto.request.CreateMenuItemRequest;
import com.ordernow.hotelservice.dto.request.UpdateMenuItemRequest;
import com.ordernow.hotelservice.dto.response.MenuItemResponse;

import java.util.List;

public interface MenuItemService {

    MenuItemResponse createMenuItem(
            Long hotelId,
            CreateMenuItemRequest request
    );

    List<MenuItemResponse> getMenuItemsByHotel(Long hotelId);

    List<MenuItemResponse> getAvailableMenuItemsByHotel(Long hotelId);

    MenuItemResponse getMenuItemById(
            Long hotelId,
            Long menuItemId
    );

    MenuItemResponse updateMenuItem(
            Long hotelId,
            Long menuItemId,
            UpdateMenuItemRequest request
    );

    void deleteMenuItem(
            Long hotelId,
            Long menuItemId
    );
}
