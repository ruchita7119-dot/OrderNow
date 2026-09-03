package com.ordernow.hotelservice.repository;

import com.ordernow.hotelservice.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Basic searches
    List<Hotel> findByCityIgnoreCase(String city);

    List<Hotel> findByNameContainingIgnoreCase(String name);

    // Active hotels
    List<Hotel> findByIsActiveTrue();

    // Verified hotels
    List<Hotel> findByIsVerifiedTrue();

    // Rating filter
    List<Hotel> findByRatingGreaterThanEqual(Double rating);

    // Pagination for active hotels
    Page<Hotel> findByIsActiveTrue(Pageable pageable);

    // Active-only searches
    List<Hotel> findByCityIgnoreCaseAndIsActiveTrue(String city);

    List<Hotel> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    List<Hotel> findByRatingGreaterThanEqualAndIsActiveTrue(Double rating);

    List<Hotel> findByIsVerifiedTrueAndIsActiveTrue();
}