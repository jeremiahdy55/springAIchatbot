import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import Login from "./UnauthorizedAccess/Login";
import RegisterUser from "./UnauthorizedAccess/RegisterUser";

let App = (props) => {
    return (
    <Router>
      <div className="container mt-5">
        {/* <nav className="mb-4">
          <Link to="/" className="btn btn-primary me-2">Login</Link>
          <Link to="/register" className="btn btn-secondary">Register</Link>
        </nav> */}
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/register" element={<RegisterUser />} />
        </Routes>
      </div>
    </Router>
    )
}

export default App;