import React from "react";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/StudyHallNotification";
import Community from "../components/StudyHallPage/community/Community";

const StudyHallPage = () => {
  return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav />
      <Community />
    </div>
  );
};

export default StudyHallPage;
