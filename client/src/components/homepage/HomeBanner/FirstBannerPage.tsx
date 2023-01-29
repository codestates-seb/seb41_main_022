import styled from "styled-components";

import firstPage from "../../.././assets/firstPage.svg";

const FirstBannerPage = () => {
  return (
    <TitleBox>
      <TitleWrap>
        <Title>
          Stu<strong>d</strong>y Tree
        </Title>
        <Subtitle>
          <strong>너</strong>와&nbsp;<strong>나</strong>의&nbsp; 스
          <strong>터</strong>디
        </Subtitle>
      </TitleWrap>
    </TitleBox>
  );
};
export default FirstBannerPage;

const TitleBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: absolute;
  justify-content: center;
  align-items: center;
  background-image: url(${firstPage});
  background-size: cover;
  background-position: center;
`;
const TitleWrap = styled.div``;

const Title = styled.div`
  font-size: 150px;
  font-family: "logo";
  color: var(--logo);
  letter-spacing: -3px;
  margin-bottom: 50px;
  > strong {
    color: var(--red-00);
    font-family: "logo";
  }
  //모바일
  @media screen and (max-width: 768px) {
    font-size: 50px;
    align-items: center;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1000px) {
    font-size: 80px;
    font-family: "logo";
    color: var(--logo);
    letter-spacing: -3px;
    margin-bottom: 50px;
  }
  // 웹
  @media screen and (min-width: 1000px) {
  }
`;
const Subtitle = styled.div`
  display: flex;
  font-family: "MainM";
  font-size: 20px;
  color: var(--logo);
  justify-content: center;
  > strong {
    color: #c62424;
    margin: 0px, 10px, 0px, 10px;
  }
`;
