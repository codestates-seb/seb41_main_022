import styled from "styled-components";

interface WeekbarProps {
  dayOfWeek: string[];
}

const WeekBar = ({ dayOfWeek }: WeekbarProps) => {
  return (
    <WeekWrapper>
      <div className="border">
        <WeekBlock className={dayOfWeek.includes("MON") ? "active" : undefined}>
          월
        </WeekBlock>
        <WeekBlock className={dayOfWeek.includes("TUE") ? "active" : undefined}>
          화
        </WeekBlock>
        <WeekBlock className={dayOfWeek.includes("WED") ? "active" : undefined}>
          수
        </WeekBlock>
        <WeekBlock className={dayOfWeek.includes("THU") ? "active" : undefined}>
          목
        </WeekBlock>
        <WeekBlock className={dayOfWeek.includes("FRI") ? "active" : undefined}>
          금
        </WeekBlock>
        <WeekBlock className={dayOfWeek.includes("SAT") ? "active" : undefined}>
          토
        </WeekBlock>
        <WeekBlock className={dayOfWeek.includes("SUN") ? "active" : undefined}>
          일
        </WeekBlock>
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
    color: var(--beige-00);
  }
  > .border {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 2px;
    .active {
      background-color: var(--beige-00);
      color: var(--green);
    }
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
`;
