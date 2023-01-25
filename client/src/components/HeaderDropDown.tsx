import Dropdown from "react-bootstrap/Dropdown";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { IoMdArrowDropdown, IoMdArrowDropup } from "react-icons/io";

const URL = process.env.REACT_APP_API_URL;

const HeaderDropDown = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [myStudyArr, setMyStudyArr] = useState([]);
  const getMyStudy = (token: object) => {
    return axios.get(URL + "/study/user", {
      headers: token,
    });
  };
  useEffect(() => {
    if (cookies.token) {
      getMyStudy({
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      }).then((res) => setMyStudyArr(res.data.data.studies));
    }
  }, []);
  return (
    <Wrapper>
      <div onClick={() => setIsOpen(!isOpen)} className={isOpen ? "show" : ""}>
        <button id="dropdown-autoclose-true">
          My Study {isOpen ? <IoMdArrowDropup /> : <IoMdArrowDropdown />}
        </button>
        {isOpen && (
          <div className="dropdown-menu">
            {myStudyArr &&
              myStudyArr.map((el: { studyId: string; teamName: string }) => (
                <div>
                  <a href={`/study-hall/main/${el.studyId}`}>{el.teamName}</a>
                </div>
              ))}
          </div>
        )}
      </div>
    </Wrapper>
  );
};
export default HeaderDropDown;
const Wrapper = styled.div`
  .show {
    button {
      background-color: var(--green);
      color: var(--gray-10);
      border: 1px solid var(--gray-10);
    }
  }
  button {
    min-width: 55px;
    font-size: 12px;
    background-color: var(--gray-10);
    border-radius: var(--radius-20);
    margin-right: 8px;
    height: 28px;
    border: 1px solid var(--green);
    cursor: pointer;
    color: var(--green);
    :hover {
      background-color: var(--green);
      color: var(--gray-10);
      border: 1px solid var(--gray-10);
    }
  }
  .dropdown-menu {
    position: absolute;
    margin-top: 17px;
    min-width: 80px;
    border-radius: 3px;
    padding: 5px 0;
    z-index: 999;
    background-color: var(--gray-10);
    a {
      text-decoration: none;
      color: var(--green-00);
      font-size: 12px;
    }
    > div {
      padding: 2px 7px;
      :hover {
        background-color: #bfbfbf;
      }
    }
  }
`;
