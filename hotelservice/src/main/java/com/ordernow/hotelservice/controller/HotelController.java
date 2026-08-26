package com.ordernow.hotelservice.controller;

import com.ordernow.hotelservice.dto.request.CreateHotelRequest;
import com.ordernow.hotelservice.dto.response.HotelResponse;
import com.ordernow.hotelservice.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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