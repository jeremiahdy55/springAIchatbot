import React, { useState } from "react";

function FilterSection({ onFilter }) {
  const [starRatings, setStarRatings] = useState([]);
  const [priceRange, setPriceRange] = useState(500);
  const [amenities, setAmenities] = useState({
    parking: false,
    wifi: false,
    airCon: false,
    iceMachine: false,
    breakfast: false,
    kitchenette: false,
    microwave: false,
    miniFridge: false,
    coffeeMaker: false,
    pool: false,
    fitnessCenter: false,
    airportShuttle: false,
    tourBusShuttle: false,
    nearbyBeach: false,
    nearbyAmusementPark: false,
  });

  const toggleStar = (value) => {
    setStarRatings((prev) =>
      prev.includes(value) ? prev.filter((v) => v !== value) : [...prev, value]
    );
  };

  const toggleAmenity = (key) => {
    setAmenities((prev) => ({ ...prev, [key]: !prev[key] }));
  };

  const handleFilterClick = () => {
    const checkedAmenities = Object.keys(amenities).filter((key) => amenities[key]);
    const amenityList = checkedAmenities.map((amenity) => amenityLabels[amenity]);
    onFilter({
      starRatings: starRatings,
      budget: priceRange,
      amenityNames: amenityList,
    });
  };

  const amenityLabels = {
    parking: "Parking",
    wifi: "Wifi",
    airCon: "Air Conditioning",
    iceMachine: "Ice Machine",
    breakfast: "Breakfast",
    kitchenette: "Kitchenette",
    microwave: "Microwave",
    miniFridge: "Mini Fridge",
    coffeeMaker: "Coffee Maker",
    pool: "Pool",
    fitnessCenter: "Fitness Center",
    airportShuttle: "Airport Shuttle",
    tourBusShuttle: "Tour Bus Shuttle",
    nearbyBeach: "Beach Nearby",
    nearbyAmusementPark: "Amusement Park Nearby",
  };

  return (
    <div
      className="col-2 border rounded"
      style={{ marginLeft: 50, padding: 25 }}
    >
      <h5>Star Rating:</h5>
      {[1, 2, 3, 4, 5].map((star) => (
        <div className="form-check-inline" key={star}>
          <label className="form-check-label me-2">
            <input
              type="checkbox"
              className="form-check-input me-1"
              id={`${star}_star_rating`}
              value={star}
              checked={starRatings.includes(star)}
              onChange={() => toggleStar(star)}
            />
            {star}
          </label>
        </div>
      ))}

      <div className="mt-4 w-100">
        <h5>Range:</h5>
        <div className="slidecontainer w-100">
          <input
            type="range"
            min="1"
            max="500"
            value={priceRange}
            className="slider w-100"
            id="priceRange"
            onChange={(e) => setPriceRange(parseInt(e.target.value, 10))}
          />
          <p>
            Price: $<span id="priceValue">{priceRange}</span>
          </p>
        </div>
      </div>

      <div className="mt-4">
        <h5>Amenities:</h5>
        {Object.keys(amenities).map((key) => (
          <div className="form-check" key={key}>
            <input
              type="checkbox"
              className="form-check-input"
              id={`amenity_${key}`}
              checked={amenities[key]}
              onChange={() => toggleAmenity(key)}
            />
            <label className="form-check-label" htmlFor={`amenity_${key}`}>
              {amenityLabels[key]}
            </label>
          </div>
        ))}
      </div>

      <button
        className="btn btn-primary mt-3 w-100"
        type="button"
        id="filterBtn"
        onClick={handleFilterClick}
      >
        FILTER
      </button>
    </div>
  );
}

export default FilterSection;
