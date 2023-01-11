import styled from "styled-components";

import Chat from "./Chat";
import { ChatData } from "../../../util/dummyData";

const ChatList = () => {
  return (
    <ChatListWrapper>
      {/* 내 채팅일때랑 아닐때 구분 */}
      {ChatData.data.map((el, idx) =>
        el.userId === 6 ? (
          <div className="myChatWrapper">
            <div className="myChat">{el.content}</div>
          </div>
        ) : (
          <Chat
            key={idx}
            username={el.username}
            content={el.content}
            dateTime={el.dateTime}
            imgUrl={el.imgUrl}
          />
        )
      )}
    </ChatListWrapper>
  );
};

const ChatListWrapper = styled.div`
  background-color: var(--green);
  border-radius: var(--radius-10);
  width: calc(460px - 40px);
  padding: 16px 20px;
  margin-bottom: 16px;
  > .myChatWrapper {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-20);
    background-color: var(--green);
    max-width: 270px;
    font-family: "mainM";
    font-size: 14px;
    color: var(--beige-00);
    padding: 8px 8px 8px 16px;
    line-height: 16px;
    margin-top: 8px;
    margin-left: calc(420px - 270px);
  }
`;

export default ChatList;
