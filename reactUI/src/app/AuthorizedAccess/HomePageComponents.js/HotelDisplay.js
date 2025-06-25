import React, { useState } from "react";

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
        <h4 className="mt-2">List of Hotels:</h4>
      </div>

      <div id="listHotel">
        {hotels && hotels.length > 0 ? (
          hotels.map((hotel) => (
            <div key={hotel.id} className="hotel-item">
              {hotel.name}
            </div>
          ))
        ) : (
          <p>No hotels to display</p>
        )}
      </div>
    </div>
  );
}
export default HotelDisplay;
