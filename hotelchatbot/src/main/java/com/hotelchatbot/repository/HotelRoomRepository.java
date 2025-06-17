package com.hotelchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.HotelRoom;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer> {
    
    // @Query(value = """
    // SELECT * FROM hotel_rooms
    // ORDER BY embedding <#> cast(:embedding as vector)
    // LIMIT 5
    // """, nativeQuery = true)
    // List<HotelRoom> findTop5BySimilarity(@Param("embedding") float[] embedding);

    // Optional<HotelRoom> findByHotelRoomId(int hotelRoomId);

    // @Query(value = "SELECT id, name, other_columns FROM hotel_room WHERE hotelroomid = :id", nativeQuery = true)
    // Optional<HotelRoom> findByIdWithoutEmbedding(@Param("id") int id);

}
