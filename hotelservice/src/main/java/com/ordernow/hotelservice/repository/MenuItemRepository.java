package com.ordernow.hotelservice.repository;

import com.ordernow.hotelservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByHotelId(Long hotelId);

    List<MenuItem> findByHotelIdAndIsAvailableTrue(Long hotelId);

    Optional<MenuItem> findByIdAndHotelId(Long id, Long hotelId);
}
