import styled from "styled-components";
import { useEffect, useState } from "react";
import { useCookies } from "react-cookie";
import { IoMdArrowDropdown, IoMdArrowDropup } from "react-icons/io";
import DropdownStore from "../util/zustandDropdown";
import { createStudyStore } from "../util/zustandCreateStudy";

const HeaderDropDown = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const [myStudyArr, setMyStudyArr] = useState([]);
  const { dropdownGet, dropdownData } = DropdownStore();
  const { studyId } = createStudyStore();
  useEffect(() => {
    if (cookies.token) {
      dropdownGet({
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      });
    }
  }, [studyId]);
  return (
    <Wrapper>
      <div onClick={() => setIsOpen(!isOpen)} className={isOpen ? "show" : ""}>
        <button id="dropdown-autoclose-true">
          My Study {isOpen ? <IoMdArrowDropup /> : <IoMdArrowDropdown />}
        </button>
        {isOpen && (
          <div className="dropdown-menu">
            {dropdownData &&
              dropdownData.map((el: { studyId: string; teamName: string }) => (
                <a href={`/study-hall/main/${el.studyId}`} key={el.studyId}>
                  <div className="hoverDiv">
                    {el.teamName.length > 10
                      ? el.teamName.slice(0, 9) + "..."
                      : el.teamName}
                  </div>
                </a>
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
    box-shadow: 1px 2px 5px black;
    a {
      text-decoration: none;
      color: var(--green-00);
      font-size: 12px;
    }
    .hoverDiv {
      padding: 2px 7px;
      :hover {
        background-color: #bfbfbf;
      }
    }
  }
`;
