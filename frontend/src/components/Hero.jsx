function Hero() {
  return (
    <section className="hero">

      <div className="hero-content">

        <h1>
          Discover. Book. Experience.
        </h1>

        <p>
          Find the best events happening around you.
        </p>

        <div className="search-box">

          <input
            type="text"
            placeholder="🔍 Search for events..."
          />

          <button>
            Search
          </button>

        </div>

      </div>

    </section>
  );
}

export default Hero;