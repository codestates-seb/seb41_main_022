import React from "react";
import { useParams } from "react-router-dom";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/StudyHallNotification";
import Community from "../components/StudyHallPage/community/Community";
import StudyHallMain from "../components/StudyHallPage/StudyHallMain";

const StudyHallPage = () => {
  const { page } = useParams();
  console.log(page);
  return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav />
      {page === "main" && <StudyHallMain />}
      {page === "community" && <Community />}
      {page === "calendar" && <div></div>}
      {page === "setting" && <div></div>}
    </div>
  );
};

export default StudyHallPage;
