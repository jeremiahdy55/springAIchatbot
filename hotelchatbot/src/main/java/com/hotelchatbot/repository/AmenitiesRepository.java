package com.hotelchatbot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.Amenities;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer> {
    Amenities findByName(String name);
    List<Amenities> findByNameIn(List<String> names);
}
