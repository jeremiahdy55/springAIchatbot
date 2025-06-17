package com.hotelchatbot.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.service.HotelService;


@RestController
@RequestMapping("/api")
public class HotelDataController {

    @Autowired
    HotelService hotelService;

    @GetMapping("/hotel/{keyword}")
    public ResponseEntity<List<String>> postMethodName(@PathVariable String keyword) {
        List<String> hotelJsonStrings = hotelService.searchHotelsByKeyword(keyword).stream().map(Hotel::toJsonObjectString).collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }

    
}
