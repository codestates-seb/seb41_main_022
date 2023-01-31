import styled from "styled-components";
import axios from "axios";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { joinStudyStore } from "../../util/zustandJoinStudy";
import AuthStore from "../../util/zustandAuth";

interface StudyHeader {
  teamName: string;
  openClose: boolean;
}
interface AuthData {
  host: boolean;
  member: boolean;
  request: boolean;
}

const StudyHallHead = () => {
  const [studyData, setStudyData] = useState<StudyHeader>();
  const { studyId } = useParams();
  const [cookies, setCookie, removeCookie] = useCookies(["token", "userData"]);
  const { authData, checkAuth } = AuthStore();
  const [buttonAuthData, setButtonAuthData] = useState<AuthData | undefined>(
    undefined
  );
  const notify = () => toast.success("가입 신청 되었습니다.");

  const fetchJoinStudy = joinStudyStore((state) => state.fetchJoinStudy);
  useEffect(() => {
    axios
      .get(process.env.REACT_APP_API_URL + `/study/${studyId}/header`)
      .then((res) => {
        setStudyData(res.data.data);
      });
    studyId &&
      axios
        .get(
          process.env.REACT_APP_API_URL +
            `/study/${studyId}/user/${cookies.userData.userId}/auth`,
          {
            headers: {
              "access-Token": cookies.token.accessToken,
              "refresh-Token": cookies.token.refreshToken,
            },
          }
        )
        .then((res) => setButtonAuthData(res.data.data));
  }, []);

  const clickJoin = () => {
    fetchJoinStudy(
      studyId,
      {},
      {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      }
    );

    notify();

    studyId &&
      checkAuth(
        studyId,
        cookies.userData.userId,
        cookies.token.accessToken,
        cookies.token.refreshToken
      );

    setButtonAuthData(authData);
  };
  return (
    <HeaderWrapper>
      <div className="padding" />
      <TopDiv>
        <div className="studyName">
          {studyData &&
            (studyData.teamName.length > 10 ? (
              <span className="studyNameLong">{studyData.teamName}</span>
            ) : (
              studyData.teamName
            ))}{" "}
          {buttonAuthData &&
            (buttonAuthData.member ? (
              <div></div>
            ) : buttonAuthData.request ? (
              <JoinButton
                request={buttonAuthData?.request}
                disabled={buttonAuthData?.request}
              >
                가입 대기
              </JoinButton>
            ) : (
              <JoinButton
                request={buttonAuthData.request}
                disabled={buttonAuthData.request}
                onClick={clickJoin}
              >
                가입 신청
              </JoinButton>
            ))}
        </div>
        <div className="studyHall">study Hall</div>
      </TopDiv>
      <FlexDiv>
        <span
          className={studyData && studyData.openClose ? "active" : undefined}
        >
          Public
        </span>
        /
        <span
          className={studyData && studyData.openClose ? undefined : "active"}
        >
          Private
        </span>
      </FlexDiv>
    </HeaderWrapper>
  );
};
export default StudyHallHead;

const HeaderWrapper = styled.header`
  height: 190px;
  background-color: var(--beige-00);
  * {
    font-family: "mainB", Arial;
  }
  .padding {
    padding-top: 100px;
  }
  > div {
    width: 460px;
    margin: 0 auto;
  }
`;
const TopDiv = styled.div`
  width: 460px;
  margin: 0 auto;
  padding-bottom: 4px;
  border-bottom: 2px solid var(--green);
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  color: var(--green);
  .studyName {
    font-size: 40px;
    display: flex;
    align-items: center;
  }
  .studyHall {
    font-size: 14px;
    display: flex;
    flex-direction: column-reverse;
  }
  .studyNameLong {
    font-size: 28px;
    margin-top: 12px;
  }
`;
//Public Private 관리
const FlexDiv = styled.div`
  padding: 2px;
  display: flex;
  justify-content: end;
  font-size: 12px;
  > span {
    color: var(--mopo-00);
  }
  > .active {
    color: var(--green);
  }
`;
const JoinButton = styled.button<{ request?: boolean }>`
  margin-left: 10px;
  border-radius: var(--radius-30);
  background-color: ${(props) =>
    props?.request ? `var(--mopo-10)` : `var(--logo)`};

  color: var(--green);
  font-size: 12px;
  border: solid 3px var(--green);
  min-width: 40px;
  padding: 1px, 4px, 6px, 4px;
  height: 24px;
  :hover {
    transition-duration: 0.2s;
    background-color: var(--mopo-10);
  }
`;
