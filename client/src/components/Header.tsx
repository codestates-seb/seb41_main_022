import styled from "styled-components";

import Bell from "../assets/Bell.svg";

const HeaderWrapper = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
  background-color: var(--green);
  padding: 0 30px 0 30px;

  > .header-logo {
    font-family: "NovaCut";
    color: var(--logo);
    font-size: 50px;
    letter-spacing: -6%;
    cursor: pointer;
    > span {
      color: var(--red-00);
    }
  }
`;

const ItemWrapper = styled.div`
  display: flex;
  align-items: center;

  > img {
    width: 50px;
    height: 50px;
    border-radius: var(--radius-10);
    margin-right: 8px;
    cursor: pointer;
  }
  > .imgWrapper {
    width: 50px;
    height: 50px;
    border-radius: var(--radius-10);
    margin-right: 8px;
    background-color: white;
    > img {
      width: 50px;
      height: 50px;
      border-radius: var(--radius-10);
      cursor: pointer;
    }
  }
  > .bell {
    width: 28px;
    height: 28px;
    margin-right: 8px;
    cursor: pointer;
  }
`;

// 마이 스터디, 로그인, 로그아웃 버튼
const WhiteButton = styled.button`
  font-size: 14px;
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

const Header = () => {
  return (
    <>
      <HeaderWrapper>
        <div className="header-logo">
          Stu<span>d</span>y Tree
        </div>
        <ItemWrapper>
          <div className="imgWrapper">
            <img src="https://mystickermania.com/cdn/stickers/cartoons/pokemon-ditto-you-can-be-anything-512x512.png" />
          </div>
          <img className="bell" src={Bell} />
          <WhiteButton>My Study</WhiteButton>
          <WhiteButton>Log out</WhiteButton>
        </ItemWrapper>
      </HeaderWrapper>
    </>
  );
};

export default Header;
