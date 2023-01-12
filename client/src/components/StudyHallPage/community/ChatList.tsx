import styled from "styled-components";
import { useRef, useCallback } from "react";

import Chat from "./Chat";
import { ChatData } from "../../../util/dummyData";

const ChatList = () => {
  const textRef = useRef<HTMLTextAreaElement>(null);
  const handleResizeHeight = useCallback(() => {
    if (textRef.current) {
      textRef.current.style.height = "16px";
      textRef.current.style.height = textRef.current.scrollHeight + "px";
    }
  }, []);

  return (
    <ChatListWrapper>
      <div className="flex">
        <WriteChat>
          <textarea
            className="textArea"
            ref={textRef}
            onInput={handleResizeHeight}
            placeholder="Write Message here..."
          ></textarea>
          <button>Send</button>
        </WriteChat>
      </div>
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
  margin: 16px 0 16px 0;
  > .myChatWrapper {
    .myChat {
      font-family: "mainL";
    }
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-20);
    background-color: var(--green);
    max-width: 270px;
    font-size: 12px;
    color: var(--beige-00);
    padding: 8px 8px 8px 16px;
    line-height: 16px;
    margin-top: 8px;
    margin-left: calc(420px - 270px);
  }
  > .flex {
    width: 100%;
    display: flex;
    align-items: center;
  }
`;
const WriteChat = styled.div`
  * {
    font-family: "mainL";
  }
  border: 1px solid var(--beige-00);
  border-radius: var(--radius-20);
  background-color: var(--green);
  width: 100%;
  font-size: 12px;
  color: var(--beige-00);
  line-height: 16px;
  display: flex;
  align-items: flex-start;
  justify-content: space-around;
  padding: 8px;

  > textarea {
    background-color: var(--green);

    border: none;
    color: var(--beige-00);
    width: 85%;
    max-height: 80px;
    resize: none;
    overflow: auto;
    padding: 8px;
  }
  > textarea:focus {
    outline: none;
  }
  .textArea::-webkit-scrollbar {
    width: 8px; /* 스크롤바의 너비 */
  }

  .textArea::-webkit-scrollbar-thumb {
    height: 30%; /* 스크롤바의 길이 */
    background: rgba(255, 255, 255, 0.15); /* 스크롤바의 색상 */
    border-radius: 10px;
  }

  .textArea::-webkit-scrollbar-track {
    background: rgba(0, 0, 0, 0.2); /*스크롤바 뒷 배경 색상*/
  }
  > button {
    border-radius: var(--radius-20);
    background-color: var(--green);
    border: none;
    color: var(--beige-00);
    margin-top: 4px;
    :hover {
      cursor: pointer;
      color: var(--blue);
    }
  }
`;

export default ChatList;
