import styled from "styled-components";
import { useNavigate } from "react-router-dom";

interface CardProps {
  teamName: string;
  summary: string;
  studyId: number;
  imgUrl: string;
}

const MyStudy = ({ teamName, summary, studyId, imgUrl }: CardProps) => {
  const navigate = useNavigate();

  return (
    <RecruitmentBackground
      onClick={() => navigate(`/study-hall/main/${studyId}`)}
      style={{ backgroundImage: `url(${imgUrl})` }}
    >
      <div className="recruitmentBody">
        <div className="border">
          <BodyWrapper>
            <div className="body-myStudy">{teamName}</div>
            <div className="body-content--wrapper">
              <div className="body-content">{summary}</div>
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
    color: #ffffff;
    font-weight: 800;
    font-size: 32px;
    margin-bottom: 22px;
    font-family: "mainEB";
  }
  // 한줄소개
  > .body-content--wrapper {
    width: 227px;
    height: 51px;
    margin-bottom: 25px;
    display: flex;
    align-items: center;
    > .body-content {
      color: rgba(0, 0, 0, 0);
      font-size: 14px;
      line-height: 17px;
    }
  }
`;
export default MyStudy;
