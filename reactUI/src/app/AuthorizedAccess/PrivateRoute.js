import React, { useEffect, useState } from "react";
import { Navigate } from "react-router-dom";
import { isAuthenticated } from "../HTTPComms/UserAuth";

// Wrapper component to ensure non-authorized users cannot access secure content
const PrivateRoute = ({ children }) => {
  const [auth, setAuth] = useState(null); // null = unknown yet

  useEffect(() => {
    const checkAuth = async () => {
      const result = await isAuthenticated();
      setAuth(result);
    };
    checkAuth();
  }, []);

  if (auth === null) {
    return <div>Loading...</div>;
  }

  return auth ? children : <Navigate to="/login" />;
};

export default PrivateRoute;
