import React from "react";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/StudyHallNotification";
import StudyHallMain from "../components/StudyHallPage/StudyHallMain";

const StudyHallPage = () => {
  return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav />
      <StudyHallMain />
    </div>
  );
};

export default StudyHallPage;
