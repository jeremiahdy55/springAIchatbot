package com.hotelchatbot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelchatbot.domain.Booking;
import com.hotelchatbot.domain.HotelRoom;
import com.hotelchatbot.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    HotelRoomService hotelRoomService;

    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }
    public Booking save(Booking booking) {
        // reduce the noRooms of this HotelRoom by one, since somebody booked it
        HotelRoom room = booking.getHotelRoom();
        room.setNoRooms(room.getNoRooms() - 1);
        try {
            hotelRoomService.save(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Booking savedBooking = bookingRepository.save(booking);
        return savedBooking;
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
