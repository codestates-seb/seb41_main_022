import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import { useEffect, useState } from "react";

import Bell from "../assets/Bell.svg";
import axios from "axios";

const Header = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [isLogin, setIsLogin] = useState(false);
  const [userImg, setUserImg] = useState<string | undefined>();
  const navigate = useNavigate();
  const googleserverURL =
    "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/google";
  const handleLogin = () => {
    window.location.assign(googleserverURL);
  };

  const handleLogout = () => {
    removeCookie("token");
    setIsLogin(false);
    navigate("/");
    window.location.reload();
  };

  useEffect(() => {
    if (cookies.token) {
      axios
        .get(
          "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/user/image",
          {
            headers: {
              "access-Token": cookies.token.accessToken,
            },
          }
        )
        .then((res) => {
          setUserImg(res.data.data.imgUrl);
          setIsLogin(true);
        });
    }
  }, [cookies.token]);

  return (
    <>
      <HeaderWrapper>
        <div className="header-logo" onClick={() => navigate("/")}>
          Stu<span>d</span>y Tree
        </div>
        {isLogin ? (
          <ItemWrapper>
            <div className="imgWrapper" onClick={() => navigate("/user")}>
              <img src={userImg} />
            </div>
            <img className="bell" src={Bell} />
            <WhiteButton onClick={() => navigate("/study-hall/main")}>
              My Study
            </WhiteButton>
            {/*<WhiteButton onClick={getToken}>Log out</WhiteButton>*/}
            {/*<WhiteButton onClick={() => `location.href=${googleURL}`} >Log out</WhiteButton>*/}
            <WhiteButton onClick={() => handleLogout()}>Log out</WhiteButton>
          </ItemWrapper>
        ) : (
          <ItemWrapper>
            <WhiteButton onClick={() => handleLogin()}>Log in</WhiteButton>
            <WhiteButton onClick={() => handleLogin()}>Sign up</WhiteButton>
          </ItemWrapper>
        )}
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
