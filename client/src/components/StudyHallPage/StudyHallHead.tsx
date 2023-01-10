import styled from "styled-components";

const StudyHallHead = () => {
  return (
    <HeaderWrapper>
      <div className="padding" />
      <TopDiv>
        <div className="studyName">Your Study</div>
        <div className="studyHall">study hall</div>
      </TopDiv>
      <FlexDiv>
        <span>Public</span>/<span>Private</span>
      </FlexDiv>
    </HeaderWrapper>
  );
};
export default StudyHallHead;

const HeaderWrapper = styled.header`
  height: 190px;
  background-color: var(--beige-00);
  * {
    font-family: "mainB", Arial;
  }
  .padding {
    padding-top: 100px;
  }
  > div {
    width: 460px;
    margin: 0 auto;
  }
`;
const TopDiv = styled.div`
  width: 460px;
  margin: 0 auto;
  border-bottom: 2px solid var(--green);
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  color: var(--green);
  .studyName {
    font-size: 40px;
  }
  .studyHall {
    font-size: 14px;
    display: flex;
    flex-direction: column-reverse;
  }
`;
//Public Private 관리
const FlexDiv = styled.div`
  padding: 2px;
  display: flex;
  justify-content: end;
  font-size: 12px;
  > span {
    color: var(--gray-20);
    :hover {
      color: var(--green);
    }
  }
`;
