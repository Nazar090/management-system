import React, { useState } from "react";
import axios from "axios";  // Make sure to install: npm install axios
import "./LoginPage.css";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      // POST to your backend's /api/auth/login
      const response = await axios.post("http://localhost:8080/api/auth/login", {
        email,
        password,
      });
      // Suppose the backend returns { token: "..." }
      localStorage.setItem("token", response.data.token);

      // Redirect to some page (e.g., profile or home)
      window.location.href = "/profile";
    } catch (error) {
      console.error("Login failed:", error);
      alert("Invalid credentials");
    }
  };

  const handleGoogleLogin = () => {
    // Redirect to your backend's Google OAuth2 endpoint
    window.location.href = "http://localhost:8080/login";
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2 className="login-title">Login</h2>

        <div className="form-group">
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        <div className="form-group">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        <button className="btn btn-login" onClick={handleLogin}>
          Login
        </button>
        <button className="btn btn-google" onClick={handleGoogleLogin}>
          Login with Google
        </button>
      </div>
    </div>
  );
}

export default LoginPage;
