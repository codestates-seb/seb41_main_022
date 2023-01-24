import styled from "styled-components";
import WeekBar from "../WeekBar";
import { VscBellDot } from "react-icons/vsc";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import NoticeStore from "../../util/zustandNotice";
import Ticker from "react-ticker";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const StudyHallRightNav = () => {
  const { dayOfWeek, notice, zustandStudyId, resetNotice, fetchRightNav } =
    NoticeStore();
  const { studyId } = useParams();
  const [noticeR, setNoticeR] = useState("");
  useEffect(() => {
    // if()
    if (zustandStudyId !== studyId) {
      resetNotice();
    }
    fetchRightNav(URL, studyId);
    setNoticeR(notice);
  }, [notice]);
  return (
    <Margin20>
      <WeekBar dayOfWeek={dayOfWeek} />
      <Notice>
        <div className="wrapper">
          <div>
            <VscBellDot />
          </div>
          {noticeR && (
            <Ticker>
              {() => <div className="noticeFont">{noticeR ? noticeR : ""}</div>}
            </Ticker>
          )}
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
    color: var(--beige-00);
    display: flex;
    align-items: center;
    padding: 3px;
  }
`;
