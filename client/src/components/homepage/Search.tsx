import styled from "styled-components";

import SearchIcon from "../.././assets/Search.svg";

const Search = () => {
  return (
    <BorderBottomWrapper>
      <div className="searchWrapper">
        <Input placeholder="Search..." />
      </div>
    </BorderBottomWrapper>
  );
};

// 바깥선 표현
const BorderBottomWrapper = styled.div`
  padding-bottom: 20px;
  border-bottom: 4px solid var(--beige-00);
  width: 45%;
  margin-top: 75px;
  * {
    font-family: "mainM";
  }
  > .searchWrapper {
    background-color: var(--beige-00);
    border-radius: var(--radius-30);
    height: 40px;
    padding: 1px 2px 1px 2px;
    display: flex;
    align-items: center;
  }
`;

// 검색창, 돋보기 아이콘
const Input = styled.input`
  outline: none;
  width: 100%;
  height: 38px;
  padding: 0px 5px 0px 32px;
  background-position: 7px 0px;
  box-sizing: border-box;
  background-image: url('data:image/svg+xml,%3Csvg xmlns="http://www.w3.org/2000/svg"  aria-hidden="true" class="s-input-icon s-input-icon__search svg-icon iconSearch" cx="15" cy="15" viewBox="0 -6 30 30" fill="black"%3E%3Cpath d="m18 16.5-5.14-5.18h-.35a7 7 0 1 0-1.19 1.19v.35L16.5 18l1.5-1.5ZM12 7A5 5 0 1 1 2 7a5 5 0 0 1 10 0Z"%3E%3C/path%3E%3C/svg%3E');
  background-repeat: no-repeat;
  background-color: var(--beige-00);
  border: 3px solid var(--green);
  border-radius: var(--radius-30);
`;

export default Search;
