import styled from "styled-components";

import Data from "../../util/dummyData";
import Recruitment from "./Recruitment";

const RecruitmentList = () => {
  return (
    <RecruitmentWrapper>
      {Data.data.map((el) => (
        <Recruitment
          key={el.studyId}
          teamName={el.teamName}
          summary={el.summary}
        />
      ))}
    </RecruitmentWrapper>
  );
};

const RecruitmentWrapper = styled.section`
  display: flex;
  flex-wrap: wrap;
  height: auto;

  //모바일
  @media screen and (max-width: 768px) {
    width: 321px;
    align-items: center;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1200px) {
    width: 750px;
  }
  // 웹
  @media screen and (min-width: 1200px) {
    width: 1100px;
  }
`;

export default RecruitmentList;
