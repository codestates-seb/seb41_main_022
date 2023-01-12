import styled from "styled-components";
import { CommentsData } from "../../../util/dummyDataStudyHall";
//내부컴포넌트 임포트
import Answer from "./Answer";

//타입지정
interface ComementsProps {
  chatUserId: string;
  content: string;
  //   <T>(answers: T): T;
  answers: any[];
}

const Comments = ({ chatUserId, content, answers }: ComementsProps) => {
  return (
    <CommentsWrapper>
      <Wrapper>
        <CommentBox>
          <CommentImg></CommentImg>
          <Texts>
            <UserId>{chatUserId}</UserId>
            <Content>{content}</Content>
            <div className="numAnswers">답글{}</div>
            {answers.map((el) => (
              <Answer
                key={el.answerId}
                answerUserId={el.answerUserId}
                content={el.content}
              />
            ))}
          </Texts>
        </CommentBox>
      </Wrapper>
    </CommentsWrapper>
  );
};
export default Comments;

const CommentsWrapper = styled.div`
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
  height: 0px auto;
  border: 1px solid var(--beige-00);
  border-radius: 10px;
  display: flex;
  padding: 10px;

  > .numAnswers {
    font-size: 8px;
  }
`;

const Texts = styled.div`
  margin-left: 10px;
`;

const CommentImg = styled.div`
  width: 30px;
  height: 30px;
  background-color: blue;
  border-radius: 10px;
`;

const UserId = styled.div`
  font-size: 10px;
  margin: 5px;
`;

const Content = styled.div``;
