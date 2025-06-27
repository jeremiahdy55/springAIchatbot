import React from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";

const RedirectToBookingButton = ({ message, index }) => {
  const navigate = useNavigate();
  return (
    <Button
      variant="warning"
      type="button"
      className="mt-2 w-100"
      onClick={() =>
        navigate("/booking/" + parseInt(message.text.match(/\d+/)[0], 10))
      }
    >
      Make Booking!
    </Button>
  );
};
export default RedirectToBookingButton;