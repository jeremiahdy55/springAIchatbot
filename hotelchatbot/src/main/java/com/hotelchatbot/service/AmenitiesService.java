package com.hotelchatbot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.repository.AmenitiesRepository;

import com.hotelchatbot.domain.Amenities;

@Service
public class AmenitiesService {

    @Autowired
    AmenitiesRepository amenitiesRepository;

    public List<Amenities> findAll() {
        return amenitiesRepository.findAll();
    }
    public Amenities save(Amenities amenity) {
        return amenitiesRepository.save(amenity);
    }
    public Amenities findById(int id) {
		Optional<Amenities> data = amenitiesRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		} else
		return null;
	}
    public boolean existsById(int id) {
		return amenitiesRepository.existsById(id);
	}
    public Amenities findByName(String name) {
		return amenitiesRepository.findByName(name);
	}
    public void deleteById(int id) {
		amenitiesRepository.deleteById(id);		
	}
    public long count() {
        return amenitiesRepository.count();
    }
}
