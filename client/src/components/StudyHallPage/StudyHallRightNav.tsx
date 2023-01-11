import styled from "styled-components";
import WeekBar from "../WeekBar";
import { FaCaretRight } from "react-icons/fa";
import { VscBellDot } from "react-icons/vsc";
//반드시 detail component(main, community, calendar, setting)를 부모로 받아야 합니다
const StudyHallRightNav = () => {
  return (
    <Margin20>
      <WeekBar />
      <Notice>
        <div className="wrapper">
          <div>
            <VscBellDot />
          </div>
          <div>공지사항</div>
          <TopNavHover>
            <FaCaretRight />
          </TopNavHover>
        </div>
      </Notice>
    </Margin20>
  );
};
export default StudyHallRightNav;
// content의 width와 nav width의 합에 25px만큼 마진
// top은 20만큼 마진
const Margin20 = styled.main`
  margin-top: 122px;
  position: absolute;
  margin-left: 685px;
  * {
    font-family: "mainM", Arial;
    font-size: 12px;
  }
`;
const Notice = styled.div`
  margin-top: 5px;
  width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  .wrapper {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 2px;
  }
  div {
    color: var(--beige-00);
    display: flex;
    align-items: center;
    padding: 3px;
  }
`;
const TopNavHover = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--green);
  border-radius: var(--radius-30);
  margin: 1px;
  width: 12px;
  height: 12px;
  color: var(--gray-00);
  :hover {
    background-color: var(--beige-00);
    color: var(--green);
  }
`;
