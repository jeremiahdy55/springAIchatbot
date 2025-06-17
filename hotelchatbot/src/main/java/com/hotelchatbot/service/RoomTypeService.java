package com.hotelchatbot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.RoomType;
import com.hotelchatbot.repository.RoomTypeRepository;

@Service
public class RoomTypeService {

    @Autowired
    RoomTypeRepository roomTypeRepository;

    public List<RoomType> findAll() {
        return roomTypeRepository.findAll();
    }
    public RoomType save(RoomType roomType) {
        return roomTypeRepository.save(roomType);
    }
    public RoomType findById(int id) {
		Optional<RoomType> data = roomTypeRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		} else return null;
	}
    public boolean existsById(int id) {
		return roomTypeRepository.existsById(id);
	}
    public RoomType findByName(String name) {
		return roomTypeRepository.findByName(name);
	}
    public void deleteById(int id) {
		roomTypeRepository.deleteById(id);		
	}
    public long count() {
        return roomTypeRepository.count();
    }

}
