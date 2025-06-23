package com.hotelchatbot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.Booking;
import com.hotelchatbot.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }
    public Booking findById(int id) {
		Optional<Booking> data = bookingRepository.findById(id);
		if(data.isPresent()) {
			return data.get();
		} else return null;
	}
    public boolean existsById(int id) {
		return bookingRepository.existsById(id);
	}
    public void deleteById(int id) {
		bookingRepository.deleteById(id);		
	}
    public long count() {
        return bookingRepository.count();
    }

}
