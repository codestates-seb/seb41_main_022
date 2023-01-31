import styled from "styled-components";

import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import { useEffect, useState } from "react";
import axios from "axios";

import Bell from "../assets/Bell.svg";
import HeaderDropDown from "./HeaderDropDown";
import LoginStore from "../util/zustandLogin";

interface UserData {
  userId: number;
  username: string;
  imgUrl: string;
}

const Header = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token", "userData"]);
  const [isReady, setIsReady] = useState<string | undefined>(undefined);
  const { isLogin, setIsLogin } = LoginStore();
  const [userData, setUserData] = useState<UserData | undefined>();
  const [scroll, setScroll] = useState(false);
  const navigate = useNavigate();

  const googleserverURL =
    process.env.REACT_APP_API_URL + "/oauth2/authorization/google";
  const handleLogin = () => {
    window.location.assign(googleserverURL);
  };

  const handleLogout = () => {
    removeCookie("token");
    removeCookie("userData");

    setIsLogin(false);
    navigate("/");
  };
  const handleScroll = () => {
    if (window.scrollY >= 100) {
      setScroll(true);
    } else {
      setScroll(false);
    }
  };
  useEffect(() => {
    if (cookies.token) {
      setIsLogin(true);
    }
    window.addEventListener("scroll", handleScroll);
    return () => {
      window.removeEventListener("scroll", handleScroll); //clean up
    };
  }, []);
  useEffect(() => {
    if (cookies.token) {
      setIsReady("ready");
    }
  }, [cookies.token]);

  useEffect(() => {
    if (isReady) {
      axios
        .get(process.env.REACT_APP_API_URL + "/user", {
          headers: {
            "access-Token": cookies.token.accessToken,
            "refresh-Token": cookies.token.refreshToken,
          },
        })
        .then((res) => {
          setUserData(res.data.data);

          setCookie(
            "userData",
            {
              userId: res.data.data.userId,
            },
            { path: "/" }
          );
          setIsLogin(true);
        });
    }
  }, [isReady]);
  return (
    <>
      <HeaderWrapper className={scroll ? "opacityTrue" : ""}>
        <div
          className="header-logo"
          onClick={() => {
            navigate("/");
          }}
        >
          Stu<span>d</span>y Tree
        </div>
        {isLogin ? (
          <ItemWrapper>
            <div className="imgWrapper" onClick={() => navigate("/user")}>
              {userData && <img src={userData?.imgUrl} />}
            </div>
            {/* <img className="bell" src={Bell} /> */}
            <HeaderDropDown />
            <WhiteButton onClick={() => handleLogout()}>Log out</WhiteButton>
          </ItemWrapper>
        ) : (
          <ItemWrapper>
            <WhiteButton onClick={() => handleLogin()}>Log in</WhiteButton>
            <RedButton onClick={() => handleLogin()}>Sign up</RedButton>
          </ItemWrapper>
        )}
      </HeaderWrapper>
    </>
  );
};

export default Header;

const HeaderWrapper = styled.header`
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  background-color: var(--green);
  z-index: 1000;
  padding: 0 30px;
  transition: 0.3s all;
  &.opacityTrue {
    transition: 0.3s all;
    background-color: rgba(29, 47, 39, 0.8);
  }
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
    width: 30px;
    height: 30px;
    border-radius: var(--radius-10);
    margin-right: 8px;
    background-color: white;
    > img {
      width: 30px;
      height: 30px;
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
const RedButton = styled.button`
  color: var(--beige-00);
  min-width: 55px;
  font-size: 12px;
  background-color: var(--red-00);
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
