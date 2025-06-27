import React, { useState } from "react";

function SearchBar({ onSearch }) {
  const [keyword, setKeyword] = useState("");
  const [noRooms, setNoRooms] = useState("");
  const [noGuests, setNoGuests] = useState("");
  const [checkInDate, setCheckInDate] = useState("");
  const [checkOutDate, setCheckOutDate] = useState("");

  const handleSearchClick = () => {
    onSearch({ keyword, noRooms, noGuests, checkInDate, checkOutDate });
  };

  return (
    <div
      className="container border rounded"
      style={{ margin: "auto", padding: 25, marginTop: 25, marginBottom: 15 }}
    >
      <h3>Narrow your search results</h3>
      <div className="row align-items-end">
        <div className="col-md-3">
          <label htmlFor="keyword">Hotel/City/State/Address</label>
          <input
            className="form-control"
            type="text"
            id="keyword"
            name="keyword"
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />
        </div>
        <div className="col-md-2">
          <label htmlFor="noRooms">No. Rooms</label>
          <input
            className="form-control"
            type="number"
            id="noRooms"
            name="noRooms"
            value={noRooms}
            onChange={(e) => setNoRooms(e.target.value)}
          />
        </div>
        <div className="col-md-2">
          <label htmlFor="noGuests">No. Guests</label>
          <input
            className="form-control"
            type="number"
            id="noGuests"
            name="noGuests"
            value={noGuests}
            onChange={(e) => setNoGuests(e.target.value)}
          />
        </div>
        <div className="col-md-2">
          <label htmlFor="checkInDate">Check-In</label>
          <input
            type="date"
            className="form-control"
            id="checkInDate"
            name="checkInDate"
            value={checkInDate}
            onChange={(e) => setCheckInDate(e.target.value)}
          />
        </div>
        <div className="col-md-2">
          <label htmlFor="checkOutDate">Check-Out</label>
          <input
            type="date"
            className="form-control"
            id="checkOutDate"
            name="checkOutDate"
            value={checkOutDate}
            onChange={(e) => setCheckOutDate(e.target.value)}
          />
        </div>
        <div className="col-md-1">
          <button
            className="btn btn-primary w-100"
            id="searchBtn"
            onClick={handleSearchClick}
          >
            SEARCH
          </button>
        </div>
      </div>
    </div>
  );
}

export default SearchBar;