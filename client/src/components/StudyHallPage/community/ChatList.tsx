import styled from "styled-components";
import { useRef, useCallback, useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

import ChatStore from "../../../util/zustandCommunity";
import Chat from "./Chat";
import moment from "moment/moment";

const ChatList = () => {
  const [cookies] = useCookies(["token", "userData"]);
  const userId = cookies.userData.userId;
  const { chatData, submitChat, getChatData } = ChatStore();
  const { studyId } = useParams();
  const [chatContent, setChatContent] = useState("");
  const textRef = useRef<HTMLTextAreaElement>(null);
  const handleResizeHeight = useCallback(() => {
    if (textRef.current) {
      textRef.current.style.height = "16px";
      textRef.current.style.height = textRef.current.scrollHeight + "px";
    }
  }, []);

  const messageBoxRef = useRef<any>();
  const scrollToBottom = () => {
    if (messageBoxRef.current) {
      messageBoxRef.current.scrollTop = messageBoxRef.current.scrollHeight;
    }
  };

  const handleSendClick = () => {
    const date = new Date();

    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);
    const hours = ("0" + date.getHours()).slice(-2);
    const minutes = ("0" + date.getMinutes()).slice(-2);
    const seconds = ("0" + date.getSeconds()).slice(-2);

    studyId &&
      submitChat(
        studyId,
        chatContent,
        `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`,
        cookies.token.accessToken,
        cookies.token.refreshToken
      );

    setChatContent("");
  };
  const EnterKey = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.shiftKey && e.key === "Enter") {
      return;
    } else if (e.key === "Enter") {
      handleSendClick();
    }
  };
  useEffect(() => {
    if (studyId) {
      getChatData(studyId);
    }
  }, []);
  useEffect(() => {
    scrollToBottom();
  }, [chatData]);
  const getDayMinuteCounter = (date?: object): number | string => {
    if (!date) {
      return "";
    }
    const today = moment();
    const postingDate = moment(date);
    const dayDiff = postingDate.diff(today, "days");
    const hourDiff = postingDate.diff(today, "hours");
    const minutesDiff = postingDate.diff(today, "minutes");
    if (minutesDiff === 0) {
      return "방금 전";
    }
    if (dayDiff === 0 && hourDiff === 0) {
      const minutes = Math.ceil(-minutesDiff);
      return minutes + "분 전";
    }
    if (dayDiff === 0 && hourDiff <= 24) {
      const hour = Math.ceil(-hourDiff);
      return hour + "시간 전";
    }
    return -dayDiff + "일 전";
  };
  return (
    <ChatListWrapper>
      <div className="flex">
        <WriteChat>
          <textarea
            value={chatContent}
            onChange={(e) => {
              setChatContent(e.target.value);
            }}
            className="textArea"
            ref={textRef}
            onInput={handleResizeHeight}
            placeholder="Write Message here..."
            onKeyPress={EnterKey}
          ></textarea>
          <button onClick={handleSendClick}>Send</button>
        </WriteChat>
      </div>
      <ChatWrapper ref={messageBoxRef}>
        {/* 내 채팅일때랑 아닐때 구분 */}
        {chatData.map((el, idx) =>
          el.messageUserId === userId ? (
            <div key={idx} className="myChatWrapper">
              <div className="time">
                {getDayMinuteCounter(moment(el.dateTime))}
              </div>
              <div className="myChat">
                {el.content.split("\n").map((el, idx) => (
                  <span key={idx}>
                    {el}
                    <br />
                  </span>
                ))}
              </div>
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
      </ChatWrapper>
    </ChatListWrapper>
  );
};

const ChatListWrapper = styled.div`
  background-color: var(--green);
  border-radius: var(--radius-10);
  width: calc(460px - 40px);
  padding: 16px 20px;
  margin: 16px 0 16px 0;
  height: 600px;
  display: flex;
  flex-direction: column-reverse;
  max-height: 635px;

  > .flex {
    width: 100%;
    display: flex;
    align-items: center;
    margin-top: 16px;
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

const ChatWrapper = styled.div`
  display: flex;
  flex-direction: column;
  overflow: auto;
  > .myChatWrapper {
    .time {
      width: 240px;
      text-align: right;
      position: relative;
      font-size: 8px;
      margin-top: -25px;
      display: none;
    }
    :hover {
      .time {
        color: var(--gray-20);
        display: block;
      }
      .myChat {
        margin-top: 9px;
      }
    }
    .myChat {
      font-family: "mainL";
      margin-top: 0;
    }
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-20);
    background-color: var(--green);
    max-width: 270px;
    font-size: 12px;
    color: var(--beige-00);
    padding: 8px 8px 8px 16px;
    line-height: 16px;
    margin-top: 18px;
    margin-left: calc(420px - 270px);
  }
`;

export default ChatList;
