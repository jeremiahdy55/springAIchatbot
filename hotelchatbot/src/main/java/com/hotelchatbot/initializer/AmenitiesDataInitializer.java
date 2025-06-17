package com.hotelchatbot.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.service.AmenitiesService;

// If the database is not set up yet, populate the Amenities data
@Component
public class AmenitiesDataInitializer {

    @Autowired
    AmenitiesService amenitiesService;

    public void init() {
        if (amenitiesService.count() == 0) {
            // Hotel-specific amenities (Hotel also includes HotelRoom-specific)
            Amenities a1 = new Amenities("Fitness Center");
            Amenities a2 = new Amenities("Pool");
            Amenities a3 = new Amenities("Breakfast");
            Amenities a4 = new Amenities("Parking");
            Amenities a5 = new Amenities("Airport Shuttle");
            Amenities a6 = new Amenities("Beach Nearby");
            Amenities a7 = new Amenities("Amusement Park Shuttle");
            Amenities a8 = new Amenities("Tour Bus Shuttle");

            // HotelRoom-specific amenities
            Amenities a9 = new Amenities("Ice Machine");
            Amenities a10 = new Amenities("Wifi");
            Amenities a11 = new Amenities("Air Conditioning");
            Amenities a12 = new Amenities("Kitchenette");
            Amenities a13 = new Amenities("Microwave");
            Amenities a14 = new Amenities("Mini Fridge");
            Amenities a15 = new Amenities("Coffee Maker");
            
            amenitiesService.save(a1);
            amenitiesService.save(a2);
            amenitiesService.save(a3);
            amenitiesService.save(a4);
            amenitiesService.save(a5);
            amenitiesService.save(a6);
            amenitiesService.save(a7);
            amenitiesService.save(a8);
            amenitiesService.save(a9);
            amenitiesService.save(a10);
            amenitiesService.save(a11);
            amenitiesService.save(a12);
            amenitiesService.save(a13);
            amenitiesService.save(a14);
            amenitiesService.save(a15);

        }
    }

}