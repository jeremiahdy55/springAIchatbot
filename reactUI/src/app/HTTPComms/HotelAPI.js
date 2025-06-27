export const filterHotels = async (filterDto) => {
  const token = sessionStorage.getItem("token");
  try {
    const res = await fetch("http://localhost:8787/api/hotel/filterHotels", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(filterDto),
    });
      
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    } else {
        console.log("Retrieved Hotel objects");
        const hotelsAsStrings = await res.json();
        console.log({hotelsAsStrings})
        const hotelData = hotelsAsStrings.map((str) => JSON.parse(str));
        console.log({hotelData})
        return hotelData;
    }
  } catch (err) {
    console.error("Filter hotels error:", err);
    throw err;
  }
};

export const fetchHotelById = async(hotelId) => {
    console.log(`Fetching hotel data with hotelId: ${hotelId}`);
    const token = sessionStorage.getItem("token");
  try {
    const res = await fetch(`http://localhost:8787/api/hotel/${hotelId}`, {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
      
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    } else {
        console.log("Retrieved Hotel objects");
        const hotelData = await res.json();
        console.log({hotelData})
        return hotelData;
    }
  } catch (err) {
    console.error("Fetch hotel error:", err);
    throw err;
  }
};

export const createBooking = async (bookingDto) => {
  const token = sessionStorage.getItem("token");
  try {
    const res = await fetch("http://localhost:8787/api/hotel/createBooking", {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(bookingDto),
    });
      
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    } else {
        return "Please check your email for a confirmation!";
    }
  } catch (err) {
    console.error("Booking error:", err);
    throw err;
  }
};

export const getChatResponse = async(userQuery, conversationId) => {
  const token = sessionStorage.getItem("token");
  try {
    const res = await fetch(`http://localhost:8787/api/chat/${conversationId}`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userQuery),
    });
      
    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    } else {
        return res.text();
    }
  } catch (err) {
    console.error("Chat Response error:", err);
    throw err;
  }
}