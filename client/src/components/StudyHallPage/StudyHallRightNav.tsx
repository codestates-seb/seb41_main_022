import styled from "styled-components";
import WeekBar from "../WeekBar";
import { FaCaretRight } from "react-icons/fa";
import { VscBellDot } from "react-icons/vsc";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import NoticeStore from "../../util/zustandNotice";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const StudyHallRightNav = () => {
  const { notice, fetchNotice } = NoticeStore();
  const { studyId } = useParams();
  const [isOpen, setIsOpen] = useState<boolean>(false);
  useEffect(() => {
    if (notice === "") {
      fetchNotice(URL, studyId);
    }
  }, []);
  return (
    <Margin20>
      <WeekBar dayOfWeek={["MON"]} />
      <Notice>
        <div className="wrapper">
          <div>
            <VscBellDot />
          </div>
          {isOpen ? (
            <div>{notice ? notice : "공지가 비어있습니다"}</div>
          ) : (
            <div>공지사항</div>
          )}
          <TopNavHover>
            <FaCaretRight
              onClick={() => {
                setIsOpen(!isOpen);
              }}
            />
          </TopNavHover>
        </div>
      </Notice>
    </Margin20>
  );
};
export default StudyHallRightNav;

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
