import React from "react";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallMain from "../components/StudyHallPage/StudyHallMain";
import StudyHallRightNav from "../components/StudyHallPage/StudyHallRightNav";

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
