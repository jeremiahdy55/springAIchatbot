import React, { useState } from "react";
import HotelObjectRow from "./HotelObjectRow";

function HotelDisplay({ hotels }) {
  return (
    <div className="col-7 border rounded" style={{ marginLeft: 50 }}>
      <div
        style={{
          textAlign: "center",
          fontSize: 20,
          fontFamily: `"Trebuchet MS", Helvetica, sans-serif`,
        }}
      >
        <h3 className="mt-2">List of Hotels:</h3>
      </div>

      <div id="listHotel">
        {hotels && hotels.length > 0 ? (
          hotels.map((hotel) => (
            <HotelObjectRow key={hotel.id || hotel.name} hotel={hotel} />
          ))
        ) : (
          <p>No hotels to display</p>
        )}
      </div>
    </div>
  );
}
export default HotelDisplay;
