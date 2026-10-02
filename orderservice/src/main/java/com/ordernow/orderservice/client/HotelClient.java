package com.ordernow.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "hotelservice",
        url = "${hotel.service.url}"
)
public interface HotelClient {

    @GetMapping("/api/v1/hotels/{id}")
    HotelResponse getHotelById(@PathVariable("id") Long id);
}