package com.ordernow.hotelservice.mapper;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import com.ordernow.hotelservice.entity.Hotel;

import java.util.ArrayList;

public class HotelMapper {

    private HotelMapper() {
    }

    public static Hotel toEntity(CreateHotelRequest request) {

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
        hotel.setRating(request.getRating());
        hotel.setOpeningTime(request.getOpeningTime());
        hotel.setClosingTime(request.getClosingTime());

        // Frontend / Restaurant Data
        hotel.setCuisines(
                request.getCuisines() != null
                        ? request.getCuisines()
                        : new ArrayList<>()
        );

        hotel.setDistance(request.getDistance());
        hotel.setOffer(request.getOffer());
        hotel.setImage(request.getImage());

        // Defaults
        hotel.setIsActive(true);
        hotel.setIsVerified(false);

        return hotel;
    }

    public static void updateEntity(
            CreateHotelRequest request,
            Hotel hotel) {

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
        hotel.setRating(request.getRating());
        hotel.setOpeningTime(request.getOpeningTime());
        hotel.setClosingTime(request.getClosingTime());

        // Frontend / Restaurant Data
        hotel.setCuisines(
                request.getCuisines() != null
                        ? request.getCuisines()
                        : new ArrayList<>()
        );

        hotel.setDistance(request.getDistance());
        hotel.setOffer(request.getOffer());
        hotel.setImage(request.getImage());
    }

    public static HotelResponse toResponse(Hotel hotel) {

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

        if (hotel.getCreatedAt() != null) {
            response.setCreatedAt(hotel.getCreatedAt());
        }

        if (hotel.getUpdatedAt() != null) {
            response.setUpdatedAt(hotel.getUpdatedAt());
        }

        // Frontend / Restaurant Data
        response.setCuisines(
                hotel.getCuisines() != null
                        ? hotel.getCuisines()
                        : new ArrayList<>()
        );

        response.setDistance(hotel.getDistance());
        response.setOffer(hotel.getOffer());
        response.setImage(hotel.getImage());

        return response;
    }
}