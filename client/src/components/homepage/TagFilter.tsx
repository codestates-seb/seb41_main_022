import { useState } from "react";
import styled from "styled-components";

import Tags from "./Tags";

const TagFilter = () => {
  const [isOpen, setIsOpen] = useState(false);

  const handleClick = () => {
    setIsOpen(!isOpen);
  };

  return (
    <TagFilterWrapper>
      <Tags />
      <div className="filterWrapper">
        {isOpen ? (
          <FilterOpen onClick={handleClick}>
            <span>필터</span>
            <span className="triangle">{isOpen ? "▲" : "▼"}</span>
          </FilterOpen>
        ) : (
          <Filter onClick={handleClick}>
            <span>필터</span>
            <span className="triangle">{isOpen ? "▲" : "▼"}</span>
          </Filter>
        )}
        {isOpen && (
          <ul className="dropdown">
            <li className="menu">최신순</li>
            <li className="menu">이름순</li>
            <li className="menu">대충정렬</li>
          </ul>
        )}
      </div>
    </TagFilterWrapper>
  );
};

const TagFilterWrapper = styled.div`
  width: 90%;
  display: flex;
  margin-top: 15px;

  //모바일
  @media screen and (max-width: 768px) {
    padding-left: calc(100% - 180px - 60px);
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1200px) {
    padding-left: calc(100% - 320px - 80px);
  }
  // 웹
  @media screen and (min-width: 1200px) {
    padding-left: calc(100% - 320px - 130px);
  }

  > .filterWrapper {
    //모바일
    @media screen and (max-width: 768px) {
      margin-left: 20px;
    }
    //태블릿
    @media screen and (min-width: 768px) and (max-width: 1200px) {
      margin-left: 65px;
    }
    // 웹
    @media screen and (min-width: 1200px) {
      margin-left: 140px;
    }

    > .dropdown {
      width: 100px;
      background-color: var(--beige-00);
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      border-bottom-right-radius: var(--radius-20);
      border-bottom-left-radius: var(--radius-20);
      > li {
        width: 90px;
        text-align: center;
        height: 30px;
        display: flex;
        align-items: center;
        justify-content: center;
        border-top: 2px dotted black;
        padding-bottom: 4px;
        :hover {
          cursor: pointer;
        }
      }
    }
  }
`;
const Filter = styled.div`
  width: 100px;
  height: 24px;
  background-color: var(--beige-00);
  border-radius: var(--radius-30);
  color: #000000;
  display: flex;
  align-items: center;
  justify-content: center;
  :hover {
    cursor: pointer;
  }
  > span {
    margin-left: 18px;
  }
  > .triangle {
    font-size: 12px;
  }
`;
const FilterOpen = styled.div`
  width: 100px;
  height: 24px;
  background-color: var(--beige-00);
  border-top-right-radius: var(--radius-20);
  border-top-left-radius: var(--radius-20);
  color: #000000;
  display: flex;
  align-items: center;
  justify-content: center;
  :hover {
    cursor: pointer;
  }
  > span {
    margin-left: 18px;
  }
  > .triangle {
    font-size: 12px;
  }
`;

export default TagFilter;
