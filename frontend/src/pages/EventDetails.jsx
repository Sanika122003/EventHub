import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import comedyImage from "../assets/events/comedy.jpg";
import technologyImage from "../assets/events/technology.jpg";
import foodImage from "../assets/events/food.jpg";
import sportImage from "../assets/events/sport.jpg";


function EventDetails() {

  const { id } = useParams();

  const [event, setEvent] = useState(null);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [numberOfTickets, setNumberOfTickets] = useState(1);
  const [bookingMessage, setBookingMessage] = useState("");
  const [bookingError, setBookingError] = useState("");
  const [bookingLoading, setBookingLoading] = useState(false);

  const handleBooking = async () => {

    const token = localStorage.getItem("token");
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

    if (!token) {
      setBookingError("Please login to book an event.");
      return;
    }

    setBookingMessage("");
    setBookingError("");
    setBookingLoading(true);

    try {

      const response = await fetch(
        "http://localhost:8080/api/bookings",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
          },

          body: JSON.stringify({
            userId: profile.id,
            eventId: event.id,
            numberOfTickets: numberOfTickets
          })
        }
      );

      const data = await response.text();

      if (response.ok) {

        setBookingMessage(data);
        setNumberOfTickets(1);

      } else {

        setBookingError(
          data || "Booking failed. Please try again."
        );

      }

    } catch (error) {

      console.error("Booking error:", error);

      setBookingError(
        "Unable to connect to the server."
      );

    } finally {

      setBookingLoading(false);

    }

  };


  useEffect(() => {

    const fetchEvent = async () => {

      try {

        const response = await fetch(
          `http://localhost:8080/api/events/${id}`
        );

        if (!response.ok) {
          throw new Error("Event not found.");
        }

        const data = await response.json();

        setEvent(data);

      } catch (error) {

        console.error("Error fetching event:", error);

        setError(
          "Unable to load event details."
        );

      } finally {

        setLoading(false);

      }

    };

    fetchEvent();

  }, [id]);


  const getEventImage = (event) => {

    if (!event) {
      return null;
    }
    const handleBooking = async () => {

      const token = localStorage.getItem("token");

      if (!token) {
        setBookingError("Please login to book an event.");
        return;
      }

      setBookingMessage("");
      setBookingError("");
      setBookingLoading(true);

      try {

        const response = await fetch(
          "http://localhost:8080/api/bookings",
          {
            method: "POST",

            headers: {
              "Content-Type": "application/json",
              "Authorization": `Bearer ${token}`
            },

            body: JSON.stringify({
              userId: 1,
              eventId: event.id,
              numberOfTickets: numberOfTickets
            })
          }
        );

        const data = await response.text();

        if (response.ok) {

          setBookingMessage(data);

          setNumberOfTickets(1);

        } else {

          setBookingError(
            data || "Booking failed. Please try again."
          );

        }

      } catch (error) {

        console.error("Booking error:", error);

        setBookingError(
          "Unable to connect to the server."
        );

      } finally {

        setBookingLoading(false);

      }
    };

    const category = event.category?.toString().toLowerCase();


    // Music
    if (category === "music") {
      return "https://images.unsplash.com/photo-1501386761578-eac5c94b800a";
    }


    // Technology / Tech
    if (category === "technology" || category === "tech") {
      return technologyImage;
    }


    // Workshop
    if (category === "workshop") {
      return "https://images.unsplash.com/photo-1540575467063-178a50c2df87";
    }


    // Sports
    if (category === "sports") {
      return sportImage;
    }


    // Comedy
    if (category === "comedy") {
      return comedyImage;
    }


    // Food
    if (category === "food") {
      return foodImage;
    }


    return null;
  };


  // Format date
  const formatDate = (date) => {

    if (!date) return "";

    const dateObject = new Date(date);

    return dateObject.toLocaleDateString("en-GB", {
      day: "numeric",
      month: "long",
      year: "numeric"
    });

  };


  // Format time
  const formatTime = (time) => {

    if (!time) return "";

    const [hours, minutes] = time.split(":");

    const dateObject = new Date();

    dateObject.setHours(hours);
    dateObject.setMinutes(minutes);

    return dateObject.toLocaleTimeString("en-US", {
      hour: "numeric",
      minute: "2-digit"
    });

  };


  // Loading
  if (loading) {

    return (
      <section className="event-details-page">

        <div className="event-details-container">

          <h1>Loading Event...</h1>

        </div>

      </section>
    );

  }


  // Error / Event not found
  if (error || !event) {

    return (
      <section className="event-details-page">

        <div className="event-details-container">

          <h1>Event Not Found</h1>

          <p>
            The event you are looking for does not exist.
          </p>

        </div>

      </section>
    );

  }

  const eventImage = getEventImage(event);

  return (
    <section className="event-details-page">

      <div className="event-details-container">


        {/* Event Image */}

        <div className="event-details-image">

          {eventImage ? (

            <img
              src={eventImage}
              alt={event.title}
              className="event-details-real-image"
            />

          ) : (

            <div className="event-details-image-placeholder">
              No Image Available
            </div>

          )}

        </div>


        {/* Event Details */}

        <div className="event-details-content">


          <span className="event-details-category">
            {event.category}
          </span>


          <h1>
            {event.title}
          </h1>


          <p className="event-details-description">
            {event.description}
          </p>


          <div className="event-info">


            {/* Location */}

            <div className="event-info-item">

              <span className="event-info-icon">
                📍
              </span>

              <div>

                <strong>Location</strong>

                <p>
                  {event.location}
                </p>

              </div>

            </div>


            {/* Date */}

            <div className="event-info-item">

              <span className="event-info-icon">
                📅
              </span>

              <div>

                <strong>Date</strong>

                <p>
                  {formatDate(event.date)}
                </p>

              </div>

            </div>


            {/* Time */}

            <div className="event-info-item">

              <span className="event-info-icon">
                ⏰
              </span>

              <div>

                <strong>Time</strong>

                <p>
                  {formatTime(event.time)}
                </p>

              </div>

            </div>


            {/* Available Seats */}

            <div className="event-info-item">

              <span className="event-info-icon">
                🎟️
              </span>

              <div>

                <strong>Available Seats</strong>

                <p>
                  {event.availableSeats}
                </p>

              </div>

            </div>


          </div>


          {/* Bottom */}
          {bookingMessage && (
            <p className="success-message">
              {bookingMessage}
            </p>
          )}

          {bookingError && (
            <p className="error-message">
              {bookingError}
            </p>
          )}

          <div className="event-details-bottom">

            <div className="event-details-price-container">

              <span className="event-details-price">
                ₹{event.price}
              </span>

              <p>
                per person
              </p>

            </div>


            <div className="ticket-selector">

              <button
                type="button"
                onClick={() =>
                  setNumberOfTickets(
                    Math.max(1, numberOfTickets - 1)
                  )
                }
              >
                −
              </button>

              <span>
                {numberOfTickets}
              </span>

              <button
                type="button"
                onClick={() =>
                  setNumberOfTickets(
                    Math.min(
                      event.availableSeats,
                      numberOfTickets + 1
                    )
                  )
                }
              >
                +
              </button>

            </div>


            <div className="booking-total">

              <span>Total</span>

              <strong>
                ₹{event.price * numberOfTickets}
              </strong>

            </div>


            <button
              className="book-event-btn"
              onClick={handleBooking}
              disabled={bookingLoading}
            >
              {bookingLoading ? "Booking..." : "Book Now"}
            </button>


          </div>


        </div>

      </div>

    </section>
  );

}

export default EventDetails;