import styled from "styled-components";

import thirdPage from "../../.././assets/thirdPage.svg";
const ThirdBannerPage: React.FC<{ scrollToStudies: () => void }> = ({
  scrollToStudies,
}) => {
  return (
    <ThirdBox>
      <ThirdWrap>
        <Title>
          나와 <strong>같은</strong> <br />
          사람이 있을까<strong>?</strong>
        </Title>
        <button onClick={scrollToStudies}>
          <strong>스터디</strong> 둘러보기
        </button>
      </ThirdWrap>
    </ThirdBox>
  );
};

export default ThirdBannerPage;

const ThirdBox = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  position: absolute;
  justify-content: center;
  align-items: center;
  background-image: url(${thirdPage});
  background-size: cover;
  background-position: center;
`;
const ThirdWrap = styled.div`
  display: flex;
  /* flex-direction: column; */
  width: 1100px;
  justify-content: space-between;
  > button {
    display: flex;
    font-size: 20px;
    width: 150px;
    height: 100px;
    background-color: transparent;
    border-color: transparent;
    border: none;
    color: var(--logo);
    margin-top: 250px;
    float: right;
    > strong {
      color: var(--red-00);
    }
  }
`;
const Title = styled.div`
  text-align: left;
  color: var(--logo);
  font-size: 100px;
  letter-spacing: -3px;
  > strong {
    color: var(--red-00);
  }
`;
