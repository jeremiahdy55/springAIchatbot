package com.hotelchatbot.repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.Amenities;
import com.hotelchatbot.domain.Hotel;

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

    @Query("""
    SELECT h
    FROM Hotel h
    JOIN h.hotelRooms hr
    GROUP BY h
    HAVING SUM(hr.guestsPerRoom * hr.noRooms) >= :guestCount
    """)
    List<Hotel> findHotelsByMinimumGuestAvailability(@Param("guestCount") int guestCount);

    @Query("""
        SELECT DISTINCT h
        FROM Hotel h
        JOIN h.hotelRooms hr
        WHERE hr.availabilityStartDate <= :checkIn
          AND hr.availabilityEndDate >= :checkOut
        """)
    List<Hotel> findHotelsWithRoomsAvailableBetween(
        @Param("checkIn") LocalDate checkIn,
        @Param("checkOut") LocalDate checkOut
    );

    @Query("SELECT h FROM Hotel h JOIN h.amenities a WHERE a IN :amenities")
    List<Hotel> findByAnyAmenities(@Param("amenities") Collection<Amenities> amenities);

    List<Hotel> findByStarRatingIn(Collection<Integer> starRatings);
    List<Hotel> findByAveragePriceLessThan(Double price);
    

}
