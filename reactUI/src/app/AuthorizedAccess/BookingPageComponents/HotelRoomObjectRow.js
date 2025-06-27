import React, { useState } from "react";
import BookingForm from "./BookingForm";
import { createBooking } from "../../HTTPComms/HotelAPI";

const HotelRoomObjectRow = ({ room }) => {
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    billingAddress: "",
    cardNo: "",
    checkInDate: "",
    checkOutDate: "",
  });

  const openBookingForm = () => setShowModal(true);
  const closeBookingForm = () => setShowModal(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Check that checkOut is ON or AFTER checkIn
    const checkIn = new Date(formData.checkInDate);
    const checkOut = new Date(formData.checkOutDate);
    if (checkOut <= checkIn) {
      alert("Check-out date must be the same day or after check-in date.");
      return;
    }

    console.log("Form submitted:", formData);
    const bookingDto = {...formData, hotelRoomId: room.hotelRoomId}
    console.log((bookingDto))
    alert(await createBooking(bookingDto));
    closeBookingForm();
    window.location.reload();
  };

  return room.noRooms > 0 ? (
    <>
      <div
        key={room.hotelRoomId}
        className="border rounded p-3 mb-3"
        style={{ cursor: "pointer" }}
        onClick={openBookingForm}
      >
        <h4><strong>{room.type}</strong></h4>
        <h6><em>{room.description}</em></h6>
        <h6><em>{room.policies}</em></h6>
        <p><strong>Price:</strong> ${room.price?.toFixed(2)} | <strong>Discount:</strong> ${room.discount}</p>
        <p><strong>Amenities:</strong> {room.amenities?.join(", ")}</p>
        <p><strong>Availability:</strong> {room.availabilityStartDate} to {room.availabilityEndDate}</p>
        <p><strong>Rooms Available:</strong> {room.noRooms}</p>
        <p><strong>Guests per Room:</strong> {room.guestsPerRoom}</p>
      </div>

      <BookingForm
        show={showModal}
        handleClose={closeBookingForm}
        formData={formData}
        setFormData={setFormData}
        handleSubmit={handleSubmit}
        roomType={room.type}
      />
    </>
  ) : null;
};

export default HotelRoomObjectRow;
