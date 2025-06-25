package com.hotelchatbot.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.domain.HotelFilterDto;
import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.embeddings.EmbeddingService;
import com.hotelchatbot.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    AmenitiesService amenitiesService;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelEmbeddingService hotelEmbeddingService;

    @Autowired
    HotelRoomEmbeddingService hotelRoomEmbeddingService;

    @Autowired
    EmbeddingService embeddingService;

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel save(Hotel hotel) throws IOException {
        Hotel returnVal = hotelRepository.save(hotel);
        hotelEmbeddingService.save(hotel);
        // For each room, save the HotelRoomEmbedding
        if (hotel.getHotelRooms() != null) {
            for (HotelRoom room : hotel.getHotelRooms()) {
                hotelRoomEmbeddingService.save(room);
            }
        }
        return returnVal;
    }

    public Hotel findById(int id) {
        Optional<Hotel> data = hotelRepository.findById(id);
        if (data.isPresent()) {
            return data.get();
        } else
            return null;
    }

    public boolean existsById(int id) {
        return hotelRepository.existsById(id);
    }

    public void deleteById(int id) {
        hotelRepository.deleteById(id);
    }

    public long count() {
        return hotelRepository.count();
    }

    // Use EmbeddingService to call OpenAI embedding model and get the float[] for
    // vector
    public float[] getHotelEmbedding(Hotel hotel) throws IOException {
        String embeddingString = hotel.toEmbeddingString();
        return embeddingService.getEmbedding(embeddingString);
    }

    public List<Hotel> searchHotelsByKeyword(String keyword) {
        return hotelRepository.searchHotelsByKeyword(keyword);
    }

    public List<Hotel> searchHotelsByMinimumGuestAvailability(int guestCount) {
        return hotelRepository.findHotelsByMinimumGuestAvailability(guestCount);
    }

    public List<Hotel> searchHotelsWithRoomsAvailableBetween(LocalDate checkIn, LocalDate checkOut) {
        return hotelRepository.findHotelsWithRoomsAvailableBetween(checkIn, checkOut);
    }

    public List<Hotel> searchHotelsByAnyAmenities(Collection<Amenities> amenities) {
        return hotelRepository.findByAnyAmenities(amenities);
    }

    public List<Hotel> searchHotelsByStarRating(Collection<Integer> starRatings) {
        return hotelRepository.findByStarRatingIn(starRatings);
    }

    public List<Hotel> searchHotelsByAveragePriceLessThanBudget(Double budget) {
        return hotelRepository.findByAveragePriceLessThan(budget);
    }

    public List<Hotel> filterHotelsByDto(HotelFilterDto filter) {
        // Intialize the list of lists that will hold the filter results
        List<List<Hotel>> lists = new ArrayList<>();

        // Only add lists if the filter criteria is present

        if (filter.getKeyword() != null && !filter.getKeyword().isEmpty()) {
            lists.add(searchHotelsByKeyword(filter.getKeyword()));
        }

        if (filter.getBudget() != null) {
            lists.add(searchHotelsByAveragePriceLessThanBudget(filter.getBudget()));
        }

        if (filter.getMinGuestAvailability() != null) {
            lists.add(searchHotelsByMinimumGuestAvailability(filter.getMinGuestAvailability()));
        }

        if (filter.getCheckIn() != null && filter.getCheckOut() != null
                && !filter.getCheckIn().isEmpty() && !filter.getCheckOut().isEmpty()) {
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
            LocalDate checkIn = LocalDate.parse(filter.getCheckIn(), dtFormatter);
            LocalDate checkOut = LocalDate.parse(filter.getCheckOut(), dtFormatter);
            lists.add(searchHotelsWithRoomsAvailableBetween(checkIn, checkOut));
        }

        if (filter.getStarRatings() != null && !filter.getStarRatings().isEmpty()) {
            lists.add(searchHotelsByStarRating(filter.getStarRatings()));
        }

        if (filter.getAmenityNames() != null && !filter.getAmenityNames().isEmpty()) {
            List<Amenities> amenities = amenitiesService.findByNameIn(filter.getAmenityNames());
            lists.add(searchHotelsByAnyAmenities(amenities));
        }

        // If no filters provided, return all hotels
        if (lists.isEmpty()) {
            return findAll();
        }

        // Initialize intersection with the first non-null list
        List<Hotel> intersection = new ArrayList<>(lists.get(0));

        // Intersect with all subsequent lists
        for (int i = 1; i < lists.size(); i++) {
            intersection.retainAll(lists.get(i));
        }

        return intersection;
    }
}
