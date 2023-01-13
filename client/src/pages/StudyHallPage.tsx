import React from "react";
import { useParams } from "react-router-dom";

import Setting from "../components/StudyHallPage/setting/Setting";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/notification/StudyHallNotification";
import Community from "../components/StudyHallPage/community/Community";
import StudyHallMain from "../components/StudyHallPage/StudyHallMain";

const StudyHallPage = () => {
  const { page } = useParams();

  return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav />
      {page === "main" && <StudyHallMain />}
      {page === "community" && <Community />}
      {page === "calendar" && <div></div>}
      {page === "setting" && <Setting />}
    </div>
  );
};

export default StudyHallPage;
