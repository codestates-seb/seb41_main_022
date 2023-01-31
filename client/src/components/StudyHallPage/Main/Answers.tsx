import styled from "styled-components";
import { FiTrash2 } from "react-icons/fi";
import moment from "moment";
import axios from "axios";
import { useParams } from "react-router-dom";

import { useCookies } from "react-cookie";
import { commentStore } from "../../../util/zustandComment";

//타입지정
export interface AnswerProps {
  answerId: number;
  username: string;
  content: string;
  imgUrl: string;
  answerCreatedAt: string;
  page: number;
  requestSize: number;
}
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
const Answers = ({
  answerId,
  username,
  content,
  imgUrl,
  answerCreatedAt,
  page,
  requestSize,
}: AnswerProps) => {
  const [cookies] = useCookies(["token"]);
  const { fetchCommentData } = commentStore();
  const { studyId } = useParams();

  const deleteAnswer = () => {
    axios
      .delete(`${process.env.REACT_APP_API_URL}/answer/${answerId}`, {
        headers: {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        },
      })
      .then(() => {
        if (studyId)
          fetchCommentData(
            cookies.token.accessToken,
            cookies.token.refreshToken,
            studyId,
            page,
            requestSize
          );
      });
  };
  return (
    <AnswerWrapper>
      <Answer>
        <div className="answerpack">
          <div className="idContent">
            <Img src={imgUrl} />
            {username} : {content}
          </div>
          <EctWrap>
            <div className="date">
              {getDayMinuteCounter(moment(answerCreatedAt))}
            </div>
            <div className="trashIcon">
              <FiTrash2 onClick={() => deleteAnswer()} />
            </div>
          </EctWrap>
        </div>
      </Answer>
    </AnswerWrapper>
  );
};

export default Answers;

const AnswerWrapper = styled.div`
  * {
    font-family: "mainB", Arial;
    font-size: 10px;
  }
`;

const Answer = styled.div`
  > .answerpack {
    align-items: center;
    display: flex;
    padding: 3px;
    flex-direction: row;
    width: 391px;

    justify-content: space-between;
    > .input {
    }
    .img {
      width: 10px;
      height: 10px;
      background-color: white;
      border-radius: 5px;
      margin-right: 5px;
    }
  }

  .idContent {
    height: 100%;
    display: flex;
    align-items: center;
    padding-left: 2px;
    padding-right: 2px;
    font-size: 9px;
    flex-direction: row;
  }
`;

const EctWrap = styled.div`
  display: flex;
  padding: 0px 2px 0px 2px;
  > .date {
    display: flex;
    align-items: center;
    font-size: 8px;
    color: var(--beige-00);
    margin-right: 5px;
  }
`;

const Img = styled.img`
  width: 10px;
  height: 10px;
  background-color: white;
  border-radius: 5px;
  margin-right: 5px;
`;
