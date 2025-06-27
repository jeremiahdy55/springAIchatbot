import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import Navbar from "./Navbar";
import { fetchHotelById } from "../HTTPComms/HotelAPI";
import HotelRoomObjectRow from "./BookingPageComponents/HotelRoomObjectRow";

const Booking = () => {
  const { hotelId } = useParams(); // get the path variable hotelId
  const [hotel, setHotel] = useState(null); // hotel data
  const [loading, setLoading] = useState(true); // loading flag

  useEffect(() => {
    const loadHotel = async () => {
      try {
        const data = await fetchHotelById(hotelId); // fetch hotel data for this hotelId
        setHotel(data);
      } catch (err) {
        console.error("Error fetching hotel:", err);
      } finally {
        setLoading(false);
      }
    };
    loadHotel();
  }, [hotelId]); // re-run if hotelId changes

  // Load the page while waiting, break the page if no data found
  if (loading) return <p>Loading hotel...</p>;
  if (!hotel) return <p>Hotel not found.</p>;

  const totalAvailableRooms = hotel.rooms.reduce((sum, room) => sum + (room.noRooms || 0), 0);
  const hotelHasAvailableRooms = totalAvailableRooms > 0;

  // Order the rooms so that the webpage will render Deluxe -> Single -> Double -> Triple
  const roomOrder = ["deluxe", "single", "double", "triple"];
  const sortedRooms = [...hotel.rooms].sort((a, b) => {
    const indexA = roomOrder.indexOf(a.type.toLowerCase());
    const indexB = roomOrder.indexOf(b.type.toLowerCase());

    // If type not found in roomOrder, put it after known types
    const posA = indexA === -1 ? roomOrder.length : indexA;
    const posB = indexB === -1 ? roomOrder.length : indexB;

    return posA - posB;
  });

  return (
    <>
      <Navbar />
      <div className="container mt-5">
        <div
          className="d-flex"
          style={{ gap: "20px", alignItems: "flex-start" }}
        >
          {/* Hotel info on left */}
          <div style={{ flex: 1 }}>
            <h2>
              <strong>{hotel.name}</strong>
            </h2>
            <h5>
              <em>{hotel.description}</em>
            </h5>
            <p>
              <strong>Star Rating:</strong> {hotel.starRating}
            </p>
            <p>
              <strong>Amenities:</strong> {hotel.amenities?.join(", ")}
            </p>
            <p>
              <strong>Total Available Rooms:</strong> {totalAvailableRooms}
            </p>
            <p>
              <strong>Total Available Guest Capacity:</strong> {hotel.guestCapacityRemaining}
            </p>
            <p>
              <strong>Average Price:</strong> ${hotel.avgPrice?.toFixed(2)} | <strong>Discount:</strong> ${hotel.discount}
            </p>
            <p>
              <strong>Address:</strong> {hotel.address}
            </p>
            <p>
              <strong>Phone:</strong> {hotel.mobile}
            </p>
            <p>
              <strong>Email:</strong> {hotel.email}
            </p>
          </div>

          {/* Image on right */}
          <div style={{ height: "300px" }}>
            <img
              src={hotel.imageURL || "https://via.placeholder.com/300x400"}
              alt={`${hotel.name} preview`}
              style={{
                width: "100%",
                height: "100%",
                objectFit: "cover",
                borderRadius: "0.25rem",
              }}
            />
          </div>
        </div>

        <hr />
        {hotel.rooms && hotel.rooms.length > 0 && hotelHasAvailableRooms ? (
          sortedRooms.map((room) => <HotelRoomObjectRow room={room} />)
        ) : (
          <p>No rooms available</p>
        )}
      </div>
    </>
  );
};

export default Booking;
