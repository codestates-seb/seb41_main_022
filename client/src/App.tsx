import React from "react";
import "./global.css";
import "./reset.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import HomePage from "./pages/HomePage";
import CreatePage from "./pages/CreatePage";
import StudyHallPage from "./pages/StudyHallPage";
import UserPage from "./pages/UserPage";
import Header from "./components/Header";

function App() {
  return (
    <>
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/create" element={<CreatePage />} />
          <Route path="/study-hall" element={<StudyHallPage />} />
          <Route path="/user" element={<UserPage />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
