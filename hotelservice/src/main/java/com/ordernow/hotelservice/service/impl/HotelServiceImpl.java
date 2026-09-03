package com.ordernow.hotelservice.service.impl;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import com.ordernow.hotelservice.entity.Hotel;
import com.ordernow.hotelservice.exception.ResourceNotFoundException;
import com.ordernow.hotelservice.mapper.HotelMapper;
import com.ordernow.hotelservice.repository.HotelRepository;
import com.ordernow.hotelservice.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

            HotelResponse cachedResponse =
                    (HotelResponse) cachedHotel;

            if (Boolean.TRUE.equals(cachedResponse.getIsActive())) {
                return cachedResponse;
            }

            redisTemplate.delete(key);
        }

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id : " + id));

        if (!Boolean.TRUE.equals(hotel.getIsActive())) {
            throw new ResourceNotFoundException(
                    "Hotel not found with id : " + id);
        }

        HotelResponse response =
                hotelMapper.toResponse(hotel);

        redisTemplate.opsForValue()
                .set(key, response, CACHE_DURATION);

        return response;
    }

    @Override
    public List<HotelResponse> getAllHotels() {

        return hotelRepository.findByIsActiveTrue()
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Page<HotelResponse> getHotels(
            int page,
            int size,
            String sortBy,
            String direction) {

        List<String> allowedSortFields =
                List.of("id", "hotelName", "city", "rating");

        if (!allowedSortFields.contains(sortBy)) {
            sortBy = "hotelName";
        }

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return hotelRepository.findByIsActiveTrue(pageable)
                .map(hotelMapper::toResponse);
    }

    @Override
    public List<HotelResponse> getHotelsByCity(String city) {

        return hotelRepository
                .findByCityIgnoreCaseAndIsActiveTrue(city)
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponse> getHotelsByName(String hotelName) {

        return hotelRepository
                .findByHotelNameContainingIgnoreCaseAndIsActiveTrue(
                        hotelName)
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponse> getActiveHotels() {

        return hotelRepository.findByIsActiveTrue()
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponse> getVerifiedHotels() {

        return hotelRepository
                .findByIsVerifiedTrueAndIsActiveTrue()
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelResponse> getHotelsByMinimumRating(
            BigDecimal rating) {

        return hotelRepository
                .findByRatingGreaterThanEqualAndIsActiveTrue(rating)
                .stream()
                .map(hotelMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public HotelResponse updateHotelStatus(
            Long id,
            boolean isActive) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id : " + id));

        hotel.setIsActive(isActive);

        Hotel updatedHotel =
                hotelRepository.save(hotel);

        HotelResponse response =
                hotelMapper.toResponse(updatedHotel);

        String key = "hotel:" + id;

        redisTemplate.opsForValue()
                .set(key, response, CACHE_DURATION);

        return response;
    }

    @Override
    public HotelResponse updateHotelVerification(
            Long id,
            boolean isVerified) {

        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Hotel not found with id : " + id));

        hotel.setIsVerified(isVerified);

        Hotel updatedHotel =
                hotelRepository.save(hotel);

        HotelResponse response =
                hotelMapper.toResponse(updatedHotel);

        String key = "hotel:" + id;

        redisTemplate.opsForValue()
                .set(key, response, CACHE_DURATION);

        return response;
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

        Hotel updatedHotel =
                hotelRepository.save(hotel);

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

        hotel.setIsActive(false);

        hotelRepository.save(hotel);

        String key = "hotel:" + id;

        redisTemplate.delete(key);
    }
}