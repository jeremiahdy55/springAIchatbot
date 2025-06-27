package com.hotelchatbot.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.domain.Booking;
import com.hotelchatbot.domain.BookingDto;
import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.domain.HotelFilterDto;
import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.email.EmailService;
import com.hotelchatbot.service.AmenitiesService;
import com.hotelchatbot.service.BookingService;
import com.hotelchatbot.service.HotelRoomService;
import com.hotelchatbot.service.HotelService;



@RestController
@RequestMapping("/api")
public class HotelDataController {

    @Autowired
    HotelService hotelService;

    @Autowired
    AmenitiesService amenitiesService;

    @Autowired
    HotelRoomService hotelRoomService;

    @Autowired
    BookingService bookingService;

    @Autowired
    EmailService emailService;

    // Filter the hotel DB by the filter DTO passed from reactUI
    @PostMapping("/hotel/filterHotels")
    public ResponseEntity<List<String>> filterHotels(@RequestBody HotelFilterDto filter) {
        List<String> hotelJsonStrings = hotelService.filterHotelsByDto(filter)
            .stream()
            .map(Hotel::toJsonObjectStringFullDetail)
            .collect(Collectors.toList());
        return ResponseEntity.ok().body(hotelJsonStrings);
    }

    // Get hotel object data by ID
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<String> getHotelById(@PathVariable Integer hotelId) {
        String hotelJsonString = hotelService.findById(hotelId).toJsonObjectStringFullDetail();
        return ResponseEntity.ok().body(hotelJsonString);
    }

    // Create a booking record in DB
    @PostMapping("/hotel/booking")
    public ResponseEntity<String> createBooking(@RequestBody BookingDto bookingDto) {
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate checkIn = LocalDate.parse(bookingDto.getCheckInDate(), dtFormatter);
        LocalDate checkOut = LocalDate.parse(bookingDto.getCheckOutDate(), dtFormatter);
        HotelRoom room = hotelRoomService.findById(bookingDto.getHotelRoomId());
        if (room == null) return ResponseEntity.badRequest().body("Could not find room with hotelRoomId: " + bookingDto.getHotelRoomId());
        Booking newBooking = new Booking(checkIn, checkOut, bookingDto.getFirstName(), bookingDto.getLastName(),
                bookingDto.getEmail(), bookingDto.getBillingAddress(), bookingDto.getCardNo(), room);
        Booking savedBooking = bookingService.save(newBooking);
        if (savedBooking != null) {
            String subject = "BOOKING CONFIRMED - " + LocalDate.now().format(dtFormatter);
            emailService.sendBookingEmail(
                savedBooking.getGuestEmail(), 
                subject, 
                savedBooking.getHotelRoom().getHotel().getHotelName(), 
                savedBooking.getGuestFirstName(),
                savedBooking.getGuestLastName(),
                savedBooking.getGuestEmail(), 
                savedBooking.getHotelRoom().getRoomType(), 
                savedBooking.getBillingAddress(),
                savedBooking.getCardNo(),
                savedBooking.getCheckInDate().format(dtFormatter), 
                savedBooking.getCheckOutDate().format(dtFormatter));
        }
        return ResponseEntity.ok().body("Saved your booking, enjoy your stay!");
    }

    /* Testing endpoints, not actually used in final version */
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
