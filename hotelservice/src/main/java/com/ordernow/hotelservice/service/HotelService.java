package com.ordernow.hotelservice.service;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {

    HotelResponse createHotel(CreateHotelRequest request);

    HotelResponse getHotelById(Long id);

    List<HotelResponse> getAllHotels();

    HotelResponse updateHotel(Long id, CreateHotelRequest request);

    void deleteHotel(Long id);
}