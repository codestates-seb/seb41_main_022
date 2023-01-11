import styled from "styled-components";

const WeekBar = () => {
  return (
    <WeekWrapper>
      <div className="border">
        <WeekBlock>월</WeekBlock>
        <WeekBlock>화</WeekBlock>
        <WeekBlock>수</WeekBlock>
        <WeekBlock>목</WeekBlock>
        <WeekBlock>금</WeekBlock>
        <WeekBlock>토</WeekBlock>
        <WeekBlock>일</WeekBlock>
      </div>
    </WeekWrapper>
  );
};

export default WeekBar;

const WeekWrapper = styled.main`
  max-width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  * {
    font-size: 12px;
  }
  > .border {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 2px;
  }
`;
//요일 들어가는 div
const WeekBlock = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "mainB";
  width: 18px;
  height: 18px;
  margin: 1px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  color: var(--gray-00);
  text-align: center;
  :hover {
    background-color: var(--beige-00);
    color: var(--green);
  }
`;
