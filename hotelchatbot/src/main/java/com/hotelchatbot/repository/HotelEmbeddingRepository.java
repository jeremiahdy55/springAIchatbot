package com.hotelchatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.HotelEmbedding;

@Repository
public interface HotelEmbeddingRepository extends JpaRepository<HotelEmbedding, Integer> {

    @Query(value = """
    SELECT hotel_id FROM hotel_embeddings
    ORDER BY embedding <#> cast(:embedding as vector)
    LIMIT 3
    """, nativeQuery = true)
    List<Integer> findTop3BySimilarity(@Param("embedding") String embedding);

}
