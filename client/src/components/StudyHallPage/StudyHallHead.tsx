import styled from "styled-components";
import axios from "axios";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";
import { joinStudyStore } from "../../util/zustandJoinStudy";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

interface StudyHeader {
  teamName: string;
  openClose: boolean;
}
interface AuthData {
  host: boolean;
  request: boolean;
  member: boolean;
}
interface AuthDataObj {
  authData: AuthData | undefined;
}
const StudyHallHead = ({ authData }: AuthDataObj) => {
  const [studyData, setStudyData] = useState<StudyHeader>();
  const { studyId } = useParams();
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);

  const fetchJoinStudy = joinStudyStore((state) => state.fetchJoinStudy);
  useEffect(() => {
    axios.get(URL + `/study/${studyId}/header`).then((res) => {
      setStudyData(res.data.data);
    });
  }, []);

  const clickJoin = () => {
    fetchJoinStudy(
      URL,
      studyId,
      {},
      {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      }
    );
  };
  return (
    <HeaderWrapper>
      <div className="padding" />
      <TopDiv>
        <div className="studyName">
          {studyData && studyData.teamName}{" "}
          {authData?.member || !authData ? (
            <div></div>
          ) : authData?.request ? (
            <JoinButton
              request={authData?.request}
              disabled={authData?.request}
            >
              가입 대기
            </JoinButton>
          ) : (
            <JoinButton
              request={authData?.request}
              disabled={authData?.request}
              onClick={clickJoin}
            >
              가입 신청
            </JoinButton>
          )}
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
    props?.request ? `var(--mopo-10)` : `var(--mopo-00)`};

  color: var(--green);
  border: none;
  min-width: 40px;
  padding: 4px;
  height: 24px;
  :hover {
    transition-duration: 0.2s;
    background-color: var(--mopo-10);
  }
`;
