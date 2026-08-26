package com.ordernow.hotelservice.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class HotelResponse {

    private Long id;

    private String hotelName;

    private String description;

    private Long ownerId;

    private String email;

    private String phoneNumber;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private BigDecimal priceForTwo;

    private BigDecimal rating;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private Boolean isActive;

    private Boolean isVerified;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}