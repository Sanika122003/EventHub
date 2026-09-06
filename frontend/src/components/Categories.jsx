function Categories() {
  return (
    <section className="categories-section">

      <div className="categories-header">
        <div>
          <h2>Explore Categories</h2>
          <p>Find events that match your interests.</p>
        </div>
      </div>

      <div className="categories-grid">

        <div className="category-card">
          <div className="category-icon">🎵</div>
          <h3>Music</h3>
          <p>Concerts & Live Shows</p>
        </div>

        <div className="category-card">
          <div className="category-icon">⚽</div>
          <h3>Sports</h3>
          <p>Games & Championships</p>
        </div>

        <div className="category-card">
          <div className="category-icon">🎨</div>
          <h3>Workshops</h3>
          <p>Learn & Create</p>
        </div>

        <div className="category-card">
          <div className="category-icon">😂</div>
          <h3>Comedy</h3>
          <p>Laugh & Enjoy</p>
        </div>

        <div className="category-card">
          <div className="category-icon">🎭</div>
          <h3>Theatre</h3>
          <p>Drama & Performances</p>
        </div>

      </div>

    </section>
  );
}

export default Categories;