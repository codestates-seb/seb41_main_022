import styled, { StyleSheetManager } from "styled-components";

//컴포넌트 import
import StudyInfo from "./Main/StudyInfo";
import CreateComment from "./Main/CreateComment";
import Comments from "./Main/Comments";

//API Data import
import StudyHallData from "../../util/dummyDataStudyHall";
import { CommentsData } from "../../util/dummyDataStudyHall";
const StudyHallMain = () => {
  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {
            <StudyInfo
              teamName={StudyHallData.data.teamName}
              summary={StudyHallData.data.summary}
              content={StudyHallData.data.content}
            />
          }
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
