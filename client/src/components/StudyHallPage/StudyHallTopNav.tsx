import styled from "styled-components";
import StudyHallRightNav from "./StudyHallRightNav";

const StudyHallTopNav = () => {
  return (
    <TopNavWrapper>
      <Nav>
        <div>Main</div>
        <div>Community</div>
        <div>Calendar</div>
        <div>Setting</div>
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
    }
  }
`;
