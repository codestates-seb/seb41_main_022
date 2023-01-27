import { useNavigate, useParams } from "react-router-dom";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import styled, { keyframes } from "styled-components";
import UserSkeleton2 from "./UserSkeleton2";
import UserStudyTree from "./UserStudyTree";
import WeekBar from "../../WeekBar";
import { VscBellDot } from "react-icons/vsc";
import Ticker from "react-ticker";
import StudyHallCommunityMember from "../../StudyHallPage/community/StudyHallCommunityMember";

interface studyType {
  studyId: number;
  teamName: string;
  summary: string;
  dayOfWeek: string[];
  want: number;
  startDate: string;
  procedure: boolean;
  openClose: boolean;
  content: string;
  notice: null;
  image: string;
  leaderId: number;
  requester: [];
}
interface treeType {
  createdAt: string;
  makeMonth: number;
  treeId: number;
  treeImage: string;
  treePoint: number;
}
interface authType {
  member: boolean;
  host: boolean;
  request: boolean;
}
interface User {
  userId: number;
  username: string;
  imgUrl: string;
  data: any;
}
interface Member {
  username: string;
  imgUrl: string;
  role: string;
}

const UserStudy = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [myStudy, setMyStudy] = useState<studyType | undefined>();
  const [treeData, setTreeData] = useState<treeType[] | undefined>();
  const [myUserData, setMyUserData] = useState<User | undefined>();
  const [memberData, setMemberData] = useState<Member[] | undefined>();
  const [scroll, setScroll] = useState(0);
  const { studyId } = useParams();

  useEffect(() => {
    setTimeout(() => {
      axios
        .all([
          axios.get(process.env.REACT_APP_API_URL + `/study/${studyId}`, {
            headers: {
              "access-Token": cookies.token.accessToken,
              "refresh-Token": cookies.token.refreshToken,
            },
          }),
          axios.get(
            process.env.REACT_APP_API_URL + `/tree?studyId=${studyId}`,
            {
              headers: {
                "access-Token": cookies.token.accessToken,
                "refresh-Token": cookies.token.refreshToken,
              },
            }
          ),
          axios.get(process.env.REACT_APP_API_URL + `/user`, {
            headers: {
              "access-Token": cookies.token.accessToken,
              "refresh-Token": cookies.token.refreshToken,
            },
          }),
          axios.get(
            process.env.REACT_APP_API_URL + `/user/study?studyId=${studyId}`,
            {
              headers: {
                "access-Token": cookies.token.accessToken,
                "refresh-Token": cookies.token.refreshToken,
              },
            }
          ),
        ])
        .then(
          axios.spread((res1, res2, res3, res4) => {
            setMyStudy(res1.data.data);
            setTreeData(res2.data.data.trees);
            setMyUserData(res3.data.data);
            setMemberData(res4.data.data);
          })
        );
    }, 500);
  }, []);
  const setData = () => {
    const data = memberData?.find((el) => el.username === myUserData?.username);
    return data?.role.includes("ADMIN");
  };
  const handleScroll = () => {
    setScroll(window.scrollY);
  };
  useEffect(() => {
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll); //clean up
    };
  }, []);
  const observer = new IntersectionObserver((e) => {
    e.forEach((el: any) => {
      if (scroll > 120) {
        el.target.style.opacity = 0.2;
        el.target.style.marginTop = "-400px";
      } else if (scroll > 60) {
        el.target.style.opacity = 0.5;
        el.target.style.marginTop = "-200px";
      } else {
        el.target.style.opacity = 0.8;
        el.target.style.marginTop = 0;
      }
    });
  });
  const backImg = document.querySelector("#backImg");

  if (backImg) {
    observer.observe(backImg);
  }
  const navigate = useNavigate();
  return (
    <Main>
      <BeigeDiv>
        <BackGround>
          <img src={myStudy?.image} id="backImg" />
        </BackGround>
        <Container>
          {myUserData && (
            <UserInfo>
              <img src={myUserData.imgUrl} />
              <div className="info">
                <div>{myUserData.username}</div>
                <div className="auth">{setData() ? "HOST" : "MEMBER"}</div>
              </div>
            </UserInfo>
          )}
          {!myUserData && <UserSkeleton2 />}
          <UserStudyDetail>
            <h2
              className="teamName"
              onClick={() => navigate("/study-hall/main/" + studyId)}
            >
              {myStudy?.teamName}
            </h2>
            <UserStudyTree treeData={treeData} />
            <div className="divide">
              <div>
                Study Introduction
                <StudyIntroduction>{myStudy?.summary}</StudyIntroduction>
              </div>
              <div className="marginTop41">
                <p>Notice</p>
                <NoticeWrapper>
                  <div className="wrapper">
                    <div>
                      <VscBellDot />
                    </div>
                    {myStudy && (
                      <Ticker>
                        {() => (
                          <div className="noticeFont">
                            {myStudy?.notice ? myStudy?.notice : ""}
                          </div>
                        )}
                      </Ticker>
                    )}
                  </div>
                </NoticeWrapper>
                <p>Week</p>
                {myStudy && <WeekBar dayOfWeek={myStudy.dayOfWeek} />}
                <p>Member</p>
                {memberData &&
                  memberData.map((el, idx) => (
                    <StudyHallCommunityMember
                      key={idx}
                      username={el.username}
                      imgUrl={el.imgUrl}
                      role={el.role}
                    />
                  ))}
              </div>
            </div>
          </UserStudyDetail>
        </Container>
      </BeigeDiv>
    </Main>
  );
};
export default UserStudy;
const Main = styled.div`
  background-color: var(--green);
  height: auto;
  * {
    font-family: "mainM";
    color: var(--green);
  }
`;
//전체박스
const BeigeDiv = styled.div`
  width: 1024px;
  margin: 0px auto;
  height: auto;
  background-color: var(--beige-00);
  padding-bottom: 30px;
`;
const Container = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  margin-left: 11%;
  ul,
  li {
    margin: 0;
    padding: 0;
  }
