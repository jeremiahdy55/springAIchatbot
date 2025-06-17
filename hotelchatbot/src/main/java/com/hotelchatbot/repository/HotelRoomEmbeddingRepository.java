package com.hotelchatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.HotelRoomEmbedding;

@Repository
public interface HotelRoomEmbeddingRepository extends JpaRepository<HotelRoomEmbedding, Integer> {

     @Query(value = """
    SELECT hotel_room_id FROM hotel_room_embeddings
    ORDER BY embedding <#> cast(:embedding as vector)
    LIMIT 5
    """, nativeQuery = true)
    List<Integer> findTop5BySimilarity(@Param("embedding") String embedding);
    
}
