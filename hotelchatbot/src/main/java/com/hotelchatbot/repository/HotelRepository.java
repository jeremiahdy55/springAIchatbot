package com.hotelchatbot.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.Hotel;
import java.util.Set;
import com.hotelchatbot.domain.Amenities;


@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("""
    SELECT DISTINCT h FROM Hotel h
    LEFT JOIN FETCH h.amenities
    LEFT JOIN FETCH h.hotelRooms
    WHERE LOWER(h.hotelName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(h.city) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(h.state) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(h.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Hotel> searchHotelsByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT h FROM Hotel h JOIN h.amenities a WHERE a IN :amenities")
    List<Hotel> findByAnyAmenities(@Param("amenities") Collection<Amenities> amenities);

    List<Hotel> findByStarRatingIn(Collection<Integer> starRatings);
    List<Hotel> findByAveragePriceLessThan(Double price);
    

}
