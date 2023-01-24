import styled from "styled-components";
import { FaBeer } from "react-icons/fa";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

import { commentStore } from "../../../util/zustandComment";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const CreateComment = () => {
  const [content, setContent] = useState(""); //댓글작성 상태변화
  const [isClosedChat, setIsClosedChat] = useState(false); //공개/비공개 상태변화
  const { studyId } = useParams(); //API에 보내지는 파람스와 동일
  const [cookies] = useCookies(["token"]); //쿠키 보내주기
  const postComment = commentStore((state) => state.postComment);

  const handleSubmit = () => {
    if (studyId) {
      postComment(
        URL,
        studyId,
        { content, isClosedChat },
        {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        }
      );
    }
  };
  return (
    <CreateWrapper>
      <Wrapper onSubmit={handleSubmit}>
        <Create>
          <img className="profile" />
          <Comment
            id="comment"
            type="text"
            placeholder="Write Message Here..."
            onChange={(e: React.ChangeEvent<HTMLInputElement>) => {
              setContent(e.target.value);
            }}
          />
          <AddButton type="submit">Add</AddButton>
        </Create>
        <CheckBox>
          <StyledInput
            type="checkbox"
            id="checkBox"
            onClick={() => {
              setIsClosedChat(!isClosedChat);
            }}
          />
          <div className="private">비공개</div>
        </CheckBox>
      </Wrapper>
    </CreateWrapper>
  );
};

export default CreateComment;

const CreateWrapper = styled.div`
  * {
    font-family: "mainEB";
    font-size: 14px;
    color: var(--beige-00);
  }
  background-color: var(--green);
  color: var(--beige-00);
  padding-top: 20px;
`;

const Wrapper = styled.form`
  width: 460px;
  height: 50px;
  margin: 0px auto;
  border: 1px solid var(--beige-00);
  border-radius: 30px;
`;

const Create = styled.div`
  display: flex;
`;

const AddButton = styled.button`
  font-family: "mainL";
  font-size: 12px;
  width: 50px;
  height: 23px;
  color: var(--beige-00);
  border: 1px solid var(--beige-00);
  background-color: var(--green);
  border-radius: 30px;
  margin: 10px 0px 0px 0px;
`;

const Comment = styled.input`
  color: var(--green);
  background-color: var(--beige-00);
  border: solid 1px;
  border-radius: 30px;
  width: 300px;
  height: 20px;
  padding-left: 10px;
  margin: 10px 20px 0px 40px;
`;

const CheckBox = styled.div`
  display: flex;
  margin-left: 43px;

  > .private {
    font-family: "mainL";
    font-size: 8px;
    margin: 3px 0px 3px 0px;
  }
`;

const StyledInput = styled.input`
  color: var(--green);
  appearance: none;
  width: 8px;
  height: 8px;
  border: 0.5px solid gainsboro;
  border-radius: 0.35rem;
  &:checked {
    border-color: transparent;
    background-image: url("data:image/svg+xml,%3csvg viewBox='0 0 16 16' fill='white' xmlns='http://www.w3.org/2000/svg'%3e%3cpath d='M5.707 7.293a1 1 0 0 0-1.414 1.414l2 2a1 1 0 0 0 1.414 0l4-4a1 1 0 0 0-1.414-1.414L7 8.586 5.707 7.293z'/%3e%3c/svg%3e");
    background-size: 100% 100%;
    background-position: 50%;
    background-repeat: no-repeat;
    background-color: var(--green);
  }
`;
