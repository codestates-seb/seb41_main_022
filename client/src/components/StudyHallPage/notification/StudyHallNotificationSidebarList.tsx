import styled from "styled-components";

import TodoData from "../../../util/dummyDataTodo";
//화면에 오늘 할일 리스트로 표시
const StudyHallNotificationSidebarList = () => {
  return (
    <TodoWrapper>
      {TodoData.data.map((el) => (
        <Todo key={el.calendarId}>
          {el.date} / {el.time.slice(0, 5)}
          <div>{el.content}</div>
        </Todo>
      ))}
    </TodoWrapper>
  );
};

const TodoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 0 auto;
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
