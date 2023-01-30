import styled from "styled-components";
import WeekBar from "../WeekBar";
import { VscBellDot } from "react-icons/vsc";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import NoticeStore from "../../util/zustandNotice";
import Ticker from "react-ticker";

const StudyHallRightNav = () => {
  const { dayOfWeek, notice, zustandStudyId, resetNotice, fetchRightNav } =
    NoticeStore();
  const { studyId } = useParams();
  useEffect(() => {
    // if()
    if (zustandStudyId !== studyId) {
      resetNotice();
      fetchRightNav(studyId);
    }
  }, [notice]);
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
