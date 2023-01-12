import styled from "styled-components";

const CreateStudyButton = () => {
  return (
    <CreateStudyWrapper>
      <div className="flexWrapper">
        <div className="assign">
          아직 스터디에 가입하지 않았나요?
          <br />
          새로 만들어보세요!
        </div>
        <RedButton>Create Study</RedButton>
      </div>
    </CreateStudyWrapper>
  );
};

const CreateStudyWrapper = styled.div`
  * {
    font-family: "mainM";
  }
  margin-top: 140px;
  background-color: var(--beige-00);
  width: 70%;
  height: 76px;
  border-radius: var(--radius-30);
  display: flex;
  align-items: center;
  justify-content: center;
  @media screen and (max-width: 768px) {
    height: 100px;
  }
  > .flexWrapper {
    width: 80%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    @media screen and (max-width: 768px) {
      width: 90%;
      flex-direction: column;
    }

    > .assign {
      font-size: 20px;
      //작아질때 글씨 잘리는거 생각해보기
      @media screen and (max-width: 768px) {
        width: 200px;
        font-size: 14px;
        text-align: center;
        margin-bottom: 8px;
      }
      @media screen and (min-width: 768px) and (max-width: 1200px) {
        width: 300px;
        font-size: 16px;
      }
    }
  }
`;

const RedButton = styled.button`
  @media screen and (max-width: 768px) {
    width: 180px;
    margin-bottom: 4px;
  }
  width: 240px;
  padding: 3px;
  background-color: var(--red-00);
  color: var(--beige-00);
  border-radius: var(--radius-30);
  font-family: "mainL", Arial;
  font-size: 22px;
  border: none;
  :hover {
    background-color: var(--red-10);
    cursor: pointer;
  }
`;

export default CreateStudyButton;
