package com.hotelchatbot.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.service.AmenitiesService;
import com.hotelchatbot.service.HotelService;



@RestController
@RequestMapping("/api")
public class HotelDataController {

    @Autowired
    HotelService hotelService;

    @Autowired
    AmenitiesService amenitiesService;

    @GetMapping("/hotel/searchByKeyword/{keyword}")
    public ResponseEntity<List<String>> searchHotelsByKeyword(@PathVariable String keyword) {
        List<String> hotelJsonStrings = hotelService.searchHotelsByKeyword(keyword)
            .stream()
            .map(Hotel::toJsonObjectString)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }

    @GetMapping("/hotel/searchByPrice/{budget}")
    public ResponseEntity<List<String>> searchHotelsByBudget(@PathVariable Double budget) {
        List<String> hotelJsonStrings = hotelService.searchHotelsByAveragePriceLessThanBudget(budget)
            .stream()
            .map(Hotel::toJsonObjectString)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }

    @GetMapping("/hotel/searchByMinGuestAvailability/{minGuestAvailability}")
    public ResponseEntity<List<String>> searchHotelsByGuestAvailability(@PathVariable Integer minGuestAvailability) {
        List<String> hotelJsonStrings = hotelService.searchHotelsByMinimumGuestAvailability(minGuestAvailability)
            .stream()
            .map(Hotel::toJsonObjectString)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }

    @GetMapping("/hotel/searchByStarRatings")
    // /hotel/searchByStarRatings?starRatings=IntegerVal&starRatings=IntegerVal...
    public ResponseEntity<List<String>> searchHotelsByStarRating(@RequestParam("starRatings") List<Integer> starRatings) {
        List<String> hotelJsonStrings = hotelService.searchHotelsByStarRating(starRatings)
            .stream()
            .map(Hotel::toJsonObjectString)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }

    @GetMapping("/hotel/searchByCheckedDates")
    public ResponseEntity<List<String>> searchHotelsByCheckingDates(
        @RequestParam("checkIn")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
        @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut
        ) {
        List<String> hotelJsonStrings = hotelService.searchHotelsWithRoomsAvailableBetween(checkIn, checkOut)
            .stream()
            .map(Hotel::toJsonObjectString)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }   

    @GetMapping("/hotel/searchByAmenities")
    // /hotel/searchByAmenities?amenityNames=StringVal&amenityNames=...
    public ResponseEntity<List<String>> searchHotelsByAmenities(@RequestParam("amenityNames") List<String> amenityNames) {
        List<Amenities> amenities = amenitiesService.findByNameIn(amenityNames);
        List<String> hotelJsonStrings = hotelService.searchHotelsByAnyAmenities(amenities)
            .stream()
            .map(Hotel::toJsonObjectString)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }
}
