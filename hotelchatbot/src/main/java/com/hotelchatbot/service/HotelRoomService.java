package com.hotelchatbot.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.embeddings.EmbeddingService;
import com.hotelchatbot.repository.HotelRoomRepository;

@Service
public class HotelRoomService {

    @Autowired
    HotelRoomRepository hotelRoomRepository;

    @Autowired
    EmbeddingService embeddingService;

    @Autowired

    public List<HotelRoom> findAll() {
        return hotelRoomRepository.findAll();
    }
    public HotelRoom save(HotelRoom room) throws IOException {
        return hotelRoomRepository.save(room);
    }
    public HotelRoom findById(int id) {
		Optional<HotelRoom> data = hotelRoomRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		} else return null;
	}
    public boolean existsById(int id) {
		return hotelRoomRepository.existsById(id);
	}
    public void deleteById(int id) {
		hotelRoomRepository.deleteById(id);		
	}
    public long count() {
        return hotelRoomRepository.count();
    }

    // Use EmbeddingService to call OpenAI embedding model and get the float[] for vector
    public float[] getHotelRoomEmbedding(HotelRoom room) throws IOException {
        String embeddingString = room.toEmbeddingString();
        return embeddingService.getEmbedding(embeddingString);
    }
}
