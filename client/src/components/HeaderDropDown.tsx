import Dropdown from "react-bootstrap/Dropdown";
<<<<<<< HEAD
=======
import "bootstrap/dist/css/bootstrap.min.css";
>>>>>>> 13a5a1da0fa44653a039015f8e4ee6c54595cba9
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
<<<<<<< HEAD
import { IoMdArrowDropdown, IoMdArrowDropup } from "react-icons/io";
=======
>>>>>>> 13a5a1da0fa44653a039015f8e4ee6c54595cba9

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const HeaderDropDown = () => {
<<<<<<< HEAD
  const [isOpen, setIsOpen] = useState(false);
=======
>>>>>>> 13a5a1da0fa44653a039015f8e4ee6c54595cba9
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
<<<<<<< HEAD
    <Wrapper className="bootstrapCss">
      <Dropdown onClick={() => setIsOpen(!isOpen)}>
        <Dropdown.Toggle id="dropdown-autoclose-true">
          My Study {isOpen ? <IoMdArrowDropdown /> : <IoMdArrowDropup />}
        </Dropdown.Toggle>
        {isOpen && (
          <Dropdown.Menu>
            {myStudyArr &&
              myStudyArr.map((el: { studyId: string; teamName: string }) => (
                <div onClick={() => navigate(`/study-hall/main/${el.studyId}`)}>
                  <Dropdown.Item>{el.teamName}</Dropdown.Item>
                </div>
              ))}
          </Dropdown.Menu>
        )}
=======
    <Wrapper>
      <Dropdown>
        <Dropdown.Toggle id="dropdown-autoclose-true">My Study</Dropdown.Toggle>
        <Dropdown.Menu>
          {myStudyArr &&
            myStudyArr.map((el: { studyId: string; teamName: string }) => (
              <div onClick={() => navigate(`/study-hall/main/${el.studyId}`)}>
                <Dropdown.Item>{el.teamName}</Dropdown.Item>
              </div>
            ))}
        </Dropdown.Menu>
>>>>>>> 13a5a1da0fa44653a039015f8e4ee6c54595cba9
      </Dropdown>
    </Wrapper>
  );
};
export default HeaderDropDown;
const Wrapper = styled.div`
<<<<<<< HEAD
=======
  .dropdown-menu {
    margin-top: 15px;
  }
>>>>>>> 13a5a1da0fa44653a039015f8e4ee6c54595cba9
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
<<<<<<< HEAD
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
=======
>>>>>>> 13a5a1da0fa44653a039015f8e4ee6c54595cba9
`;
