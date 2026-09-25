import { useEffect, useState } from "react";
import { Link, useSearchParams } from "react-router-dom";

import comedyImage from "../assets/events/comedy.jpg";
import technologyImage from "../assets/events/technology.jpg";
import foodImage from "../assets/events/food.jpg";
import sportImage from "../assets/events/sport.jpg";


function Events() {
const [searchParams] = useSearchParams();
const selectedCategory = searchParams.get("category");

  const [events, setEvents] = useState([]);
  const [showAll, setShowAll] = useState(false);
  const [searchTerm, setSearchTerm] = useState("");
  const [categoryFilter, setCategoryFilter] = useState(
    selectedCategory || ""
  );

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");


  useEffect(() => {

    const fetchEvents = async () => {

      try {

        const response = await fetch(
          "http://localhost:8080/api/events"
        );

        if (!response.ok) {
          throw new Error("Failed to fetch events.");
        }

        const data = await response.json();

        setEvents(data);

      } catch (error) {

        console.error("Error fetching events:", error);

        setError(
          "Unable to load events. Please try again later."
        );

      } finally {

        setLoading(false);

      }

    };

    fetchEvents();

  }, []);

  const getEventImage = (event) => {

    const category = event.category?.toString().toLowerCase();

    if (category === "music") {
      return null;
    }

    if (category === "workshop") {
      return null;
    }

    if (category === "sports") {
      return sportImage;
    }

    if (category === "comedy") {
      return comedyImage;
    }

    if (category === "technology" || category === "tech") {
      return technologyImage;
    }

    if (category === "food") {
      return foodImage;
    }

    return null;
  };

  const getEventImageClass = (event) => {

    const category = event.category?.toString().toLowerCase();

    if (category === "music") {
      return "concert-image";
    }

    if (category === "workshop") {
      return "workshop-image";
    }

    if (category === "sports") {
      return "sports-image";
    }

    if (category === "comedy") {
      return "comedy-image";
    }

    if (category === "technology" || category === "tech") {
      return "tech-image";
    }

    if (category === "food") {
      return "food-image";
    }

    return "";
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
      <section className="events-section">

        <div className="events-header">

          <div>

            <h2>Popular Events</h2>

            <p>
              Discover events people are loving right now.
            </p>

          </div>

        </div>

        <p>Loading events...</p>

      </section>
    );

  }


  // Error

  if (error) {

    return (
      <section className="events-section">

        <div className="events-header">

          <div>

            <h2>Popular Events</h2>

            <p>
              Discover events people are loving right now.
            </p>

          </div>

        </div>

        <p className="error-message">
          {error}
        </p>

      </section>
    );

  }


  // First 3 events initially, all when View All is clicked

  const filteredEvents = events.filter((event) => {
    const matchesCategory = categoryFilter
      ? event.category?.toString().toUpperCase() ===
        categoryFilter.toUpperCase()
      : true;

    const searchText = searchTerm.toLowerCase();

    const matchesSearch =
      event.title?.toLowerCase().includes(searchText) ||
      event.description?.toLowerCase().includes(searchText) ||
      event.location?.toLowerCase().includes(searchText);

    return matchesCategory && matchesSearch;
  });

  const visibleEvents = showAll
    ? filteredEvents
    : filteredEvents.slice(0, 3);


  return (
    <section className="events-section">

      <div className="events-header">

        <div>

          <h2>Popular Events</h2>

          <p>
            Discover events people are loving right now.
          </p>

        </div>

        <div className="event-search">
          <input
            type="text"
            placeholder="Search events..."
            value={searchTerm}
            onChange={(e) => {
              setSearchTerm(e.target.value);
              setShowAll(false);
            }}
          />

          <select
            value={categoryFilter}
            onChange={(e) => {
              setCategoryFilter(e.target.value);
              setShowAll(false);
            }}
          >
            <option value="">All Categories</option>
            <option value="MUSIC">Music</option>
            <option value="SPORTS">Sports</option>
            <option value="TECH">Tech</option>
            <option value="EDUCATION">Education</option>
            <option value="BUSINESS">Business</option>
            <option value="WORKSHOP">Workshop</option>
            <option value="CULTURAL">Cultural</option>
            <option value="OTHER">Other</option>
          </select>

          <button
            className="clear-filter-btn"
            onClick={() => {
              setSearchTerm("");
              setCategoryFilter("");
              setShowAll(false);
            }}
          >
            Clear Filters
          </button>
        </div>


        {events.length > 3 && (

          <button
            className="view-all-btn"
            onClick={() => setShowAll(!showAll)}
          >
            {showAll ? "Show Less" : "View All"}
          </button>

        )}

      </div>


      <div className="events-grid">

        {visibleEvents.map((event) => {

          const localImage = getEventImage(event);

          const imageClass = getEventImageClass(event);


          return (

            <Link
              key={event.id}
              to={`/events/${event.id}`}
              className="event-card-link"
            >

              <div className="event-card">


                {/* Event Image */}

                <div
                  className={`event-image ${imageClass}`}

                  style={
                    localImage
                      ? {
                          backgroundImage: `url(${localImage})`
                        }
                      : {}
                  }
                >

                  <span className="event-category">
                    {event.category}
                  </span>

                </div>


                {/* Event Content */}

                <div className="event-content">

                  <h3>
                    {event.title}
                  </h3>


                  <p className="event-location">
                    📍 {event.location}
                  </p>


                  <p className="event-date">
                    📅 {formatDate(event.date)}
                  </p>


                  <p className="event-time">
                    🕒 {formatTime(event.time)}
                  </p>


                  <div className="event-bottom">

                    <span className="event-price">
                      ₹{event.price}
                    </span>

                    <button>
                      Book Now
                    </button>

                  </div>

                </div>

              </div>

            </Link>

          );

        })}

      </div>

    </section>
  );

}

export default Events;