package com.hotelchatbot.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.domain.HotelRoomEmbedding;
import com.hotelchatbot.embeddings.EmbeddingService;
import com.hotelchatbot.repository.HotelRoomEmbeddingRepository;

@Service
public class HotelRoomEmbeddingService {

    @Autowired
    HotelRoomEmbeddingRepository hotelRoomEmbeddingRepository;

    @Autowired
    HotelRoomService hotelRoomService;

    @Autowired
    EmbeddingService embeddingService;

    public List<HotelRoomEmbedding> findAll() {
        return hotelRoomEmbeddingRepository.findAll();
    }
    public HotelRoomEmbedding save(HotelRoom room) throws IOException {
        HotelRoomEmbedding data = new HotelRoomEmbedding(getHotelRoomEmbedding(room), room);
        return hotelRoomEmbeddingRepository.save(data);
    }
    public HotelRoomEmbedding findById(int id) {
		Optional<HotelRoomEmbedding> data = hotelRoomEmbeddingRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		} else return null;
	}
    public boolean existsById(int id) {
		return hotelRoomEmbeddingRepository.existsById(id);
	}
    public void deleteById(int id) {
		hotelRoomEmbeddingRepository.deleteById(id);		
	}
    public long count() {
        return hotelRoomEmbeddingRepository.count();
    }

    // Use EmbeddingService to call OpenAI embedding model and get the float[] for vector
    public float[] getHotelRoomEmbedding(HotelRoom room) throws IOException {
        String embeddingString = room.toEmbeddingString();
        return embeddingService.getEmbedding(embeddingString);
    }

    public List<HotelRoom> findTop5BySimilarity(float[] embedding) {
        // Convert the embedding into a String representation
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < embedding.length; i++) {
            sb.append(embedding[i]);
            if (i != embedding.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        String vectorString = sb.toString();
        List<Integer> top5Ids = hotelRoomEmbeddingRepository.findTop5BySimilarity(vectorString);
        List<HotelRoom> returnHotelRooms = new ArrayList<>();
        for (Integer id : top5Ids) {
            HotelRoom room = hotelRoomService.findById(id);
            if (room != null) {
                returnHotelRooms.add(room);
            }
        }
        return returnHotelRooms;
    }
}
