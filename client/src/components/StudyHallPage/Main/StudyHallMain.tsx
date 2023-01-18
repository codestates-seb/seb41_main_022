import styled, { StyleSheetManager } from "styled-components";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";

//API Data import
import { CommentsData } from "../../../util/dummyDataStudyHall";

const StudyHallMain = () => {
  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {<StudyInfo />}
          <CreateComment />
          {CommentsData.data.map((el) => (
            <Comments
              key={el.chatId}
              chatUserId={el.chatUserId}
              content={el.content}
              answers={el.answers}
              totalElements={CommentsData.pageInfo.totalElements}
            />
          ))}
        </div>
      </div>
    </MainWrapper>
  );
};
export default StudyHallMain;

const MainWrapper = styled.div``;
