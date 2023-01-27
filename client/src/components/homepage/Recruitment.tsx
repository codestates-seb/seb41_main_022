import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import LoginStore from "../../util/zustandLogin";
import { toast, ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import WeekBar from "../WeekBar";

interface CardProps {
  teamName: string;
  summary: string;
  dayOfWeek: string[];
  studyId: number;
  procedure: boolean;
  imgUrl: string;
}

const Recruitment = ({
  teamName,
  summary,
  dayOfWeek,
  studyId,
  procedure,
  imgUrl,
}: CardProps) => {
  const navigate = useNavigate();
  const { isLogin } = LoginStore();
  const error = () => toast.error("로그인 후 이용해주세요!");

  return (
    <RecruitmentBackground
      style={{ backgroundImage: `url(${imgUrl})` }}
      onClick={
        isLogin ? () => navigate(`/study-hall/main/${studyId}`) : () => error()
      }
    >
      <ToastContainer position="top-right" autoClose={1500} />
      <div className="recruitmentBody">
        <div className="border">
          <BodyWrapper>
            <div className="body-myStudy">{teamName}</div>
            <div className="body-content--wrapper">
              <div className="body-content">{summary}</div>
            </div>
            <WeekBar dayOfWeek={dayOfWeek} />
            <div className="body-online">
              <span className={procedure ? "active" : undefined}>Online </span>
              <span>&nbsp;/&nbsp;</span>
              <span className={procedure ? undefined : "active"}> Offline</span>
            </div>
          </BodyWrapper>
        </div>
      </div>
    </RecruitmentBackground>
  );
};

const RecruitmentBackground = styled.div`
  width: 321px;
  height: 246px;
  border-radius: var(--radius-10);
  background-color: #ffffff;
  margin: 16px 20px 16px 20px;
  background-size: 311px 236px;
  background-position: center;
  background-repeat: no-repeat;
  :hover {
    cursor: pointer;
  }

  > .recruitmentBody {
    border-radius: var(--radius-10);
    width: 321px;
    height: 246px;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    :hover {
      transition-duration: 0.5s;
      background-color: rgba(0, 0, 0, 0.75);
      .body-content {
        transition-duration: 0.5s;
        color: rgba(255, 255, 255, 1);
      }
    }

    > .border {
      width: 311px;
      height: 236px;
      border-radius: var(--radius-10);
      border: 2px solid var(--green);
    }
  }
`;
// 콘텐츠들 감싸서 정렬
const BodyWrapper = styled.div`
  margin: 0 43px 0 43px;
  padding: 45px 0 0 0;
  // 마이스터디
  > .body-myStudy {
    width: 227px;
    height: 64px;
    color: #ffffff;
    font-weight: 800;
    font-size: 32px;
    font-family: "mainEB";
  }
  // 한줄소개
  > .body-content--wrapper {
    width: 227px;
    height: 51px;
    margin-bottom: 18px;
    display: flex;
    align-items: center;
    > .body-content {
      color: rgba(0, 0, 0, 0);
      font-size: 14px;
      line-height: 17px;
    }
  }

  // 온오프라인
  > .body-online {
    font-size: 10px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;

    > .active {
      color: #7cc9a7;
    }
  }
`;
export default Recruitment;
