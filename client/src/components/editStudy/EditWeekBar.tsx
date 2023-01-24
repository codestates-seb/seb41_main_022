import styled from "styled-components";
import { useEffect, useState } from "react";
interface PropsType {
  onChange: (...event: any[]) => void;
  error: boolean;
}
interface dayType {
  [key: string]: boolean;
}
const EditWeekbar = ({ onChange, error }: PropsType) => {
  const [dayOfWeek, setDayOfWeek] = useState([]);
  const [week, setWeek] = useState<dayType>({
    MON: false,
    TUE: false,
    WED: false,
    THU: false,
    FRI: false,
    SAT: false,
    SUN: false,
  });
  useEffect(() => {
    onChange(dayOfWeek);
  }, [dayOfWeek]);

  const weekFunc = (day: string) => {
    week[day] = !week[day];
    setWeek(week);
    let temp: any = Object.keys(week).filter((el) => week[el]);
    setDayOfWeek(temp);
  };
  return (
    <WeekWrapper>
      <Border className={error ? "errorBorder" : ""}>
        <WeekBlock
          onClick={() => weekFunc("MON")}
          className={week.MON ? "active" : undefined}
        >
          월
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("TUE")}
          className={week.TUE ? "active" : undefined}
        >
          화
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("WED")}
          className={week.WED ? "active" : undefined}
        >
          수
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("THU")}
          className={week.THU ? "active" : undefined}
        >
          목
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("FRI")}
          className={week.FRI ? "active" : undefined}
        >
          금
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("SAT")}
          className={week.SAT ? "active" : undefined}
        >
          토
        </WeekBlock>
        <WeekBlock
          onClick={() => weekFunc("SUN")}
          className={week.SUN ? "active" : undefined}
        >
          일
        </WeekBlock>
      </Border>
    </WeekWrapper>
  );
};

export default EditWeekbar;

const WeekWrapper = styled.main`
  max-width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  margin: 5px 0;
  * {
    font-size: 12px;
    color: var(--beige-00);
  }
`;
const Border = styled.div`
  border: 1px solid var(--beige-00);
  border-radius: var(--radius-30);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 2px;
  .errorBorder {
    border: 1px solid var(--red-00);
  }
  > .active {
    background-color: var(--beige-00);
    color: var(--green);
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
    cursor: pointer;
  }
`;
