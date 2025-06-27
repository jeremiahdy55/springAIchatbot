import React from "react";
import { Modal, Button, Form } from "react-bootstrap";

const BookingForm = ({
  show,
  handleClose,
  formData,
  setFormData,
  handleSubmit,
  roomType,
}) => {
  // Constantly update form data values
  const handleChange = (e) => {
    setFormData((prev) => ({
      ...prev,
      [e.target.name]: e.target.value,
    }));
  };

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Book Room: {roomType}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group controlId="firstName" className="mb-2">
            <Form.Label>First Name</Form.Label>
            <Form.Control
              type="text"
              name="firstName"
              required
              value={formData.firstName}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="lastName" className="mb-2">
            <Form.Label>Last Name</Form.Label>
            <Form.Control
              type="text"
              name="lastName"
              required
              value={formData.lastName}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="email" className="mb-2">
            <Form.Label>Email</Form.Label>
            <Form.Control
              type="email"
              name="email"
              required
              value={formData.email}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="billingAddress" className="mb-2">
            <Form.Label>Billing Address</Form.Label>
            <Form.Control
              type="text"
              name="billingAddress"
              required
              value={formData.billingAddress}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="cardNo" className="mb-2">
            <Form.Label>Card Number</Form.Label>
            <Form.Control
              type="text"
              name="cardNo"
              value={formData.cardNo}
              onChange={handleChange}
              pattern="\d{14,}"
              required
              title="Card number must be at least 14 digits"
            />
          </Form.Group>

          <Form.Group controlId="checkInDate" className="mb-2">
            <Form.Label>Check-In Date</Form.Label>
            <Form.Control
              type="date"
              name="checkInDate"
              value={formData.checkInDate}
              onChange={handleChange}
            />
          </Form.Group>

          <Form.Group controlId="checkOutDate" className="mb-2">
            <Form.Label>Check-Out Date</Form.Label>
            <Form.Control
              type="date"
              name="checkOutDate"
              required
              value={formData.checkOutDate}
              onChange={handleChange}
            />
          </Form.Group>

          <Button variant="primary" type="submit" className="mt-3 w-100">
            Confirm Booking
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default BookingForm;
