import styled from "styled-components";
import axios from "axios";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

interface StudyHeader {
  teamName: string;
  openClose: boolean;
}

const StudyHallHead = () => {
  const [studyData, setStudyData] = useState<StudyHeader>();
  const { studyId } = useParams();
  useEffect(() => {
    axios
      .get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/header`
      )
      .then((res) => {
        setStudyData(res.data.data);
      });
  }, []);
  return (
    <HeaderWrapper>
      <div className="padding" />
      <TopDiv>
        <div className="studyName">{studyData && studyData.teamName}</div>
        <div className="studyHall">study hall</div>
      </TopDiv>
      <FlexDiv>
        <span
          className={studyData && studyData.openClose ? "active" : undefined}
        >
          Public
        </span>
        /
        <span
          className={studyData && studyData.openClose ? undefined : "active"}
        >
          Private
        </span>
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
  padding-bottom: 4px;
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
    color: var(--mopo-00);
  }
  > .active {
    color: var(--green);
  }
`;
