package com.hotelchatbot.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Initialize the database if it's currently empty
// The MasterInitializer ensures that entities dependent on others (e.g. Amenities -> Hotel)
// will initialize after their dependencies
@Component
public class MasterInitializer implements CommandLineRunner {

    private final AmenitiesDataInitializer amenitiesDataInitializer;
    private final RoomTypeDataInitializer roomTypeDataInitializer;
    private final HotelDataInitializer hotelDataInitializer;

    public MasterInitializer(
        AmenitiesDataInitializer amenitiesDataInitializer, 
        RoomTypeDataInitializer roomTypeDataInitializer,
        HotelDataInitializer hotelDataInitializer) {
        this.amenitiesDataInitializer = amenitiesDataInitializer;
        this.roomTypeDataInitializer = roomTypeDataInitializer;
        this.hotelDataInitializer = hotelDataInitializer;
    }

    @Override
    public void run(String... args) {
        // Initialize the data in sequence
        amenitiesDataInitializer.init();
        roomTypeDataInitializer.init();
        hotelDataInitializer.init();
        System.out.println("\n\nDone with initialization!\n\n\n");
    }
}