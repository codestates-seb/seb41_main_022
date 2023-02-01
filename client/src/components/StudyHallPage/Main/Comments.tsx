import styled from "styled-components";
import { useState } from "react";
import { useCookies } from "react-cookie";
import axios from "axios";
import { MdOutlineLock } from "react-icons/md";
import { useNavigate, useParams } from "react-router-dom";
//내부컴포넌트 임포트
import Answers from "./Answers";
import { answerStore } from "../../../util/zustandCreatAnswer";
import moment from "moment/moment";
import TrashButton from "./TrashButton";
import { commentStore } from "../../../util/zustandComment";
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
  chatCreatedAt: string;
  page: number;
  requestSize: number;
}

interface AnswerProps {
  answerId: number;
  username: string;
  imgUrl: string;
  content: string;
  answerCreatedAt: string;
  chatCreatedAt: string;
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
  chatCreatedAt,
  totalElements,
  page,
  requestSize,
}: CommentsProps) => {
  const [cookies] = useCookies(["token"]);
  const { studyId } = useParams();
  const [showAnswer, setShowAnswer] = useState(false);
  const navigate = useNavigate();
  const { postAnswer, postedAnswer } = answerStore();
  const [answer, setAnswer] = useState("");
  const { fetchCommentData } = commentStore();

  //대댓글 작성
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (chatId) {
      await postAnswer(
        chatId,
        { content: answer },
        {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        }
      );
      if (studyId) {
        fetchCommentData(
          cookies.token.accessToken,
          cookies.token.refreshToken,
          studyId,
          page,
          requestSize
        );
      }
      setAnswer("");
    }
  };

  const handleClickDeleteComment = () => {
    axios
      .delete(`${process.env.REACT_APP_API_URL}/chat/${chatId}`, {
        headers: {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        },
      })
      .then(() => {
        if (studyId) {
          fetchCommentData(
            cookies.token.accessToken,
            cookies.token.refreshToken,
            studyId,
            page,
            requestSize
          );
        }
      });
  };

  const getDayMinuteCounter = (date?: object): number | string => {
    if (!date) {
      return "";
    }
    const today = moment();
    const postingDate = moment(date).add(9, "hours");
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
                <div className="date">
                  {getDayMinuteCounter(moment(chatCreatedAt))}
                </div>
                {isClosedChat === false ? null : <MdOutlineLock />}
                <TrashButton handleClick={handleClickDeleteComment} />
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
                  value={answer}
                />
                <AnswerButton type="submit">Add</AnswerButton>
                {answers.map((el) => (
                  <Answers
                    key={el.answerId}
                    answerId={el.answerId}
                    username={el.username}
                    content={el.content}
                    imgUrl={el.imgUrl}
                    answerCreatedAt={el.answerCreatedAt}
                    page={page}
                    requestSize={requestSize}
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
      display: flex;
      justify-content: flex-end;
      > .date {
        display: flex;
        align-items: center;
        font-size: 8px;
        color: var(--beige-00);
        margin-right: 5px;
      }
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
