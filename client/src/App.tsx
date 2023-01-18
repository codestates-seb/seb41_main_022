import React from "react";
import "./global.css";
import "./reset.css";
import { Routes, Route } from "react-router-dom";

import HomePage from "./pages/HomePage";
import CreatePage from "./pages/CreatePage";
import StudyHallPage from "./pages/StudyHallPage";
import UserPage from "./pages/UserPage";
import Header from "./components/Header";
import Footer from "./components/Footer";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/*" element={<NotFound />} />
        <Route path="/create" element={<CreatePage />} />
        <Route path="/study-hall/:page/:studyId" element={<StudyHallPage />} />
        <Route path="/user" element={<UserPage />} />
      </Routes>
      <Footer />
    </>
  );
}

export default App;
