package com.hotelchatbot.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hotelchatbot.domain.RoomType;
import com.hotelchatbot.service.RoomTypeService;

// If the database is not set up yet, populate the RoomType data
@Component
public class RoomTypeDataInitializer {

    @Autowired
    RoomTypeService roomTypeService;

    public void init() {
        if (roomTypeService.count() == 0) {
            RoomType r1 = new RoomType("Single");
            RoomType r2 = new RoomType("Double");
            RoomType r3 = new RoomType("Triple");
            RoomType r4 = new RoomType("Deluxe");
            roomTypeService.save(r1);
            roomTypeService.save(r2);
            roomTypeService.save(r3);
            roomTypeService.save(r4);
        }
    }

}