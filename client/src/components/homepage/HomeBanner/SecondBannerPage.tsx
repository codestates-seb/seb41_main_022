import styled from "styled-components";

import secondPage from "../../.././assets/secondPage.svg";

const SecondBannerPage: React.FC<{ scrollToCreate: () => void }> = ({
  scrollToCreate,
}) => {
  return (
    <SecondBox>
      <SecondWrap>
        <button onClick={scrollToCreate}>
          <strong>스터디</strong> 생성하기
        </button>
        <div className="title-wrapper">
          <div></div>
          <SecondTitle>
            나와 같은 <br />
            <strong>사람을</strong> 모으고 싶다면?
          </SecondTitle>
        </div>
      </SecondWrap>
    </SecondBox>
  );
};

export default SecondBannerPage;

const SecondBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: absolute;
  justify-content: center;
  align-items: center;
  background-image: url(${secondPage});
  background-size: cover;
  background-position: center;
`;

const SecondWrap = styled.div`
  display: flex;
  flex-direction: column;
  width: 1100px;

  @media screen and (max-width: 768px) {
    width: 550px;
    align-items: center;
    flex-direction: row;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1000px) {
    font-size: 75px;
    width: 750px;
  }
  // 웹
  @media screen and (min-width: 1000px) {
  }
  > button {
    font-size: 20px;
    width: 150px;
    height: 100px;
    background-color: transparent;
    border-color: transparent;
    border: none;
    color: var(--logo);
    margin-bottom: 150px;
    > strong {
      color: var(--red-00);
    }
  }

  .title-wrapper {
    display: flex;
    justify-content: flex-end;
  }
`;
const SecondTitle = styled.div`
  text-align: right;
  color: var(--logo);
  font-size: 100px;
  letter-spacing: -3px;
  > strong {
    color: var(--red-00);
  }
  @media screen and (max-width: 768px) {
    font-size: 35px;
    align-items: center;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1000px) {
    font-size: 65px;
  }
  // 웹
  @media screen and (min-width: 1000px) {
  }
`;
