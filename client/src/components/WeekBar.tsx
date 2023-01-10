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
  width: 200px;
  background-color: var(--green);
  border-radius: var(--radius-30);

  * {
    font-size: 15px;
  }

  > .border {
    margin: 1px;
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
`;
//요일 들어가는 div
const WeekBlock = styled.div`
  font-family: "mainM";
  max-width: 25px;
  max-height: 25px;
  margin: 1px;
  background-color: var(--green);
  padding: 0 5px;
  border-radius: var(--radius-30);
  color: var(--gray-00);
  text-align: center;
  line-height: 22px;
  :hover {
    background-color: var(--beige-00);
    color: var(--green);
  }
`;
