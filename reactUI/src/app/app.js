import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Login from "./UnauthorizedAccess/Login";
import RegisterUser from "./UnauthorizedAccess/RegisterUser";
import Home from "./AuthorizedAccess/Home";
import PrivateRoute from "./AuthorizedAccess/PrivateRoute";
import Booking from "./AuthorizedAccess/Booking";

let App = (props) => {
  return (
    <Router basename="/">
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<RegisterUser />} />
        <Route
          path="/booking/:hotelId"
          element={<PrivateRoute><Booking /></PrivateRoute>}
        />
        <Route
          path="/"
          element={<PrivateRoute><Home /></PrivateRoute>}
        />
      </Routes>
    </Router>
  );
};

export default App;
