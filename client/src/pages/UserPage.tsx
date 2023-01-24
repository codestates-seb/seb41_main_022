import axios from "axios";
import styled from "styled-components";
import { useState } from "react";
//구분선
import Profile from "../components/userpage/Profile";
import MyStudy from "../components/userpage/MyStudy";
import MyStudyList from "../components/userpage/MyStudyList";
import { useCookies } from "react-cookie";

const UserPage = () => {
  const [cookies] = useCookies(["token"]);
  const [isOpenAgreePage, setIsOpenAgreePage] = useState(false);
  console.log(isOpenAgreePage);

  const withdraw = () => {
    axios.delete(
      "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/user",
      {
        headers: {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        },
      }
    );
  };

  const handleClickWithdraw = () => {
    setIsOpenAgreePage(!isOpenAgreePage);
  };
  return (
    <Main>
      <Container>
        <Profile />
        <h3 className="title">My Study</h3>
        <MyStudyList />
        <h3 className="title">Tags</h3>
        <h3 className="title">나무나무</h3>
        <button onClick={handleClickWithdraw} className="button" type="submit">
          회원탈퇴
        </button>
        {isOpenAgreePage ? <WithdrawAgree>히히</WithdrawAgree> : null}
      </Container>
    </Main>
  );
};

//전체
const Main = styled.div`
  background-color: var(--green);
  height: 100vh;
  * {
    font-family: "mainM";
    color: var(--green);
  }
`;

//전체박스
const Container = styled.div`
  background-color: var(--beige-00);
  width: 1024px;
  height: 183px;
  margin: 0px auto;

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
    margin-left: 700px;
    font-size: 12px;
    color: #c0c0c0;
    background-color: var(--gray-10);
    border-radius: var(--radius-20);
    margin-right: 8px;
    height: 28px;
    border: none;
    cursor: pointer;
    :hover {
      background-color: var(--gray-20);
      transition-duration: 0.5s;
    }
  }
`;

const WithdrawAgree = styled.div`
  width: 70%;
  height: 40px;
  margin-left: 120px;
  margin-top: 20px;
  border: 1px orange solid;
`;

export default UserPage;
