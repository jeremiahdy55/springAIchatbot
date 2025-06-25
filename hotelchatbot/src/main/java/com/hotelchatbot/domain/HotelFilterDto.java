package com.hotelchatbot.domain;

import java.util.List;

public class HotelFilterDto {
    private String keyword;
    private Double budget;
    private Integer minGuestAvailability;
    private String checkIn;
    private String checkOut;
    private List<Integer> starRatings;
    private List<String> amenityNames;

    public HotelFilterDto(
            String keyword,
            Double budget,
            Integer minGuestAvailability,
            String checkIn,
            String checkOut,
            List<Integer> starRatings,
            List<String> amenityNames) {
        this.amenityNames = amenityNames;
        this.budget = budget;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.keyword = keyword;
        this.minGuestAvailability = minGuestAvailability;
        this.starRatings = starRatings;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Integer getMinGuestAvailability() {
        return minGuestAvailability;
    }

    public void setMinGuestAvailability(Integer minGuestAvailability) {
        this.minGuestAvailability = minGuestAvailability;
    }

    public List<Integer> getStarRatings() {
        return starRatings;
    }

    public void setStarRatings(List<Integer> starRatings) {
        this.starRatings = starRatings;
    }

    public List<String> getAmenityNames() {
        return amenityNames;
    }

    public void setAmenityNames(List<String> amenityNames) {
        this.amenityNames = amenityNames;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

}
