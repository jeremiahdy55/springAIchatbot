package com.jwtsecurity.domain;

public class BookingDto {
    private Integer hotelRoomId;
    private String firstName;
    private String lastName;
    private String email;
    private String billingAddress;
    private String cardNo;
    private String checkInDate;
    private String checkOutDate;

    public BookingDto() {
    }

    public BookingDto(Integer hotelRoomId,
            String firstName,
            String lastName,
            String email,
            String billingAddress,
            String cardNo,
            String checkInDate,
            String checkOutDate) {
        this.billingAddress = billingAddress;
        this.cardNo = cardNo;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.email = email;
        this.firstName = firstName;
        this.hotelRoomId = hotelRoomId;
        this.lastName = lastName;
    }

    public Integer getHotelRoomId() {
        return hotelRoomId;
    }

    public void setHotelName(Integer hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

}
