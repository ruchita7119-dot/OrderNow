package com.ordernow.hotelservice.mapper;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import com.ordernow.hotelservice.entity.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public Hotel toEntity(CreateHotelRequest request) {

        Hotel hotel = new Hotel();

        hotel.setHotelName(request.getHotelName());
        hotel.setDescription(request.getDescription());
        hotel.setOwnerId(request.getOwnerId());
        hotel.setEmail(request.getEmail());
        hotel.setPhoneNumber(request.getPhoneNumber());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setState(request.getState());
        hotel.setCountry(request.getCountry());
        hotel.setPincode(request.getPincode());
        hotel.setPriceForTwo(request.getPriceForTwo());
        hotel.setOpeningTime(request.getOpeningTime());
        hotel.setClosingTime(request.getClosingTime());

        return hotel;
    }

    public HotelResponse toResponse(Hotel hotel) {

        HotelResponse response = new HotelResponse();

        response.setId(hotel.getId());
        response.setHotelName(hotel.getHotelName());
        response.setDescription(hotel.getDescription());
        response.setOwnerId(hotel.getOwnerId());
        response.setEmail(hotel.getEmail());
        response.setPhoneNumber(hotel.getPhoneNumber());
        response.setAddress(hotel.getAddress());
        response.setCity(hotel.getCity());
        response.setState(hotel.getState());
        response.setCountry(hotel.getCountry());
        response.setPincode(hotel.getPincode());
        response.setPriceForTwo(hotel.getPriceForTwo());
        response.setRating(hotel.getRating());
        response.setOpeningTime(hotel.getOpeningTime());
        response.setClosingTime(hotel.getClosingTime());
        response.setIsActive(hotel.getIsActive());
        response.setIsVerified(hotel.getIsVerified());
        response.setCreatedAt(hotel.getCreatedAt());
        response.setUpdatedAt(hotel.getUpdatedAt());

        return response;
    }

    public void updateEntity(CreateHotelRequest request, Hotel hotel) {

        hotel.setHotelName(request.getHotelName());
        hotel.setDescription(request.getDescription());
        hotel.setOwnerId(request.getOwnerId());
        hotel.setEmail(request.getEmail());
        hotel.setPhoneNumber(request.getPhoneNumber());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setState(request.getState());
        hotel.setCountry(request.getCountry());
        hotel.setPincode(request.getPincode());
        hotel.setPriceForTwo(request.getPriceForTwo());
        hotel.setOpeningTime(request.getOpeningTime());
        hotel.setClosingTime(request.getClosingTime());
    }
}