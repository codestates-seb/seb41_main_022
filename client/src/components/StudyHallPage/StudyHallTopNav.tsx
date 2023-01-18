import styled from "styled-components";

import { useNavigate, useParams } from "react-router-dom";

import StudyHallRightNav from "./StudyHallRightNav";

const StudyHallTopNav = () => {
  const navigate = useNavigate();
  const { page, studyId } = useParams();
  const sId = Number(studyId);
  console.log(page);

  const navigateStudyHall = (whichPage: string, studyId: number) => {
    navigate(`/study-hall/${whichPage}/${studyId}`);
  };

  return (
    <TopNavWrapper>
      <Nav>
        <div
          onClick={() => navigateStudyHall("main", sId)}
          className={page === "main" ? `selected` : undefined}
        >
          Main
        </div>
        <div
          onClick={() => navigateStudyHall("community", sId)}
          className={page === "community" ? `selected` : undefined}
        >
          Community
        </div>
        <div
          onClick={() => navigateStudyHall("calendar", sId)}
          className={page === "calendar" ? `selected` : undefined}
        >
          Calendar
        </div>
        <div
          onClick={() => navigateStudyHall("setting", sId)}
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
