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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.jwtsecurity.domain.HotelFilterDto;

@RestController
@RequestMapping("/api")
public class HotelChatbotController {

    private static final String FILTER_HOTELS_URL = "http://localhost:8686/api/hotel/filterHotels";
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
                new ParameterizedTypeReference<List<String>>() {
                });

        return responseEntity.getBody();
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/chat/{conversationId}")
    public String getChatResponse(
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
