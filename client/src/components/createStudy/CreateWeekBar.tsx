import styled from "styled-components";
import { useEffect, useState } from "react";

interface Props {
  week: dayType;
  setWeek?: any;
}
interface dayType {
  [key: string]: boolean;
}
const CreateWeekBar = ({ week, setWeek }: Props) => {
  const [click, setClick] = useState(false);
  const weekFunc = (day: string) => {
    week[day] = !week[day];
    setWeek(week);
    setClick(!click);
  };
  return (
    <WeekWrapper>
      <div className="border">
        <WeekBlock
          onClick={() => weekFunc("mon")}
          className={week.mon ? "active" : undefined}
        >
          월
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("tue")}
          className={week.tue ? "active" : undefined}
        >
          화
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("wen")}
          className={week.wen ? "active" : undefined}
        >
          수
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("thu")}
          className={week.thu ? "active" : undefined}
        >
          목
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("fri")}
          className={week.fri ? "active" : undefined}
        >
          금
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("sat")}
          className={week.sat ? "active" : undefined}
        >
          토
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("sun")}
          className={week.sun ? "active" : undefined}
        >
          일
        </WeekBlock>
      </div>
    </WeekWrapper>
  );
};

export default CreateWeekBar;

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
    > .active {
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
  :hover {
    background-color: var(--beige-00);
    color: var(--green);
  }
`;
