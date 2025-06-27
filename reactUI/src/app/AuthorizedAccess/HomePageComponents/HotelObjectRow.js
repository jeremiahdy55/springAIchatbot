import React from "react";
import { useNavigate } from "react-router-dom";

const HotelObjectRow = ({ hotel }) => {

  // Define hooks
  const navigate = useNavigate();

  const handleClick = (evt) => {
      evt.preventDefault();
      navigate(`/booking/${hotel.hotelId}`)
  }
  const totalAvailableRooms = hotel.rooms.reduce((sum, room) => sum + (room.noRooms || 0), 0);
  return (
    <div
      className="hotel-row d-flex mb-4 p-3 border rounded"
      style={{ width: "100%", maxHeight: "260px", backgroundColor: "#f8f9fa" }}
      onClick={handleClick}
    >
      {/* render image on the left side*/}
      <div className="me-3" style={{ flex: "0 0 40%" }}>
        <img
          src={hotel.imageURL || "https://via.placeholder.com/150"}
          alt={`${hotel.name} preview`}
          style={{ width: "100%", height: "100%", objectFit: "cover" }}
          className="rounded"
        />
      </div>

      {/* display hotel details on the right */}
      <div className="hotel-details flex-grow-1">
        <h4>{hotel.name}</h4>
        <p className="mb-1">
          <strong>Address:</strong> {hotel.address}
        </p>
        <p className="mb-1">
          <strong>Star Rating:</strong> {hotel.starRating}⭐
        </p>
        <p className="mb-1">
          <strong>Average Price:</strong> ${hotel.avgPrice?.toFixed(2)}
        </p>
        <p className="mb-1">
          <strong>Total Available Rooms:</strong> {totalAvailableRooms}
        </p>
        <p className="mb-1">
          <strong>Total Available Guest Capacity:</strong> {hotel.guestCapacityRemaining}
        </p>
        <p className="mb-1">
          <strong>Amenities:</strong> {hotel.amenities?.join(", ")}
        </p>
        <p className="mb-1 text-muted">
          <em>{hotel.description}</em>
        </p>
      </div>
    </div>
  );
};

export default HotelObjectRow;
