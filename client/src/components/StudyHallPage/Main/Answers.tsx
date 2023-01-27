import styled from "styled-components";
import { FiTrash2 } from "react-icons/fi";
import moment from "moment";

//타입지정
export interface AnswerProps {
  username: string;
  content: string;
  imgUrl: string;
  answerCreatedAt: string;
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
  username,
  content,
  imgUrl,
  answerCreatedAt,
}: AnswerProps) => {
  return (
    <AnswerWrapper>
      <Answer>
        <div className="answerpack">
          <div className="idContent">
            <Img src={imgUrl} />
            {username} : {content}
          </div>
          <EctWrap>
            <div className="trashIcon">
              <FiTrash2 />
            </div>
            <div className="date">
              {getDayMinuteCounter(moment(answerCreatedAt))}
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
    > .date {
      font-size: 8px;
      color: var(--beige-00);
      justify-content: flex-end;
    }
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
`;

const Img = styled.img`
  width: 10px;
  height: 10px;
  background-color: white;
  border-radius: 5px;
  margin-right: 5px;
`;
