package com.hotelchatbot.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
}
