import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

function Login() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
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


  const handleLogin = async (e) => {

    e.preventDefault();

    setMessage("");
    setError("");
    setLoading(true);

    try {

      const response = await fetch(
        "http://localhost:8080/api/users/login",
        {
          method: "POST",

          headers: {
            "Content-Type": "application/json"
          },

          body: JSON.stringify(formData)
        }
      );


      const data = await response.json();


      if (response.ok) {

        // Store JWT token in browser
        localStorage.setItem("token", data.token);

        setMessage(data.message);

        setFormData({
          email: "",
          password: ""
        });

        setTimeout(() => {
          navigate("/");
        }, 1000);

      } else {

        setError(
          data.message || "Invalid email or password."
        );

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

          <h1>Welcome Back 👋</h1>

          <p>
            Login to continue to EventHub
          </p>

        </div>


        <form onSubmit={handleLogin}>

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
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleChange}
              required
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

            {loading ? "Logging in..." : "Login"}

          </button>

        </form>


        <p className="auth-switch">

          Don't have an account?{" "}

          <Link to="/register">
            Register
          </Link>

        </p>

      </div>

    </section>
  );
}

export default Login;