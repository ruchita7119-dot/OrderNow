package com.ordernow.hotelservice.controller;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import org.springframework.data.domain.Page;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import com.ordernow.hotelservice.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> createHotel(
            @Valid @RequestBody CreateHotelRequest request) {

        return new ResponseEntity<>(
                hotelService.createHotel(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {

        return ResponseEntity.ok(
                hotelService.getHotelById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {

        return ResponseEntity.ok(
                hotelService.getAllHotels()
        );
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<HotelResponse>> getHotelsByCity(
            @PathVariable String city) {

        return ResponseEntity.ok(
                hotelService.getHotelsByCity(city)
        );
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<HotelResponse>> getHotelsByName(
            @RequestParam String hotelName) {

        return ResponseEntity.ok(
                hotelService.getHotelsByName(hotelName)
        );
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<HotelResponse>> getActiveHotels() {

        return ResponseEntity.ok(
                hotelService.getActiveHotels()
        );
    }
    @GetMapping("/page")
    public ResponseEntity<Page<HotelResponse>> getHotels(

            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "Page number cannot be negative")
            int page,

            @RequestParam(defaultValue = "10")
            @Min(value = 1, message = "Size must be at least 1")
            @Max(value = 100, message = "Size cannot exceed 100")
            int size,

            @RequestParam(defaultValue = "name")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        return ResponseEntity.ok(
                hotelService.getHotels(
                        page,
                        size,
                        sortBy,
                        direction
                )
        );
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<HotelResponse> updateHotelStatus(
            @PathVariable Long id,
            @RequestParam boolean isActive) {

        return ResponseEntity.ok(
                hotelService.updateHotelStatus(id, isActive)
        );
    }
    @PatchMapping("/{id}/verification")
    public ResponseEntity<HotelResponse> updateHotelVerification(
            @PathVariable Long id,
            @RequestParam boolean isVerified) {

        return ResponseEntity.ok(
                hotelService.updateHotelVerification(id, isVerified)
        );
    }
    
    @GetMapping("/rating")
    public ResponseEntity<List<HotelResponse>> getHotelsByMinimumRating(
            @RequestParam Double minimumRating) {

        return ResponseEntity.ok(
                hotelService.getHotelsByMinimumRating(minimumRating)
        );
    }
    
    @GetMapping("/verified")
    public ResponseEntity<List<HotelResponse>> getVerifiedHotels() {

        return ResponseEntity.ok(
                hotelService.getVerifiedHotels()
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(
            @PathVariable Long id,
            @Valid @RequestBody CreateHotelRequest request) {

        return ResponseEntity.ok(
                hotelService.updateHotel(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable Long id) {

        hotelService.deleteHotel(id);

        return ResponseEntity.noContent().build();
    }
}