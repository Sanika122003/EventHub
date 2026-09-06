import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

function Register() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: ""
  });

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);


  const handleChange = (e) => {

    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });

  };


  const handleRegister = async (e) => {

    e.preventDefault();

    setMessage("");
    setError("");
    setLoading(true);

    try {

      const response = await fetch(
        "http://localhost:8080/api/users/register",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json"
          },

          body: JSON.stringify(formData)
        }
      );


      const data = await response.text();


      if (response.ok) {

        setMessage(data);

        setFormData({
          fullName: "",
          email: "",
          password: ""
        });

        setTimeout(() => {
          navigate("/login");
        }, 1500);

      } else {

        setError(data || "Registration failed.");

      }

    } catch (error) {

      setError(
        "Unable to connect to the server. Please try again."
      );

    } finally {

      setLoading(false);

    }

  };


  return (
    <section className="auth-page">

      <div className="auth-card">

        <div className="auth-header">

          <h1>Create Account ✨</h1>

          <p>
            Join EventHub and start exploring events
          </p>

        </div>


        <form onSubmit={handleRegister}>

          <div className="form-group">

            <label>Full Name</label>

            <input
              type="text"
              name="fullName"
              placeholder="Enter your full name"
              value={formData.fullName}
              onChange={handleChange}
              required
            />

          </div>


          <div className="form-group">

            <label>Email</label>

            <input
              type="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              required
            />

          </div>


          <div className="form-group">

            <label>Password</label>

            <input
              type="password"
              name="password"
              placeholder="Create a password"
              value={formData.password}
              onChange={handleChange}
              required
              minLength="8"
              maxLength="20"
            />

          </div>


          {message && (
            <p className="success-message">
              {message}
            </p>
          )}


          {error && (
            <p className="error-message">
              {error}
            </p>
          )}


          <button
            type="submit"
            className="auth-btn"
            disabled={loading}
          >

            {loading ? "Creating Account..." : "Register"}

          </button>

        </form>


        <p className="auth-switch">

          Already have an account?{" "}

          <Link to="/login">
            Login
          </Link>

        </p>

      </div>

    </section>
  );
}


export default Register;