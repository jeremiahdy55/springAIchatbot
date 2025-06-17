package com.hotelchatbot.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.hotelchatbot.openai.ChatService;
import com.hotelchatbot.service.HotelRoomService;
import com.hotelchatbot.service.HotelService;

// Initialize the database if it's currently empty
// The MasterInitializer ensures that entities dependent on others (e.g. Amenities -> Hotel)
// will initialize after their dependencies
@Component
public class MasterInitializer implements CommandLineRunner {

    @Autowired
    ChatService chatService;

    @Autowired
    HotelService hotelService;

    @Autowired
    HotelRoomService hotelRoomService;

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
    }
}