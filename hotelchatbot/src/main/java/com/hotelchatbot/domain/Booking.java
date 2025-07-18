package com.hotelchatbot.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bookingId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guestFirstName;
    private String guestLastName;
    private String guestEmail;
    private String billingAddress;
    private String cardNo;

    @ManyToOne
    @JoinColumn(name = "hotel_room_id")
    private HotelRoom hotelRoom;

    public Booking() {}

    public Booking(LocalDate checkInDate, LocalDate checkOutDate, String guestFirstName, String guestLastName,
            String guestEmail, String billingAddress, String cardNo, HotelRoom hotelRoom) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestEmail = guestEmail;
        this.guestFirstName = guestFirstName;
        this.guestLastName = guestLastName;
        this.hotelRoom = hotelRoom;
        this.billingAddress = billingAddress;
        this.cardNo = cardNo;
    }

    public HotelRoom getHotelRoom() {
        return hotelRoom;
    }
    public void setHotelRoom(HotelRoom hotelRoom) {
        this.hotelRoom = hotelRoom;
    }
    public int getBookingId() {
        return bookingId;
    }
    public LocalDate getCheckInDate() {
        return checkInDate;
    }
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
    public String getGuestFirstName() {
        return guestFirstName;
    }
    public void setGuestFirstName(String guestFirstName) {
        this.guestFirstName = guestFirstName;
    }
    public String getGuestLastName() {
        return guestLastName;
    }
    public void setGuestLastName(String guestLastName) {
        this.guestLastName = guestLastName;
    }
    public String getGuestEmail() {
        return guestEmail;
    }
    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }    

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
