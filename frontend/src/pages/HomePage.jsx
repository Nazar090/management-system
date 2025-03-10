// src/pages/HomePage.jsx
import { useEffect, useState } from "react";
import axios from "axios";
import "./HomePage.css"; // Our custom styles

function HomePage() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetchHome();
  }, []);

  const fetchHome = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/home");
      setMessage(response.data); // e.g. "Welcome to Home Page!"
    } catch (error) {
      console.error("Error fetching home page:", error);
    }
  };

  return (
    <div className="home-container">
      <h1 className="home-title">Task Weave</h1>
      <p className="home-message">Biggest Management System You Ever Seen</p>

      <div className="filler">
        <p>
          description
        </p>
      </div>
    </div>
  );
}

export default HomePage;
