package com.ordernow.hotelservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
public class CreateHotelRequest {

    @NotBlank(message = "Hotel name is required")
    @Size(
            min = 2,
            max = 150,
            message = "Hotel name must be between 2 and 150 characters"
    )
    private String hotelName;

    @Size(
            max = 1000,
            message = "Description cannot exceed 1000 characters"
    )
    private String description;

    @NotNull(message = "Owner ID is required")
    private Long ownerId;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain between 10 and 15 digits"
    )
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(
            max = 255,
            message = "Address cannot exceed 255 characters"
    )
    private String address;

    @NotBlank(message = "City is required")
    @Size(
            max = 100,
            message = "City cannot exceed 100 characters"
    )
    private String city;

    @NotBlank(message = "State is required")
    @Size(
            max = 100,
            message = "State cannot exceed 100 characters"
    )
    private String state;

    @NotBlank(message = "Country is required")
    @Size(
            max = 100,
            message = "Country cannot exceed 100 characters"
    )
    private String country;

    @NotBlank(message = "Pincode is required")
    @Pattern(
            regexp = "^[0-9]{6}$",
            message = "Pincode must contain exactly 6 digits"
    )
    private String pincode;

    @NotNull(message = "Price for two is required")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Price for two must be greater than 0"
    )
    private BigDecimal priceForTwo;

    private LocalTime openingTime;

    private LocalTime closingTime;
}