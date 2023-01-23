import styled from "styled-components";
import WeekBar from "../WeekBar";
import { VscBellDot } from "react-icons/vsc";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import NoticeStore from "../../util/zustandNotice";
import Ticker from "react-ticker";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const StudyHallRightNav = () => {
  const {
    dayOfWeek,
    notice,
    zustandStudyId,
    resetNotice,
    fetchRightNav,
    patchNotice,
    saveNotice,
  } = NoticeStore();
  const { studyId } = useParams();
  useEffect(() => {
    setTimeout(() => {
      if (zustandStudyId !== studyId) {
        resetNotice();
      }
      fetchRightNav(URL, studyId);
    }, 500);
  }, [saveNotice]);
  return (
    <Margin20>
      <WeekBar dayOfWeek={dayOfWeek} />
      <Notice>
        <div className="wrapper">
          <div>
            <VscBellDot />
          </div>
          {notice && (
            <Ticker>
              {() => <div className="noticeFont">{notice ? notice : ""}</div>}
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
        width: 350px;
        display: flex;
        justify-content: center;
      }
      .noticeFont {
        font-family: "mainM", Arial;
        font-size: 12px;
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
