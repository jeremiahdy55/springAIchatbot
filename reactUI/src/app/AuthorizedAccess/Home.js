import React, { useState } from "react";
import { Link } from "react-router-dom";
import Navbar from "./Navbar";
import SearchBar from "./HomePageComponents.js/SearchBar";
import FilterSection from "./HomePageComponents.js/FilterSidebar";
import HotelDisplay from "./HomePageComponents.js/HotelDisplay";

const Home = () => {
  const [hotels, setHotels] = useState([]); // You would fill this from your API
  const [combinedParams, setCombinedParams] = useState({});

  // When Search calls back, merge search params into combinedParams
  const handleSearch = (searchParams) => {
    setCombinedParams((prev) => {
      const updated = { ...prev, ...searchParams };
      console.log("Combined Params on Search:", updated);
      return updated;
    });
  };

  // When Filter calls back, merge filter params into combinedParams
  const handleFilter = (filterParams) => {
    setCombinedParams((prev) => {
      const updated = { ...prev, ...filterParams };
      console.log("Combined Params on Filter:", updated);
      return updated;
    });
  };

  return (
    <>
      <Navbar />
      <SearchBar onSearch={handleSearch} />
      <div className="row">
        <FilterSection onFilter={handleFilter} />
        <HotelDisplay hotels={hotels} />
      </div>
    </>
  );
};

export default Home;
