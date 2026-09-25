import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <nav className="navbar">

      <div className="logo">
        🎟️ EventHub
      </div>

      <div className="nav-links">
        <a href="/">Home</a>
        <a href="/events">Events</a>
        <a href="/my-bookings">My Bookings</a>

        {token ? (
          <button onClick={handleLogout} className="logout-btn">
            Logout
          </button>
        ) : (
          <a href="/login" className="login-btn">
            Login
          </a>
        )}
      </div>

    </nav>
  );
}

export default Navbar;