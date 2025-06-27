package com.hotelchatbot.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotelid")
	private Integer hotelId;
	private String hotelName;
	private String address;
	private String city;
	private String state;
	private int starRating;
	private double averagePrice;
	private double discount;
	private String description;
	private String email;
	private String mobile;
	private int timesBooked;
	private String imageURL;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Amenities> amenities = new HashSet<>();

	@OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<HotelRoom> hotelRooms = new HashSet<>();

	public Hotel() {}

	public Hotel(String hotelName, String address, String city, String state, int starRating,
			double averagePrice, double discount, String description, String email,
			String mobile, int timesBooked, Set<Amenities> amenities, String imageURL) {
		this.hotelName = hotelName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.starRating = starRating;
		this.averagePrice = averagePrice;
		this.discount = discount;
		this.description = description;
		this.email = email;
		this.mobile = mobile;
		this.timesBooked = timesBooked;
		this.amenities = amenities;
		this.imageURL = imageURL;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getTimesBooked() {
		return timesBooked;
	}

	public void setTimesBooked(int timesBooked) {
		this.timesBooked = timesBooked;
	}

	public Set<HotelRoom> getHotelRooms() {
		return hotelRooms;
	}

	public void setHotelRooms(Set<HotelRoom> hotelRooms) {
		this.hotelRooms = hotelRooms;
	}

	public Set<Amenities> getAmenities() {
		return amenities;
	}

	public void setAmenities(Set<Amenities> hotelAmenities) {
		this.amenities = hotelAmenities;
	}

	public Set<String> getHotelAmenityNames() {
		return amenities.stream().map(Amenities::getName).collect(Collectors.toSet());
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public Integer getHotelId() {
		return hotelId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStarRating() {
		return starRating;
	}

	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}

	public double getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(double averagePrice) {
		this.averagePrice = averagePrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void addRoom(HotelRoom room) {
		hotelRooms.add(room);
		room.setHotel(this);
	}
	
	public void removeRoom(HotelRoom room) {
		hotelRooms.remove(room);
		room.setHotel(null);
	}

	// Flattens the complex Java object into a String to be passed into an embedding model
	// Full disclosure: I had OpenAI ChatGPT help me make this as I figured it would format it
	// better than I could myself.
	public String toEmbeddingString() {
		StringBuilder sb = new StringBuilder();
	
		sb.append("Hotel Name: ").append(hotelName).append(". ");
		sb.append("Address: ").append(address).append(", ").append(city).append(", ").append(state).append(". ");
		sb.append("Star Rating: ").append(starRating).append(". ");
		sb.append("Average Price: $").append(String.format("%.2f", averagePrice)).append(". ");
		sb.append("Discount: ").append(String.format("%.2f", discount)).append("%. ");
		sb.append("Description: ").append(description).append(". ");
		sb.append("Email: ").append(email).append(". ");
		sb.append("Mobile: ").append(mobile).append(". ");
		sb.append("Times Booked: ").append(timesBooked).append(". ");
	
		if (amenities != null && !amenities.isEmpty()) {
			sb.append("Amenities: ");
			for (Amenities amenity : amenities) {
				sb.append(amenity.getName()).append(", ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.append(". ");
		}
	
		if (hotelRooms != null && !hotelRooms.isEmpty()) {
			int guestsAvailable = 0;
			sb.append("Rooms: ");
			for (HotelRoom room : hotelRooms) {
				guestsAvailable += (room.getNoRooms() * room.getGuestsPerRoom());
				sb.append(room.getType().getName()).append(" with features: ");
				Set<Amenities> roomAmenities = room.getAmenities();
				roomAmenities.removeAll(this.amenities);
				sb.append(roomAmenities.stream().map(Amenities::getName).collect(Collectors.joining(", "))).append(". ");
			}
			sb.append("Available Guest Capacity: ").append(guestsAvailable).append(". ");
		}
	
		return sb.toString();
	}

	// formats the Hotel object into a JSON format with slightly less granularity
    public String toJsonObjectString() {
        StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"hotelId\": ").append(hotelId).append(", ");
		sb.append("\"name\": ").append(wrapInQuotes(hotelName)).append(", ");
		sb.append("\"address\": \"").append(address).append(", ").append(city).append(", ").append(state).append("\", ");
		sb.append("\"starRating\": ").append(starRating).append(", ");
		sb.append("\"avgPrice\": ").append(String.format("%.2f", averagePrice)).append(", ");
		sb.append("\"discount\": ").append(String.format("%.2f", discount)).append(", ");
		sb.append("\"description\": ").append(wrapInQuotes(description)).append(", ");
		sb.append("\"email\": ").append(wrapInQuotes(email)).append(", ");
		sb.append("\"mobile\": ").append(wrapInQuotes(mobile)).append(", ");
		sb.append("\"timesBooked\": ").append(timesBooked).append(", ");
	
		if (amenities != null && !amenities.isEmpty()) {
			sb.append("\"amenities\": [");
			for (Amenities amenity : amenities) {
				sb.append(wrapInQuotes(amenity.getName())).append(", ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.append("], ");
		}
		
	
		if (hotelRooms != null && !hotelRooms.isEmpty()) {
			int guestsAvailable = 0;
			StringBuilder roomSB = new StringBuilder();
			roomSB.append("\"rooms\": [");
			for (HotelRoom room : hotelRooms) {
				guestsAvailable += (room.getNoRooms() * room.getGuestsPerRoom());
				roomSB.append("{");
				roomSB.append("\"hotelRoomId\": ").append(room.getHotelRoomId()).append(", ");
				roomSB.append("\"type\": ").append(wrapInQuotes(room.getType().getName())).append(", ");
				roomSB.append("\"availabilityStartDate\": ").append(wrapInQuotes(room.getAvailabilityStartDate())).append(", ");
				roomSB.append("\"availabilityEndDate\": ").append(wrapInQuotes(room.getAvailabilityEndDate())).append(", ");
				Set<Amenities> roomAmenities = room.getAmenities();
				roomAmenities.removeAll(this.amenities);
				roomSB.append("\"amenities\": [").append(roomAmenities.stream().map(Amenities::getName).map(amenity -> wrapInQuotes(amenity)).collect(Collectors.joining(", "))).append("]}, ");
			}
			roomSB.deleteCharAt(roomSB.length()-1);
			roomSB.deleteCharAt(roomSB.length()-1);
			roomSB.append("]");
			roomSB.append("}");
			sb.append("\"guestCapacityRemaining\": ").append(guestsAvailable).append(", ");
			sb.append(roomSB);
		}
        return sb.toString();
    }

	// formats the Hotel object into a JSON format with full granularity (included id, HotelRooms are fully expanded, etc.)
    public String toJsonObjectStringFullDetail() {
        StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"hotelId\": ").append(hotelId).append(", ");
		sb.append("\"name\": ").append(wrapInQuotes(hotelName)).append(", ");
		sb.append("\"address\": \"").append(address).append(", ").append(city).append(", ").append(state).append("\", ");
		sb.append("\"starRating\": ").append(starRating).append(", ");
		sb.append("\"avgPrice\": ").append(String.format("%.2f", averagePrice)).append(", ");
		sb.append("\"discount\": ").append(String.format("%.2f", discount)).append(", ");
		sb.append("\"description\": ").append(wrapInQuotes(description)).append(", ");
		sb.append("\"email\": ").append(wrapInQuotes(email)).append(", ");
		sb.append("\"mobile\": ").append(wrapInQuotes(mobile)).append(", ");
		sb.append("\"timesBooked\": ").append(timesBooked).append(", ");
		sb.append("\"imageURL\": ").append(wrapInQuotes(imageURL)).append(", ");

	
		if (amenities != null && !amenities.isEmpty()) {
			sb.append("\"amenities\": [");
			for (Amenities amenity : amenities) {
				sb.append(wrapInQuotes(amenity.getName())).append(", ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			sb.append("], ");
		}
		
	
		if (hotelRooms != null && !hotelRooms.isEmpty()) {
			int guestsAvailable = 0;
			StringBuilder roomSB = new StringBuilder();
			roomSB.append("\"rooms\": [");
			for (HotelRoom room : hotelRooms) {
				guestsAvailable += (room.getNoRooms() * room.getGuestsPerRoom());
				roomSB.append("{");
				roomSB.append("\"hotelRoomId\": ").append(room.getHotelRoomId()).append(", ");
				roomSB.append("\"type\": ").append(wrapInQuotes(room.getType().getName())).append(", ");
				roomSB.append("\"availabilityStartDate\": ").append(wrapInQuotes(room.getAvailabilityStartDate())).append(", ");
				roomSB.append("\"availabilityEndDate\": ").append(wrapInQuotes(room.getAvailabilityEndDate())).append(", ");
				roomSB.append("\"description\": ").append(wrapInQuotes(room.getDescription())).append(", ");
				roomSB.append("\"policies\": ").append(wrapInQuotes(room.getPolicies())).append(", ");
				roomSB.append("\"discount\": ").append(String.format("%.2f", room.getDiscount())).append(", ");
				roomSB.append("\"price\": ").append(String.format("%.2f", room.getPrice())).append(", ");
				roomSB.append("\"noRooms\": ").append(room.getNoRooms()).append(", ");
				roomSB.append("\"guestsPerRoom\": ").append(room.getGuestsPerRoom()).append(", ");
				Set<Amenities> roomAmenities = room.getAmenities();
				roomAmenities.removeAll(this.amenities);
				roomSB.append("\"amenities\": [").append(roomAmenities.stream().map(Amenities::getName).map(amenity -> wrapInQuotes(amenity)).collect(Collectors.joining(", "))).append("]}, ");
			}
			roomSB.deleteCharAt(roomSB.length()-1);
			roomSB.deleteCharAt(roomSB.length()-1);
			roomSB.append("]");
			roomSB.append("}");
			sb.append("\"guestCapacityRemaining\": ").append(guestsAvailable).append(", ");
			sb.append(roomSB);
		}
        return sb.toString();
    }

    public String wrapInQuotes(String input) {
        return "\"" + input + "\"";
    }

}
