import React, { useState } from "react";
import { Link } from "react-router-dom";
import Navbar from "./Navbar";

const Home = () => {
    return (
        <>
        <Navbar/>
        <div className="container d-flex justify-content-center">
            <div className="card p-4 shadow w-50">
                <h2 className="text-center">Delete me later</h2>
                <p>Hello World!</p>
            </div>
        </div>
        </>
    );
};

export default Home;
