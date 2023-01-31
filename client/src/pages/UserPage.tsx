import styled from "styled-components";
import "react-toastify/dist/ReactToastify.css";

import Profile from "../components/userpage/Profile";
import UserMain from "../components/userpage/UserMain";
import React from "react";

const UserPage = () => {
  return (
    <Main>
      <Container>
        <Profile />
        <UserMain />
      </Container>
    </Main>
  );
};

//전체
const Main = styled.div`
  background-color: var(--green);
  height: auto;
  * {
    font-family: "mainM";
    color: var(--green);
  }
`;

//전체박스
const Container = styled.div`
  background-color: var(--beige-00);
  width: 1024px;
  margin: 0px auto;
  height: auto;
  display: flex;
  flex-direction: column;

  ul,
  li {
    margin: 0;
    padding: 0;
  }
  * {
  }

  //My study, Tag 등 각 title
  > .title {
    margin-left: 100px;
    padding: 20px;
    font-family: "mainEB";
  }
  //회원탈퇴 버튼
  > .button {
    width: 105px;
    height: 10px;
    margin: 0 8px 16px 750px;
    font-size: 12px;
    color: #c0c0c0;
    background-color: var(--gray-10);
    border-radius: var(--radius-20);
    height: 28px;
    border: none;
    cursor: pointer;
    :hover {
      background-color: var(--gray-20);
      transition-duration: 0.5s;
    }
  }

  > .buttonActive {
    background-color: var(--gray-20);
  }
`;

export default UserPage;
