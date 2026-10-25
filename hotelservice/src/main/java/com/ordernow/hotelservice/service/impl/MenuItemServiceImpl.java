package com.ordernow.hotelservice.service.impl;

import com.ordernow.hotelservice.dto.request.CreateMenuItemRequest;
import com.ordernow.hotelservice.dto.request.UpdateMenuItemRequest;
import com.ordernow.hotelservice.dto.response.MenuItemResponse;
import com.ordernow.hotelservice.entity.MenuItem;
import com.ordernow.hotelservice.exception.ResourceNotFoundException;
import com.ordernow.hotelservice.mapper.MenuItemMapper;
import com.ordernow.hotelservice.repository.HotelRepository;
import com.ordernow.hotelservice.repository.MenuItemRepository;
import com.ordernow.hotelservice.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final HotelRepository hotelRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public MenuItemResponse createMenuItem(
            Long hotelId,
            CreateMenuItemRequest request
    ) {

        validateHotelExists(hotelId);

        MenuItem menuItem = menuItemMapper.toEntity(request, hotelId);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toResponse(savedMenuItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getMenuItemsByHotel(Long hotelId) {

        validateHotelExists(hotelId);

        return menuItemRepository.findByHotelId(hotelId)
                .stream()
                .map(menuItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenuItemResponse> getAvailableMenuItemsByHotel(Long hotelId) {

        validateHotelExists(hotelId);

        return menuItemRepository.findByHotelIdAndIsAvailableTrue(hotelId)
                .stream()
                .map(menuItemMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuItemResponse getMenuItemById(
            Long hotelId,
            Long menuItemId
    ) {

        validateHotelExists(hotelId);

        MenuItem menuItem = menuItemRepository
                .findByIdAndHotelId(menuItemId, hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Menu item not found with id: " + menuItemId
                        )
                );

        return menuItemMapper.toResponse(menuItem);
    }

    @Override
    public MenuItemResponse updateMenuItem(
            Long hotelId,
            Long menuItemId,
            UpdateMenuItemRequest request
    ) {

        validateHotelExists(hotelId);

        MenuItem menuItem = menuItemRepository
                .findByIdAndHotelId(menuItemId, hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Menu item not found with id: " + menuItemId
                        )
                );

        menuItemMapper.updateEntity(menuItem, request);

        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toResponse(updatedMenuItem);
    }

    @Override
    public void deleteMenuItem(
            Long hotelId,
            Long menuItemId
    ) {

        validateHotelExists(hotelId);

        MenuItem menuItem = menuItemRepository
                .findByIdAndHotelId(menuItemId, hotelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Menu item not found with id: " + menuItemId
                        )
                );

        menuItemRepository.delete(menuItem);
    }

    private void validateHotelExists(Long hotelId) {

        if (!hotelRepository.existsById(hotelId)) {
            throw new ResourceNotFoundException(
                    "Hotel not found with id: " + hotelId
            );
        }
    }
}
