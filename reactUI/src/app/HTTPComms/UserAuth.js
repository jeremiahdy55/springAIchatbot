export const isAuthenticated = async () => {
  const token = sessionStorage.getItem("token");
  if (!token) return false;

  try {
    const res = await fetch("http://localhost:8787/api/auth/validate", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return res.ok;
  } catch (err) {
    console.error("Validation failed", err);
    return false;
  }
};

export const login = async (userObj) => {
  try {
    const res = await fetch("http://localhost:8787/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userObj),
    });

    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    }

    const userData = await res.json(); // get JwtAuthResponse as JSON
    sessionStorage.setItem("token", userData.accessToken); // set the JWT token for authentication
  } catch (err) {
    console.error("Login error:", err);
    throw err;
  }
};

export const register = async (userObj) => {
  try {
    const res = await fetch("http://localhost:8787/api/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userObj),
    });

    if (!res.ok) {
      throw new Error(`HTTP error! status: ${res.status}`);
    } else {
      console.log("Successfully saved user: " + userObj.username);
      return true;
    }
  } catch (err) {
    console.error("Login error:", err);
    throw err;
  }
};
