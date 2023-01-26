import styled from "styled-components";
import { FiTrash2 } from "react-icons/fi";
import { useState, useEffect } from "react";
import { useCookies } from "react-cookie";
import axios, { AxiosResponse } from "axios";
import { MdOutlineLock } from "react-icons/md";

//내부컴포넌트 임포트
import Answers from "./Answers";
import { answerStore } from "../../../util/zustandCreatAnswer";

//타입지정
export interface CommentsProps {
  username: string;
  content: string;
  answers: any[];
  totalElements: number;
  size: number;
  imgUrl: string;
  chatId: number;
  isClosedChat: boolean;
}

interface AnswerProps {
  answerId: number;
  username: string;
  imgUrl: string;
  content: string;
  answerCreatedAt: string;
}
interface Data {
  data: any;
}

const URL = process.env.REACT_APP_API_URL;

const Comments = ({
  username,
  content,
  answers,
  imgUrl,
  chatId,
  isClosedChat,
  totalElements,
}: CommentsProps) => {
  const [cookies] = useCookies(["token"]);

  console.log(content);

  const [showAnswer, setShowAnswer] = useState(false);

  const postAnswer = answerStore((state) => state.postAnswer);
  const [answer, setAnswer] = useState("");

  //대댓글 작성
  const handleSubmit = () => {
    if (chatId) {
      postAnswer(
        chatId,
        { content: answer },
        {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        }
      );
    }
  };

  return (
    <CommentsWrapper onSubmit={handleSubmit}>
      <Wrapper>
        <CommentBox>
          <Img src={imgUrl} />
          <Texts>
            <UserName>{username}</UserName>
            <Content>{content}</Content>
            <span className="answerNumIcon">
              {username === "Secret" ? null : (
                <div className="answerNum">
                  <AddButton
                    type="button"
                    className="numAnswers"
                    onClick={() => {
                      setShowAnswer(!showAnswer);
                    }}
                  >
                    답글
                  </AddButton>
                  <div className="totalElements">{answers.length}</div>
                </div>
              )}
              <div className="icons">
                {isClosedChat === false ? null : <MdOutlineLock />}
                <FiTrash2 type="button" />
              </div>
            </span>
            {showAnswer && (
              <span>
                <Input
                  id="answer"
                  type="text"
                  placeholder="Answer"
                  onChange={(e: any) => {
                    setAnswer(e.target.value);
                  }}
                />
                <AnswerButton type="submit">Add</AnswerButton>
                {answers.map((el) => (
                  <Answers
                    key={el.answerId}
                    username={el.username}
                    content={el.content}
                    imgUrl={el.imgUrl}
                  />
                ))}
              </span>
            )}
          </Texts>
        </CommentBox>
      </Wrapper>
    </CommentsWrapper>
  );
};

//FiTrash2
export default Comments;

const CommentsWrapper = styled.form`
  * {
    font-family: "mainEB";
    font-size: 14px;
    color: var(--beige-00);
  }
  background-color: var(--green);
  color: var(--beige-00);
`;

const Wrapper = styled.div`
  width: 460px;
  margin: 0px auto;
  padding-top: 15px;
`;

const CommentBox = styled.div`
  width: 450px;
  height: auto;
  border: 1px solid var(--beige-00);
  border-radius: 10px;
  display: flex;
  padding: 10px;
`;
const Img = styled.img`
  width: 30px;
  height: 30px;
  background-color: var(--mopo-00);
  border-radius: 70%;
`;
const AddButton = styled.button`
  color: var(--beige-00);
  background-color: var(--green);
  border: none;
  font-size: 10px;
  :hover {
    color: var(--blue);
  }
  > .totalElements {
    font-size: 10px;
  }
`;

const Texts = styled.div`
  width: 400px;
  margin-left: 10px;

  > .numAnswers {
    font-size: 9px;
  }
  > .answerNumIcon {
    justify-content: space-between;
    display: flex;
    font-size: 9px;
    width: 400px;
    > .answerNum {
      display: flex;
    }
    .icons {
      justify-content: flex-end;
    }
  }
`;
const UserName = styled.div`
  font-size: 10px;
  margin: 5px;
`;

const Content = styled.div`
  font-size: 12px;
  padding-left: 2px;
`;

const Input = styled.input`
  background-color: var(--green);
  color: var(--beige-00);
  background-color: var(--green);
  border: solid 1px;
  border-radius: 30px;
  padding: 1px 9px 1px 9px;
  font-size: 10px;
`;
const AnswerButton = styled.button`
  font-size: 10px;
  color: var(--beige-00);
  background-color: var(--green);
  border: solid 1px;
  border-radius: 30px;
  padding: 1px 9px 1px 9px;
`;
