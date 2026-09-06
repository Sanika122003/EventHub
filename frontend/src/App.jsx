import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";
import Hero from "./components/Hero";
import Events from "./components/Events";
import Categories from "./components/Categories";
import Footer from "./components/Footer";

import EventDetails from "./pages/EventDetails";
import Login from "./pages/Login";
import Register from "./pages/Register";


function Home() {
  return (
    <>
      <Hero />
      <Events />
      <Categories />
      <Footer />
    </>
  );
}


function App() {
  return (
    <BrowserRouter>

      <Navbar />

      <Routes>

        <Route path="/" element={<Home />} />

        <Route
          path="/events/:id"
          element={<EventDetails />}
        />

        <Route
          path="/login"
          element={<Login />}
        />
       <Route
         path="/register"
         element={<Register />}
       />
      </Routes>

    </BrowserRouter>
  );
}

export default App;