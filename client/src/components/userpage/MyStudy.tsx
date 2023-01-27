import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

interface TreeProps {
  treeId: number;
  treePoint: number;
  treeImage: string;
  createdAt: string;
  makeMonth: number;
  teamName: string;
}
interface treeType {
  tree: object | undefined;
}
interface CardProps {
  teamName: string;
  summary: string;
  studyId: number;
  imgUrl: string;
  tree: any;
}

const MyStudy = ({ teamName, summary, studyId, imgUrl, tree }: CardProps) => {
  const navigate = useNavigate();

  return (
    <RecruitmentBackground
      onClick={() => navigate(`/user/${studyId}`)}
      style={{ backgroundImage: `url(${imgUrl})` }}
    >
      <div className="recruitmentBody">
        <div className="border">
          <BodyWrapper>
            <Img src={tree.treeImage} />
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
  transform: scale(1);
  transition: all 0.3s ease;
  :hover {
    cursor: pointer;
    box-shadow: 1px 1px 5px 1px rgba(0, 0, 0, 0.2);
    transform: scale(1.05);
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
      transition-duration: 200ms;
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
const Img = styled.img`
  position: absolute;
  width: 80px;
  margin-left: 190px;
  margin-top: 100px;
`;
export default MyStudy;
