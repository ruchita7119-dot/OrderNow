package com.ordernow.hotelservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required")
    @Size(max = 150, message = "Hotel name must not exceed 150 characters")
    private String hotelName;

    private String description;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Size(max = 20, message = "Phone number must not exceed 20 characters")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String state;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @NotBlank(message = "Pincode is required")
    @Size(max = 10, message = "Pincode must not exceed 10 characters")
    private String pincode;

    @NotNull(message = "Price for two is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price for two cannot be negative")
    private BigDecimal priceForTwo;

    @DecimalMin(value = "0.0", inclusive = true, message = "Rating cannot be negative")
    @DecimalMax(value = "5.0", inclusive = true, message = "Rating cannot exceed 5")
    private BigDecimal rating;

    private LocalTime openingTime;

    private LocalTime closingTime;

    // =========================
    // Frontend / Restaurant Data
    // =========================

    private List<String> cuisines;

    private String distance;

    private String offer;

    private String image;
}

