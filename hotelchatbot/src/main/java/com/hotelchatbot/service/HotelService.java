package com.hotelchatbot.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.embeddings.EmbeddingService;
import com.hotelchatbot.repository.HotelRepository;

@Service
public class HotelService {

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
		if(data.isPresent()) {
			return data.get();
		} else return null;
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

    // Use EmbeddingService to call OpenAI embedding model and get the float[] for vector
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
    
}
