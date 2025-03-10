// src/pages/RegisterPage.jsx
import React, { useState } from "react";
import axios from "axios"; // Make sure to install: npm install axios
import "./RegisterPage.css";

function RegisterPage() {
  const [email, setEmail] = useState("");
  const [nickname, setNickname] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const [name, setName] = useState("");

  const handleRegister = async () => {
    try {
      // POST to your backend's /api/auth/register
      const response = await axios.post("http://localhost:8080/api/auth/register", {
        email,
        nickname,
        password,
        repeatPassword,
        name,
      });

      // On success, alert the user or redirect
      alert("Registration successful! Please log in.");
      window.location.href = "/login";
    } catch (error) {
      console.error("Registration failed:", error);
      alert("Error registering user.");
    }
  };

  return (
    <div className="register-page">
      <div className="register-box">
        <h2 className="register-title">Register</h2>

        {/* Email Field */}
        <div className="form-group">
          <label>
            Email <span className="required">*</span>
          </label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>

        {/* Nickname Field */}
        <div className="form-group">
          <label>
            Nickname <span className="required">*</span>
          </label>
          <input
            type="text"
            value={nickname}
            onChange={(e) => setNickname(e.target.value)}
          />
        </div>

        {/* Password Field */}
        <div className="form-group">
          <label>
            Password <span className="required">*</span>
          </label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>

        {/* Repeat Password Field */}
        <div className="form-group">
          <label>
            Repeat Password <span className="required">*</span>
          </label>
          <input
            type="password"
            value={repeatPassword}
            onChange={(e) => setRepeatPassword(e.target.value)}
          />
        </div>

        {/* Name Field */}
        <div className="form-group">
          <label>
            Name <span className="optional">(optional)</span>
          </label>
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>

        {/* Register Button */}
        <button className="btn btn-register" onClick={handleRegister}>
          Register
        </button>
      </div>
    </div>
  );
}

export default RegisterPage;
