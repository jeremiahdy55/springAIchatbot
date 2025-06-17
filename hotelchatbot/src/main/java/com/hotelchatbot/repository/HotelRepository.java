package com.hotelchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    // @Query(value = """
    // SELECT * FROM hotels
    // ORDER BY embedding <#> cast(:embedding as vector)
    // LIMIT 5
    // """, nativeQuery = true)
    // List<Hotel> findTop5BySimilarity(@Param("embedding") float[] embedding);

    // @Query(value = "SELECT hotelid, name, address, city, description, discount, email, hotelname, mobile, starrating, state, timesbooked FROM hotel_room WHERE hotelroomid = :id", nativeQuery = true)
    // Optional<Hotel> findByIdWithoutEmbedding(@Param("id") int id);
    
}
