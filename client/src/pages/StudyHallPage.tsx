import React from "react";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/StudyHallNotification";

const StudyHallPage = () => {
  return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav />
      <StudyHallNotification />
    </div>
  );
};

export default StudyHallPage;
