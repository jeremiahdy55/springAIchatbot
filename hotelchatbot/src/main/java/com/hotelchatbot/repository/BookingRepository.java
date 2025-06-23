package com.hotelchatbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hotelchatbot.domain.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
