import React from "react";
import "./global.css";
import "./reset.css";
import { Routes, Route } from "react-router-dom";

import HomePage from "./pages/HomePage";
import CreatePage from "./pages/CreatePage";
import EditPage from "./pages/EditPage";
import StudyHallPage from "./pages/StudyHallPage";
import UserPage from "./pages/UserPage";
import Header from "./components/Header";
import Footer from "./components/Footer";
import NotFound from "./pages/NotFound";
import TokenPage from "./pages/TokenPage";
import styled from "styled-components";
import UserStudy from "./components/userpage/UsreStudyDetail/UserStudy";

function App() {
  return (
    <>
      <Header />
      <MainWrapper>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/*" element={<NotFound />} />
          <Route path="/create" element={<CreatePage />} />
          <Route path="/edit/:studyId" element={<EditPage />} />
          <Route
            path="/study-hall/:page/:studyId"
            element={<StudyHallPage />}
          />
          <Route path="/user" element={<UserPage />} />
          <Route path="/user/:studyId" element={<UserStudy />} />
          <Route path="/Token.html" element={<TokenPage />} />
        </Routes>
      </MainWrapper>
      <Footer />
    </>
  );
}

export default App;

const MainWrapper = styled.article`
  /* margin-top: 64px; */
  // 크롬, 사파리, 파이어폭스
  .scroll::-webkit-scrollbar {
    display: none;
  }
  /* .scroll {
    overflow: hidden;
  } */
`;
