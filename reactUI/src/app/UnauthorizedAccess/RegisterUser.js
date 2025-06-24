import React, { useState } from "react";

const RegisterUser = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = (e) => {
    e.preventDefault();
    alert(`Registering user:\nUsername: ${username}\nPassword: ${password}`);
  };

  return (
    <div>
<div className="container d-flex justify-content-center">
            <div className="card p-4 shadow w-50">
                <h2 className="text-center">Register</h2>
                <form onSubmit={handleRegister}>
                    <div className="mb-3">
                        <label className="form-label">Username:</label>
                        <input type="text" className="form-control" value={username}
                            onChange={(e) => setUsername(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Password:</label>
                        <input type="password" className="form-control" value={password}
                            onChange={(e) => setPassword(e.target.value)} required />
                    </div>
                </form>
                <button type="submit" className="btn btn-primary">Register</button>
            </div>
        </div>
    </div>
  );
};

export default RegisterUser;
