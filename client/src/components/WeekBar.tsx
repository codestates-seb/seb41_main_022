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
  width: 168px;
  height: 30;
  background-color: var(--green);
  border-radius: 40px;
  padding: 1px;

  * {
    font-size: 12px !important ;
    color: var(--beige-00) !important ;
  }
  > .border {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    .active {
      background-color: var(--beige-00);
      color: var(--green) !important ;
    }
  }
`;
//요일 들어가는 div
const WeekBlock = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 20px;
  height: 20px;
  margin: 2px;
  background-color: var(--green);
  border-radius: 10px;
  color: var(--gray-00);
  text-align: center !important;
`;
