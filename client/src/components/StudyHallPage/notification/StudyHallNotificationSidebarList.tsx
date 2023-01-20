import styled from "styled-components";

import { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const StudyHallNotificationSidebarList = () => {
  const { studyId } = useParams();
  const [todayData, setTodayData] = useState([]);
  const fetch = (url: string) => {
    return axios.get(url + "/calendar?studyId=" + studyId);
  };
  const date: string = new Date().toISOString().split("T")[0];
  useEffect(() => {
    fetch(URL).then((res) => {
      setTodayData(
        res.data.data.filter(
          (el: { date: string }) => el.date.split("T")[0] === date
        )
      );
    });
  }, []);
  return (
    <TodoWrapper>
      {todayData.length ? (
        todayData.map(
          (el: { calendarId: string; date: string; title: string }) => (
            <Todo key={el.calendarId}>
              {el.date.split("T")[0] + " / " + el.date.split("T")[1]}
              <div>{el.title}</div>
            </Todo>
          )
        )
      ) : (
        <Todo>
          {date}
          <div>일정이 없습니다</div>
        </Todo>
      )}
    </TodoWrapper>
  );
};

const TodoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 4px auto;
  max-height: 160px;
  overflow: auto;
  &::-webkit-scrollbar {
    width: 4px;
  }
  &::-webkit-scrollbar-thumb {
    border-radius: 2px;
    background: var(--green-00);
  }
  * {
    font-size: 14px;
    font-family: mainM;
  }
`;
const Todo = styled.div`
  display: flex;
  flex-direction: column;
  margin: 5px 0;
`;

export default StudyHallNotificationSidebarList;
