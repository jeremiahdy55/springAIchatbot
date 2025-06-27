import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "./Navbar";
import SearchBar from "./HomePageComponents/SearchBar";
import FilterSection from "./HomePageComponents/FilterSidebar";
import HotelDisplay from "./HomePageComponents/HotelDisplay";
import { filterHotels } from "../HTTPComms/HotelAPI";
import AIChatbotButton from "./HomePageComponents/AIChatbotButton";

const Home = () => {
  // Define hooks
  const navigate = useNavigate();

  const [hotels, setHotels] = useState([]);
  const [combinedParams, setCombinedParams] = useState({});

  // When SearchBar calls back, merge search params into combinedParams
  // and call jwtsecurity -> hotelchatbot to retrieve hotel data that fits the new params
  const handleSearch = async (searchParams) => {
    const updatedParams = { ...combinedParams, ...searchParams };
    console.log({ updatedParams });
    setCombinedParams(updatedParams);
    const hotelData = await filterHotels(updatedParams);
    setHotels(hotelData);
  };

  // When FilterSidebar calls back, merge filter params into combinedParams
  // and call jwtsecurity -> hotelchatbot to retrieve hotel data that fits the new params
  const handleFilter = async (filterParams) => {
    const updatedParams = { ...combinedParams, ...filterParams };
    console.log({ updatedParams });
    setCombinedParams(updatedParams);
    const hotelData = await filterHotels(updatedParams);
    setHotels(hotelData);
  };

  // On component mount, load all the hotels
  useEffect(() => {
    const loadHotels = async () => {
      try {
        const hotelData = await filterHotels({}); // fetch hotels
        setHotels(hotelData);
      } catch (err) {
        console.error("Error fetching hotels:", err);
      }
    };
    loadHotels();
  }, []);

  return (
    <>
      <Navbar />
      <SearchBar onSearch={handleSearch} />
      <div className="row">
        <FilterSection onFilter={handleFilter} />
        <HotelDisplay hotels={hotels} />
      </div>
      <AIChatbotButton />
    </>
  );
};

export default Home;
