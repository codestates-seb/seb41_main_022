import styled from "styled-components";
import { useNavigate, useParams } from "react-router-dom";
import { useCookies } from "react-cookie";
import { useEffect } from "react";
import axios from "axios";

import StudyHallRightNav from "./StudyHallRightNav";

const StudyHallTopNav = () => {
  const navigate = useNavigate();
  const { page, studyId } = useParams();
  const sId = Number(studyId);
  const [cookies, setCookie, removeCookie] = useCookies([
    "userData",
    "authData",
    "token",
  ]);
  const navigateStudyHall = (whichPage: string, studyId: number) => {
    navigate(`/study-hall/${whichPage}/${studyId}`);
  };

  useEffect(() => {
    axios
      .get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/user/${cookies.userData.userId}/auth`,
        {
          headers: {
            "access-Token": cookies.token.accessToken,
            "refresh-Token": cookies.token.refreshToken,
          },
        }
      )
      .then((res) => setCookie("authData", { data: res.data.data }));
    // .then(() => console.log("123",cookies.authData));
  }, []);

  return (
    <TopNavWrapper>
      <Nav>
        <div
          onClick={
            cookies.authData && cookies.authData.data.member
              ? () => {
                  navigateStudyHall("main", sId);
                }
              : () => {
                  alert("해당 스터디원만 이용가능한 기능입니다.");
                }
          }
          className={page === "main" ? `selected` : undefined}
        >
          Main
        </div>
        <div
          onClick={
            cookies.authData && cookies.authData.data.member
              ? () => {
                  navigateStudyHall("community", sId);
                }
              : () => {
                  alert("해당 스터디원만 이용가능한 기능입니다.");
                }
          }
          className={page === "community" ? `selected` : undefined}
        >
          Community
        </div>
        <div
          onClick={
            cookies.authData && cookies.authData.data.member
              ? () => {
                  navigateStudyHall("calendar", sId);
                }
              : () => {
                  alert("해당 스터디원만 이용가능한 기능입니다.");
                }
          }
          className={page === "calendar" ? `selected` : undefined}
        >
          Calendar
        </div>
        <div
          onClick={
            cookies.authData && cookies.authData.data.member
              ? () => {
                  navigateStudyHall("setting", sId);
                }
              : () => {
                  alert("해당 스터디원만 이용가능한 기능입니다.");
                }
          }
          className={page === "setting" ? `selected` : undefined}
        >
          Setting
        </div>
      </Nav>
      <StudyHallRightNav />
    </TopNavWrapper>
  );
};
export default StudyHallTopNav;
const TopNavWrapper = styled.main`
  height: 50px;
  background-color: var(--green);
  display: flex;
  justify-content: center;
  align-items: center;
  * {
    font-family: "mainB", Arial;
    font-size: 14px;
  }
`;
const Nav = styled.nav`
  background-color: var(--mopo-00);
  width: 460px;
  height: 28px;
  border-radius: var(--radius-30);
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  > div {
    width: 100px;
    height: 24px;
    margin: 2px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: var(--radius-30);
    color: var(--green);
    background-color: var(--mopo-00);
    :hover {
      color: var(--beige-00);
      background-color: var(--green);
      cursor: pointer;
    }
  }
  > .selected {
    color: var(--beige-00);
    background-color: var(--green);
  }
`;
