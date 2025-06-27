package com.jwtsecurity.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.jwtsecurity.domain.BookingDto;
import com.jwtsecurity.domain.HotelFilterDto;

@RestController
@RequestMapping("/api")
public class HotelChatbotController {

    // Define the HTTP request URLs to get/send data
    private static final String FILTER_HOTELS_URL = "http://localhost:8686/api/hotel/filterHotels";
    private static final String BOOKING_URL = "http://localhost:8686/api/hotel/booking";
    private static final String HOTELID_FETCH_BASE_URL = "http://localhost:8686/api/hotel/";
    private static final String CHATBOT_BASE_URL = "http://localhost:8686/chatbot/chat/";


    // Get the hotels as a JS array/ Java List<Strings>
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/hotel/filterHotels")
    public List<String> getHotelsFromHotelChatbotMS(@RequestBody HotelFilterDto filter) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Set the header to specify that only JSON data is accepted as response
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<HotelFilterDto> requestEntity = new HttpEntity<>(filter, headers);
        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
                FILTER_HOTELS_URL,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<List<String>>() {}
                );

        return responseEntity.getBody();
    }

    // Get a single Hotel object as JSON string (not parsed)
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hotel/{hotelId}")
    public String getHotelByIdFromHotelChatbotMS(@PathVariable Integer hotelId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Set the header to specify that only JSON data is accepted as response
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
            HOTELID_FETCH_BASE_URL + hotelId,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        return responseEntity.getBody();
    }

    // Pass booking information as a DTO to hotelchatbot microservice
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/hotel/createBooking")
    public String postBookingToHotelChatbotMS(@RequestBody BookingDto bookingDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        // Set the header to specify that only JSON data is accepted as response
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<BookingDto> requestEntity = new HttpEntity<>(bookingDto, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                BOOKING_URL,
                HttpMethod.POST,
                requestEntity,
                String.class);

        return responseEntity.getBody();
    }

    // send a requst to hotelchatbot microservice for a chatbot assistant response
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/chat/{conversationId}")
    public String getChatResponseFromHotelChatbotMS(
            @PathVariable String conversationId,
            @RequestBody String userQuery) {
            
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        String chatbotURL = CHATBOT_BASE_URL + conversationId;

        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        HttpEntity<String> requestEntity = new HttpEntity<>(userQuery, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                chatbotURL,
                HttpMethod.POST,
                requestEntity,
                String.class);

        return responseEntity.getBody();
    }

}
