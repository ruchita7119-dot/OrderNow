package com.ordernow.hotelservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Getter
@Setter
public class CreateHotelRequest {

    @NotBlank
    @Size(max = 150)
    private String hotelName;

    private String description;

    @NotNull
    private Long ownerId;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 10, max = 15)
    private String phoneNumber;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String country;

    @NotBlank
    @Size(max = 10)
    private String pincode;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal priceForTwo;

    private LocalTime openingTime;

    private LocalTime closingTime;
}