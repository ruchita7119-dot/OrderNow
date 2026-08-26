package com.ordernow.hotelservice.service.impl;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import com.ordernow.hotelservice.entity.Hotel;
import com.ordernow.hotelservice.exception.ResourceNotFoundException;
import com.ordernow.hotelservice.mapper.HotelMapper;
import com.ordernow.hotelservice.repository.HotelRepository;
import com.ordernow.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final Duration CACHE_DURATION = Duration.ofMinutes(10);

    @Override
    public HotelResponse createHotel(CreateHotelRequest request) {

        Hotel hotel = hotelMapper.toEntity(request);

        hotel.setRating(null);
        hotel.setIsActive(true);
        hotel.setIsVerified(false);

        Hotel savedHotel = hotelRepository.save(hotel);

        return hotelMapper.toResponse(savedHotel);
    }

    @Override
    public HotelResponse getHotelById(Long id) {

        String key = "hotel:" + id;

        Object cachedHotel = redisTemplate.opsForValue().get(key);

        if (cachedHotel != null) {
            return (HotelResponse) cachedHotel;
        }

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id : " + id));

        HotelResponse response = hotelMapper.toResponse(hotel);

        redisTemplate.opsForValue()
                .set(key, response, CACHE_DURATION);

        return response;
    }

    @Override
    public List<HotelResponse> getAllHotels() {

        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse updateHotel(
            Long id,
            CreateHotelRequest request) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id : " + id));

        hotelMapper.updateEntity(request, hotel);

        Hotel updatedHotel = hotelRepository.save(hotel);

        HotelResponse response =
                hotelMapper.toResponse(updatedHotel);

        String key = "hotel:" + id;

        redisTemplate.opsForValue()
                .set(key, response, CACHE_DURATION);

        return response;
    }

    @Override
    public void deleteHotel(Long id) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id : " + id));

        hotelRepository.delete(hotel);

        String key = "hotel:" + id;

        redisTemplate.delete(key);
    }
}