import React from "react";
import { Link, useNavigate } from "react-router-dom";

const Navbar = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        sessionStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <nav className="navbar navbar-expand-lg bg-secondary bg-opacity-75 shadow-sm px-4">
            <div className="container-fluid">
                <div className="d-flex gap-4">
                    <Link to="/" className="nav-link text-dark">Home</Link>
                    <Link to="/chats" className="nav-link text-dark">Chats</Link>
                </div>
                <button onClick={handleLogout} className="btn btn-danger ms-auto">
                    Logout
                </button>
            </div>
        </nav>
    );
};

export default Navbar;
