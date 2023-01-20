import styled from "styled-components";
import { CommentsData } from "../../../util/dummyDataStudyHall";
import { FiTrash2 } from "react-icons/fi";
import React, { useForm } from "react-hook-form";
import { useState, useEffect } from "react";
import { useCookies } from "react-cookie";
import CreateAnswer from "./CreateAnswer";
import axios, { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
//내부컴포넌트 임포트
import Answers from "./Answers";
import { SpawnSyncOptionsWithBufferEncoding } from "child_process";

//타입지정
export interface CommentsProps {
  el: string;
  content: string;
  answers: any[];
  totalElements: number;
  size: number;
  imgUrl: string;
}
interface Data {
  data: any;
}

const Comments = ({ el, content, answers, imgUrl }: CommentsProps) => {
  const [cookies, setCookie, removeCookie] = useCookies(["token", "userData"]);
  // const { studyId, page } = useParams;

  const [answersData, setAnswersData] = useState<CommentsProps | undefined>();
  const getAnswersData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
      },
    });
  };

  // useEffect(() => {
  //   getAnswersData(
  //     `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/chat/${studyId}?page=${page}&size=${size}`
  //   ).then((res) => {
  //     setAnswersData(res.data.data);
  //   });
  // });

  const { register, handleSubmit } = useForm();
  const [showAnswer, setShowAnswer] = useState(false);

  return (
    <CommentsWrapper
      onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}
    >
      <Wrapper>
        <CommentBox>
          <img src={imgUrl} />
          <Texts>
            <UserName>{el}</UserName>
            <Content>{content}</Content>
            <span className="answerNum">
              <AddButton
                type="button"
                className="numAnswers"
                onClick={() => {
                  setShowAnswer(!showAnswer);
                }}
              >
                답글
              </AddButton>
              <div className="totalElements">
                {answersData && answersData.totalElements}
              </div>
              <div className="trashIcon">
                <FiTrash2></FiTrash2>
              </div>
            </span>
            {showAnswer && (
              <span>
                <Input type="text" {...register("content")} />
                <AnswerButton type="submit" {...register("answer")}>
                  Add
                </AnswerButton>

                {answers.map((el) => (
                  <Answers
                    key={el.answerId}
                    answerUserId={el.answerUserId}
                    content={el.content}
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
  > img {
    width: 30px;
    height: 30px;
    background-color: blue;
    border-radius: 70%;
  }
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
  margin-left: 10px;

  > .numAnswers {
    font-size: 9px;
  }
  > .answerNum {
    display: flex;
    font-size: 9px;
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
