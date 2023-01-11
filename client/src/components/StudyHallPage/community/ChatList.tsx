import styled from "styled-components";

import Chat from "./Chat";
import { ChatData } from "../../../util/dummyData";

const ChatList = () => {
  return (
    <ChatListWrapper>
      {ChatData.data.map((el, idx) => (
        <Chat
          key={idx}
          username={el.username}
          content={el.content}
          dateTime={el.dateTime}
          imgUrl={el.imgUrl}
        />
      ))}
    </ChatListWrapper>
  );
};

const ChatListWrapper = styled.div``;

export default ChatList;
