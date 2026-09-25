import { useEffect, useState } from "react";

function MyBookings() {

  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const handleCancel = async (bookingId) => {

    const token = localStorage.getItem("token");

    try {

      const response = await fetch(
        `http://localhost:8080/api/bookings/${bookingId}/cancel`,
        {
          method: "PUT",
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!response.ok) {
        throw new Error("Cancellation failed.");
      }

      alert("Booking cancelled successfully.");

      fetchBookings();

    } catch (error) {

      alert("Unable to cancel booking.");

    }
  };

  const fetchBookings = async () => {

    const token = localStorage.getItem("token");

    if (!token) {
      setError("Please login to view your bookings.");
      setLoading(false);
      return;
    }

    try {

      // Get logged-in user's ID
      const profileResponse = await fetch(
        "http://localhost:8080/api/users/profile",
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!profileResponse.ok) {
        throw new Error("Unable to get user profile.");
      }

      const profile = await profileResponse.json();

      // Get user's bookings
      const bookingResponse = await fetch(
        `http://localhost:8080/api/bookings/user/${profile.id}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!bookingResponse.ok) {
        throw new Error("Unable to fetch bookings.");
      }

      const data = await bookingResponse.json();

      setBookings(data);

    } catch (error) {

      console.error("Booking error:", error);
      setError("Unable to load your bookings.");

    } finally {

      setLoading(false);

    }
  };

  useEffect(() => {
    fetchBookings();
  }, []);

  if (loading) {
    return (
      <section className="my-bookings-page">
        <h1>My Bookings</h1>
        <p>Loading your bookings...</p>
      </section>
    );
  }

  return (
    <section className="my-bookings-page">

      <h1>My Bookings</h1>
      <p className="my-bookings-subtitle">
        View and manage your event bookings.
      </p>

      {error && (
        <p className="error-message">
          {error}
        </p>
      )}

      {!error && bookings.length === 0 && (
        <p>No bookings found.</p>
      )}

      <div className="bookings-list">

        {bookings.map((booking) => (

          <div className="booking-card" key={booking.id}>

            <h2>Booking #{booking.id}</h2>

            <p>
              <strong>Event ID:</strong> {booking.eventId}
            </p>

            <p>
              <strong>Tickets:</strong> {booking.numberOfTickets}
            </p>

            <p>
              <strong>Status:</strong>{" "}
              {booking.bookingStatus}
            </p>

            <button
              className="cancel-booking-btn"
              onClick={() => handleCancel(booking.id)}
              disabled={booking.bookingStatus === "CANCELLED"}
            >
              {booking.bookingStatus === "CANCELLED"
                ? "Cancelled"
                : "Cancel Booking"}
            </button>

            {booking.bookingDate && (
              <p>
                <strong>Booking Date:</strong>{" "}
                {booking.bookingDate}
              </p>
            )}

          </div>

        ))}

      </div>

    </section>
  );
}

export default MyBookings;