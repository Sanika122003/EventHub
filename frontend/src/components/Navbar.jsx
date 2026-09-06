function Navbar() {
  return (
    <nav className="navbar">

      <div className="logo">
        🎟️ EventHub
      </div>

      <div className="nav-links">
        <a href="/">Home</a>
        <a href="/events">Events</a>
        <a href="/bookings">My Bookings</a>
        <a href="/login" className="login-btn">
          Login
        </a>
      </div>

    </nav>
  );
}

export default Navbar;