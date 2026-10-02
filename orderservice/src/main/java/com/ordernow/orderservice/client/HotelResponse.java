package com.ordernow.orderservice.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonProperty("isActive")
    private boolean active;

    @JsonProperty("isVerified")
    private boolean verified;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public boolean isActive() {
        return active;
    }

    public boolean isVerified() {
        return verified;
    }
}