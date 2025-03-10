// src/components/Navbar.jsx
import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  const token = localStorage.getItem("token");

  const handleLogout = async () => {
    // Your logout logic here
    localStorage.removeItem("token");
    window.location.href = "/login";
  };

  return (
    <nav className="navbar">
      <div className="left-links">
        <Link to="/" className="nav-link">Home</Link>
      </div>
      <div className="right-links">
        {token ? (
          <>
            <Link to="/profile" className="nav-link">Profile</Link>
            <button onClick={handleLogout} className="nav-link">Logout</button>
          </>
        ) : (
          <>
            <Link to="/login" className="nav-link">Login</Link>
            <Link to="/register" className="nav-link">Register</Link>
          </>
        )}
      </div>
    </nav>
  );
}

export default Navbar;
