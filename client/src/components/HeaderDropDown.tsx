import Dropdown from "react-bootstrap/Dropdown";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import { IoMdArrowDropdown, IoMdArrowDropup } from "react-icons/io";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const HeaderDropDown = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [myStudyArr, setMyStudyArr] = useState([]);
  const navigate = useNavigate();
  const getMyStudy = (url: string, token: object) => {
    return axios.get(url + "/study/user", {
      headers: token,
    });
  };
  useEffect(() => {
    if (cookies.token) {
      getMyStudy(URL, {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      }).then((res) => setMyStudyArr(res.data.data.studies));
    }
  }, []);
  return (
    <Wrapper className="bootstrapCss">
      <Dropdown onClick={() => setIsOpen(!isOpen)}>
        <Dropdown.Toggle id="dropdown-autoclose-true">
          My Study {isOpen ? <IoMdArrowDropup /> : <IoMdArrowDropdown />}
        </Dropdown.Toggle>
        {isOpen && (
          <Dropdown.Menu>
            {myStudyArr &&
              myStudyArr.map((el: { studyId: string; teamName: string }) => (
                <div
                  onClick={() => {
                    setIsOpen(false);
                    console.log(el.studyId);
                    navigate(`/study-hall/main/${el.studyId}`);
                  }}
                >
                  <Dropdown.Item>{el.teamName}</Dropdown.Item>
                </div>
              ))}
          </Dropdown.Menu>
        )}
      </Dropdown>
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
