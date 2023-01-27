import styled from "styled-components";
import { useForm } from "react-hook-form";

import Answers from "./Answers";

export interface CreatAnswerProps {
  username: string;
  content: string;
  answers: any[];
  totalElements: number;
  imgUrl: string;
}

const CreateAnswer = ({
  username,
  content,
  answers,
  totalElements,
  imgUrl,
}: CreatAnswerProps) => {
  const { register, handleSubmit } = useForm();
  return (
    <CreateAnserWrapper
      onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}
    >
      <span>
        <Input type="text" {...register("content")} />
        <AnswerButton type="submit" {...register("answer")}>
          Add
        </AnswerButton>

        {answers.map((el) => (
          <Answers
            key={el.answerId}
            username={el.username}
            content={el.content}
            imgUrl={el.imgUrl}
            answerCreatedAt={el.answerCreatedAt}
          />
        ))}
      </span>
    </CreateAnserWrapper>
  );
};

export default CreateAnswer;

const CreateAnserWrapper = styled.div`
  * {
    font-family: "mainEB";
    font-size: 14px;
    color: var(--beige-00);
  }
  background-color: var(--green);
  color: var(--beige-00);
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
