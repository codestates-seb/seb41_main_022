import styled from "styled-components";
import { useNavigate } from "react-router-dom";

import Bell from "../assets/Bell.svg";
import axios from "axios";

const Header = () => {
  const googleLocalURL = "http://localhost:8080/oauth2/authorization/google";
  const googleserverURL =
    "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google";
  function moveURL() {
    window.location.assign(googleserverURL);
  }

  const navigate = useNavigate();
  const getToken = () => {
    axios
      .post("http://localhost:8080/oauth2/authorization/google")
      .then(function (response) {
        console.log(response);
        alert("로그인 되었습니다");
        navigate("/");
      });
  };
  return (
    <>
      <HeaderWrapper>
        <div className="header-logo" onClick={() => navigate("/")}>
          Stu<span>d</span>y Tree
        </div>
        <ItemWrapper>
          <WhiteButton>Log in</WhiteButton>
          <WhiteButton>Sign up</WhiteButton>
        </ItemWrapper>
        <ItemWrapper>
          <div className="imgWrapper" onClick={() => navigate("/user")}>
            <img src="https://mystickermania.com/cdn/stickers/cartoons/pokemon-ditto-you-can-be-anything-512x512.png" />
          </div>
          <img className="bell" src={Bell} />
          <WhiteButton onClick={() => navigate("/study-hall/main")}>
            My Study
          </WhiteButton>
          {/*<WhiteButton onClick={getToken}>Log out</WhiteButton>*/}
          {/*<WhiteButton onClick={() => `location.href=${googleURL}`} >Log out</WhiteButton>*/}
          <WhiteButton onClick={() => moveURL()}>Log out</WhiteButton>
        </ItemWrapper>
      </HeaderWrapper>
    </>
  );
};

export default Header;

const HeaderWrapper = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  background-color: var(--green);
  padding: 0 30px 0 30px;

  > .header-logo {
    font-family: "logo";
    color: var(--logo);
    font-size: 35px;
    letter-spacing: -3px;
    cursor: pointer;
    > span {
      color: var(--red-00);
    }
  }
`;

const ItemWrapper = styled.div`
  display: flex;
  align-items: center;

  > .imgWrapper {
    width: 35px;
    height: 35px;
    border-radius: var(--radius-10);
    margin-right: 8px;
    background-color: white;
    > img {
      width: 35px;
      height: 35px;
      border-radius: var(--radius-10);
      cursor: pointer;
    }
  }
  > .bell {
    width: 24px;
    height: 24px;
    margin-right: 8px;
    cursor: pointer;
  }
`;

// 마이 스터디, 로그인, 로그아웃 버튼
const WhiteButton = styled.button`
  min-width: 55px;
  font-size: 12px;
  background-color: var(--gray-10);
  border-radius: var(--radius-20);
  margin-right: 8px;
  height: 28px;
  border: 1px solid var(--green);
  cursor: pointer;

  :hover {
    background-color: var(--green);
    color: var(--gray-10);
    border: 1px solid var(--gray-10);
  }
`;
