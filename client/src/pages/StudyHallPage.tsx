import React, {useEffect, useState} from "react";
import { useParams } from "react-router-dom";

import Setting from "../components/StudyHallPage/setting/Setting";
import StudyHallHead from "../components/StudyHallPage/StudyHallHead";
import StudyHallTopNav from "../components/StudyHallPage/StudyHallTopNav";
import StudyHallNotification from "../components/StudyHallPage/notification/StudyHallNotification";
import Community from "../components/StudyHallPage/community/Community";
import StudyHallMain from "../components/StudyHallPage/Main/StudyHallMain";
import {useCookies} from "react-cookie";
import axios from "axios";

interface AuthData {
    host: boolean;
    request: boolean;
    member: boolean;
}

const StudyHallPage = () => {
  const { page,studyId } = useParams();
    const [cookies, setCookie, removeCookie] = useCookies(["userData", "token"]);
    const [authData, setAuthData] = useState<AuthData | undefined>();
    const checkAuth = async () => {
        const res = await axios.get(
            `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/user/${cookies.userData.userId}/auth`,
            {
                headers: {
                    "access-Token": cookies.token.accessToken,
                    "refresh-Token": cookies.token.refreshToken,
                },
            }
        );
        setAuthData(res.data.data);
    };

    useEffect(() => {
        checkAuth();
    }, []);

    return (
    <div>
      <StudyHallHead />
      <StudyHallTopNav authData={authData}/>
      {page === "main" && <StudyHallMain />}
      {page === "community" && <Community />}
      {page === "calendar" && <StudyHallNotification />}
      {page === "setting" && <Setting />}
    </div>
  );
};

export default StudyHallPage;
