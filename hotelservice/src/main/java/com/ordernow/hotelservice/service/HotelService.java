package com.ordernow.hotelservice.service;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface HotelService {

    HotelResponse createHotel(CreateHotelRequest request);

    HotelResponse getHotelById(Long id);

    List<HotelResponse> getAllHotels();

    List<HotelResponse> getHotelsByCity(String city);
    
    List<HotelResponse> getHotelsByName(String Hotelname);

    List<HotelResponse> getActiveHotels();
    
    Page<HotelResponse> getHotels(int page, int size, String sortBy, String direction);
    
    List<HotelResponse> getHotelsByMinimumRating(Double rating);
    
    List<HotelResponse> getVerifiedHotels();
    
    HotelResponse updateHotelVerification(Long id, boolean isVerified);
    
    HotelResponse updateHotelStatus(Long id, boolean isActive);
    
    HotelResponse updateHotel(Long id, CreateHotelRequest request);

    void deleteHotel(Long id);
}