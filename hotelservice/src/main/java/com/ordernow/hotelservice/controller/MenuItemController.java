package com.ordernow.hotelservice.controller;

import com.ordernow.hotelservice.dto.request.CreateMenuItemRequest;
import com.ordernow.hotelservice.dto.request.UpdateMenuItemRequest;
import com.ordernow.hotelservice.dto.response.MenuItemResponse;
import com.ordernow.hotelservice.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels/{hotelId}/menu")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @PathVariable Long hotelId,
            @Valid @RequestBody CreateMenuItemRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(menuItemService.createMenuItem(hotelId, request));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponse>> getMenuItems(
            @PathVariable Long hotelId,
            @RequestParam(required = false, defaultValue = "false") boolean availableOnly
    ) {

        if (availableOnly) {
            return ResponseEntity.ok(
                    menuItemService.getAvailableMenuItemsByHotel(hotelId)
            );
        }

        return ResponseEntity.ok(
                menuItemService.getMenuItemsByHotel(hotelId)
        );
    }

    @GetMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> getMenuItemById(
            @PathVariable Long hotelId,
            @PathVariable Long menuItemId
    ) {
        return ResponseEntity.ok(
                menuItemService.getMenuItemById(hotelId, menuItemId)
        );
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long hotelId,
            @PathVariable Long menuItemId,
            @Valid @RequestBody UpdateMenuItemRequest request
    ) {
        return ResponseEntity.ok(
                menuItemService.updateMenuItem(
                        hotelId,
                        menuItemId,
                        request
                )
        );
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable Long hotelId,
            @PathVariable Long menuItemId
    ) {
        menuItemService.deleteMenuItem(hotelId, menuItemId);

        return ResponseEntity.noContent().build();
    }
}
