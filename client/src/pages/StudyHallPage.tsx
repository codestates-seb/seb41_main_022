import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

import Setting from "../components/StudyHallPage/setting/Setting";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/notification/StudyHallNotification";
import Community from "../components/StudyHallPage/community/Community";
import StudyHallMain from "../components/StudyHallPage/Main/StudyHallMain";
import { useCookies } from "react-cookie";
import AuthStore from "../util/zustandAuth";

interface AuthData {
  host: boolean;
  request: boolean;
  member: boolean;
}

const StudyHallPage = () => {
  const { page, studyId } = useParams();
  const [cookies, setCookie, removeCookie] = useCookies(["userData", "token"]);
  const { authData, checkAuth } = AuthStore();
  const [callTimes, setCallTimes] = useState(0);

  useEffect(() => {
    if (callTimes === 0) {
      if (studyId && !authData?.request) {
        setTimeout(() => {
          checkAuth(
            studyId,
            cookies.userData.userId,
            cookies.token.accessToken,
            cookies.token.refreshToken
          );
        }, 500);
        setCallTimes(callTimes + 1);
      }
    }
  }, [authData]);

  return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav />
      {page === "main" && <StudyHallMain />}
      {page === "community" && <Community />}
      {page === "calendar" && <StudyHallNotification />}
      {page === "setting" && <Setting />}
    </div>
  );
};

export default StudyHallPage;