`;
const BackGround = styled.div`
  position: absolute;
  background-color: white;
  margin: 0px auto;
  width: 1024px;
  height: 200px;
  overflow: hidden;
  img {
    width: 100%;
    opacity: 0.8;
    transition: all 0.5s;
    margin-top: 0px;
  }
`;

const UserInfo = styled.div`
  margin-top: 160px;
  margin-bottom: 60px;
  display: flex;
  flex-direction: row;
  align-items: center;
  > img {
    width: 85px;
    height: 85px;
    background-color: #b0ac93;
    border-radius: var(--radius-30);
    align-items: center;
  }
  .info {
    font-size: 16px;
    font-family: "mainM";
    margin-left: 10px;
    display: flex;
    flex-direction: column;
    > .auth {
      font-size: 12px;
    }
  }
`;
const UserStudyDetail = styled.div`
  width: 600px;
  margin: 0 auto;
  * {
    font-size: 18px;
    font-family: "mainEB";
  }
  .teamName {
    width: fit-content;
    font-size: 24px;
    cursor: pointer;
    :hover {
      color: var(--green-00);
    }
  }
  .divide {
    display: flex;
    flex-direction: row;
    margin-top: 50px;
    > div {
      width: 300px;
      display: flex;
      flex-direction: column;
    }
  }
  .marginTop41 {
    margin: 10px 0;
    display: flex;
    align-items: center;
    p {
      margin: 5px 0;
    }
  }
`;
const StudyIntroduction = styled.div`
  background-color: var(--green);
  margin: 20px auto;
  width: 90%;
  padding: 10px;
  color: var(--beige-00);
  border-radius: var(--radius-10);
`;
const NoticeWrapper = styled.main`
  margin-top: 5px;
  width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  * {
    color: var(--beige-00);
  }
  .wrapper {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    padding: 0 2px;

    .ticker {
      width: 150px;
      max-height: 16px;

      .ticker__element {
        width: 100%;
        display: flex;
        justify-content: center;
      }
      .noticeFont {
        font-family: "mainM", Arial;
        font-size: 10px;
      }
    }
  }
  div {
    display: flex;
    align-items: center;
    padding: 3px;
  }
`;
