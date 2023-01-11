import styled from "styled-components";

import { ChatData } from "../../../util/dummyData";
import ChatList from "./ChatList";
import StudyHallCommunityMemberList from "./StudyHallCommunityMemberList";

const Community = () => {
  return (
    <CommunityWrapper>
      <div className="padding" />
      <div>
        <div className="flex">
          <div className="title">Chat</div>
          <ChatList />
        </div>
        <StudyHallCommunityMemberList />
      </div>
    </CommunityWrapper>
  );
};

const CommunityWrapper = styled.main`
  background-color: var(--beige-00);
  min-height: 400px;
  * {
    font-family: "mainB", Arial;
  }
  .padding {
    padding-top: 25px;
  }
  > div {
    width: 460px;
    margin: 0 auto;
    display: flex;
  }
  .title {
    color: var(--green);
    font-size: 24px;
  }
`;

export default Community;
