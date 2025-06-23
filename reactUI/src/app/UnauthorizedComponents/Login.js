import React, { useState } from "react";
import { Link } from "react-router-dom";

const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        alert(`Logging in with\nUsername: ${username}\nPassword: ${password}`);
    };

    return (
        <div className="container d-flex justify-content-center">
            <div className="card p-4 shadow w-50">
                <h2 className="text-center">Login</h2>
                <form onSubmit={handleSubmit}>
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
                <button type="submit" className="btn btn-primary">Login</button>
                <div className="text-center mt-3"><Link to="/register" className="text-decoration-none">Register</Link></div>
            </div>
        </div>
    );
};

export default Login;
