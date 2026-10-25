package com.ordernow.hotelservice.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ordernow.hotelservice.entity.Hotel;
import com.ordernow.hotelservice.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HotelDataSeeder implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {

        InputStream inputStream =
                getClass()
                        .getResourceAsStream("/data/hotels.json");

        if (inputStream == null) {
            throw new IllegalStateException(
                    "hotels.json not found in resources/data"
            );
        }

        List<Hotel> hotels =
                objectMapper.readValue(
                        inputStream,
                        new TypeReference<List<Hotel>>() {
                        }
                );

        int insertedCount = 0;
        int skippedCount = 0;

        for (Hotel hotel : hotels) {

            boolean exists =
                    hotelRepository
                            .existsByHotelNameIgnoreCaseAndAddressIgnoreCase(
                                    hotel.getHotelName(),
                                    hotel.getAddress()
                            );

            if (exists) {
                skippedCount++;
                continue;
            }

            hotelRepository.save(hotel);
            insertedCount++;
        }

        System.out.println(
                "Hotel data seeding completed."
        );

        System.out.println(
                "Hotels inserted: " + insertedCount
        );

        System.out.println(
                "Hotels skipped: " + skippedCount
        );
    }
}