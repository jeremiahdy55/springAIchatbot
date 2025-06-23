package com.hotelchatbot.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "hotel_rooms")
public class HotelRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotelRoomId")
    private Integer hotelRoomId; // PK
    private int noRooms;
    private int guestsPerRoom;
    private float price;
    private float discount;
    private String description;
    private String policies;
    private LocalDate availabilityStartDate;
    private LocalDate availabilityEndDate;

    @ManyToOne
    private RoomType type;

    @OneToMany(mappedBy = "hotelRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Booking> bookings;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Amenities> amenities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id") // foreign key in hotel_room table
    private Hotel hotel;

    @Transient
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    public HotelRoom() {}

    public int getGuestsPerRoom() {
        return guestsPerRoom;
    }

    public void setGuestsPerRoom(int guestsPerRoom) {
        this.guestsPerRoom = guestsPerRoom;
    }

    public String getAvailabilityStartDate() {
        return availabilityStartDate.format(dtFormatter);
    }

    public void setAvailabilityStartDate(String availabilityStartDate) {
        this.availabilityStartDate = LocalDate.parse(availabilityStartDate, dtFormatter);
    }

    public String getAvailabilityEndDate() {
        return availabilityEndDate.format(dtFormatter);
    }

    public void setAvailabilityEndDate(String availabilityEndDate) {
        this.availabilityEndDate = LocalDate.parse(availabilityEndDate, dtFormatter);
    }

    public HotelRoom(int noRooms, int guestsPerRoom,float price, float discount, String description, String policies,
            RoomType type, Set<Amenities> amenities, String availabilityStartDate, String availabilityEndDate) {
        this.noRooms = noRooms;
        this.price = price;
        this.discount = discount;
        this.description = description;
        this.policies = policies;
        this.type = type;
        this.amenities = amenities;
        this.availabilityStartDate = LocalDate.parse(availabilityStartDate, dtFormatter);
        this.availabilityEndDate = LocalDate.parse(availabilityEndDate, dtFormatter);
        this.guestsPerRoom = guestsPerRoom;
    }

    public String getRoomType() {
        return type.getName();
    }

    public int getHotelRoomId() {
        return hotelRoomId;
    }

    public void setHotelRoomId(int hotelRoomId) {
        this.hotelRoomId = hotelRoomId;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public Set<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenities> amenities) {
        this.amenities = amenities;
    }

    public Set<String> getHotelRoomAmenityNames() {
        return amenities.stream().map(Amenities::getName).collect(Collectors.toSet());
    }

    // public void setHotelRoomAmenityNames(Set<String> hotelRoomAmenityNames) {
    // this.hotelRoomAmenityNames = hotelRoomAmenityNames;
    // }
    public int getNoRooms() {
        return noRooms;
    }

    public void setNoRooms(int noRooms) {
        this.noRooms = noRooms;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPolicies() {
        return policies;
    }

    public void setPolicies(String policies) {
        this.policies = policies;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        // If a HotelRoom belongs to the Hotel, the guest should have access to the Hotel's amenities
        this.amenities.addAll(hotel.getAmenities());
        this.hotel = hotel;
    }

    // Flattens the complex Java object into a String to be passed into an embedding model
	// Full disclosure: I had OpenAI ChatGPT help me make this as I figured it would format it
	// better than I could myself.
    public String toEmbeddingString() {
        StringBuilder sb = new StringBuilder();
    
        sb.append("Room Type: ").append(type != null ? type.getName() : "Unknown").append(". ");
        sb.append("Price: $").append(String.format("%.2f", price)).append(". ");
        sb.append("Discount: ").append(String.format("%.2f", discount)).append("%. ");
        sb.append("Number of Rooms: ").append(noRooms).append(". ");
        sb.append("Guests Allowed per Room: ").append(guestsPerRoom).append(". ");
        sb.append("Description: ").append(description).append(". ");
        sb.append("Policies: ").append(policies).append(". ");
        sb.append("Available from ").append(availabilityStartDate).append(" to ").append(availabilityEndDate).append(". ");
    
        if (amenities != null && !amenities.isEmpty()) {
            sb.append("Amenities: ");
            sb.append(amenities.stream()
                    .map(Amenities::getName)
                    .collect(Collectors.joining(", ")));
            sb.append(". ");
        }
    
        if (hotel != null) {
            sb.append("Hotel: ").append(hotel.getHotelName()).append(", located in ")
              .append(hotel.getCity()).append(", ").append(hotel.getState()).append(". ");
        }
        return sb.toString();
    }

    // formats the HotelRoom object into a JSON format
    public String toJsonObjectString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"type\": \"").append(type != null ? type.getName() : "Unknown").append("\", ");
        sb.append("\"price\": ").append(String.format("%.2f", price)).append(", ");
        sb.append("\"discount\": ").append(String.format("%.2f", discount)).append(", ");
        sb.append("\"noRooms\": ").append(noRooms).append(", ");
        sb.append("\"guestsAllowedPerRoom\": ").append(guestsPerRoom).append(", ");
        sb.append("\"description\": ").append(wrapInQuotes(description)).append(", ");
        sb.append("\"policies\": ").append(wrapInQuotes(policies)).append(", ");
        sb.append("\"availabilityStartDate\": ").append(wrapInQuotes(availabilityStartDate.format(dtFormatter))).append(", ");
        sb.append("\"availabilityEndDate\": ").append(wrapInQuotes(availabilityEndDate.format(dtFormatter))).append(", ");

    
        if (amenities != null && !amenities.isEmpty()) {
            sb.append("\"amenities\": [");
            sb.append(amenities.stream()
                    .map(Amenities::getName)
                    .map(amenity -> wrapInQuotes(amenity))
                    .collect(Collectors.joining(", ")));
            sb.append("], ");
        }
    
        if (hotel != null) {
            sb.append("\"hotelId\": ").append(hotel.getHotelId()).append(", ");
            sb.append("\"hotelName\": ").append(wrapInQuotes(hotel.getHotelName())).append(", ");
            sb.append("\"hotelAddress\": \"").append(hotel.getAddress()).append(", ").append(hotel.getCity()).append(", ").append(hotel.getState()).append("\"}");
        }
        return sb.toString();
    }

    public String wrapInQuotes(String input) {
        return "\"" + input + "\"";
    }

}