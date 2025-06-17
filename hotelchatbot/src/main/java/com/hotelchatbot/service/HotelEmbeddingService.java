package com.hotelchatbot.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.Hotel;
import com.hotelchatbot.domain.HotelEmbedding;
import com.hotelchatbot.embeddings.EmbeddingService;
import com.hotelchatbot.repository.HotelEmbeddingRepository;
import com.hotelchatbot.repository.HotelRepository;

@Service
public class HotelEmbeddingService {

    @Autowired
    HotelEmbeddingRepository hotelEmbeddingRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    EmbeddingService embeddingService;

    public List<HotelEmbedding> findAll() {
        return hotelEmbeddingRepository.findAll();
    }
    public HotelEmbedding save(Hotel hotel) throws IOException {
        HotelEmbedding data = new HotelEmbedding(getHotelEmbedding(hotel), hotel);
        return hotelEmbeddingRepository.save(data);
    }
    public HotelEmbedding findById(int id) {
		Optional<HotelEmbedding> data = hotelEmbeddingRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		} else return null;
	}
    public boolean existsById(int id) {
		return hotelEmbeddingRepository.existsById(id);
	}
    public void deleteById(int id) {
		hotelEmbeddingRepository.deleteById(id);		
	}
    public long count() {
        return hotelEmbeddingRepository.count();
    }

    // Use EmbeddingService to call OpenAI embedding model and get the float[] for vector
    public float[] getHotelEmbedding(Hotel room) throws IOException {
        String embeddingString = room.toEmbeddingString();
        return embeddingService.getEmbedding(embeddingString);
    }

    public List<Hotel> findTop3BySimilarity(float[] embedding) {
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
        List<Integer> top3Ids = hotelEmbeddingRepository.findTop3BySimilarity(vectorString);
        List<Hotel> returnHotels = new ArrayList<>();
        for (Integer id : top3Ids) {
            Hotel hotel = hotelRepository.findById(id).orElse(null);
            if (hotel != null) {
                returnHotels.add(hotel);
            }
        }
        return returnHotels;
    }
}
