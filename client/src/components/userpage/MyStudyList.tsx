import styled from "styled-components";

import UserData from "../../util/dummyDataUser";
import MyStudy from "./MyStudy";

const MyStudyList = () => {
  return (
    <MyStudyWrapper>
      {UserData.data.study.map((el) => (
        <MyStudy key={el.studyId} teamName={el.teamName} image={el.image} />
      ))}
    </MyStudyWrapper>
  );
};

const MyStudyWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  height: auto;
  margin-left: 10%;
`;

export default MyStudyList;
