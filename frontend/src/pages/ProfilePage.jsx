// src/pages/ProfilePage.jsx
import React, { useState, useEffect } from "react";
import axios from "axios";
import "./ProfilePage.css";

function ProfilePage() {
  const [profile, setProfile] = useState({});
  const [updateName, setUpdateName] = useState("");
  const [updateNickname, setUpdateNickname] = useState("");
  const [updateEmail, setUpdateEmail] = useState("");

  useEffect(() => {
    fetchProfile();
  }, []);

  const fetchProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        window.location.href = "/login";
        return;
      }

      const response = await axios.get("http://localhost:8080/api/user/profile", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setProfile(response.data);
    } catch (error) {
      console.error("Error fetching profile:", error);
      window.location.href = "/login";
    }
  };

  const handleUpdateProfile = async () => {
    try {
      const token = localStorage.getItem("token");
      await axios.post(
        "http://localhost:8080/api/user/profile/update",
        {
          name: updateName || undefined,
          nickname: updateNickname || undefined,
          email: updateEmail || undefined,
        },
        {
          headers: { Authorization: `Bearer ${token}` },
        }
      );
      alert("Profile updated!");
      fetchProfile(); // re-fetch updated info
    } catch (error) {
      console.error("Error updating profile:", error);
      alert("Update failed.");
    }
  };

  return (
    <div className="profile-page">
      <div className="profile-box">
        <h2 className="profile-title">Profile Page</h2>
        <div className="profile-info">
          <p>
            <strong>Name:</strong> {profile.name}
          </p>
          <p>
            <strong>Nickname:</strong> {profile.nickname}
          </p>
          <p>
            <strong>Email:</strong> {profile.email}
          </p>
        </div>
      </div>

      <div className="update-box">
        <h3 className="update-title">Update Profile</h3>

        <input
          type="text"
          placeholder="New name"
          value={updateName}
          onChange={(e) => setUpdateName(e.target.value)}
        />
        <input
          type="text"
          placeholder="New nickname"
          value={updateNickname}
          onChange={(e) => setUpdateNickname(e.target.value)}
        />
        <input
          type="email"
          placeholder="New email"
          value={updateEmail}
          onChange={(e) => setUpdateEmail(e.target.value)}
        />

        <button className="btn-update" onClick={handleUpdateProfile}>
          Save Changes
        </button>
      </div>
    </div>
  );
}

export default ProfilePage;
