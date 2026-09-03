package com.ordernow.hotelservice.repository;

import com.ordernow.hotelservice.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByCityIgnoreCase(String city);

    List<Hotel> findByHotelNameContainingIgnoreCase(String hotelName);

    List<Hotel> findByIsActiveTrue();

    List<Hotel> findByIsVerifiedTrue();

    List<Hotel> findByRatingGreaterThanEqual(BigDecimal rating);

    Page<Hotel> findByIsActiveTrue(Pageable pageable);

    List<Hotel> findByCityIgnoreCaseAndIsActiveTrue(String city);

    List<Hotel> findByHotelNameContainingIgnoreCaseAndIsActiveTrue(
            String hotelName
    );

    List<Hotel> findByRatingGreaterThanEqualAndIsActiveTrue(
            BigDecimal rating
    );

    List<Hotel> findByIsVerifiedTrueAndIsActiveTrue();
}