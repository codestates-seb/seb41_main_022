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
  > .flexWrapper {
    width: 80%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    > .assign {
      font-size: 20px;
    }
  }
`;

const RedButton = styled.button`
  width: 240px;
  padding: 3px;
  background-color: var(--red-00);
  color: var(--beige-00);
  border-radius: var(--radius-30);
  font-family: "mainL", Arial;
  font-size: 22px;
  :hover {
    background-color: var(--red-10);
  }
`;

export default CreateStudyButton;
