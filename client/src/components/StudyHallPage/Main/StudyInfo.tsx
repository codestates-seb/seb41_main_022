import styled from "styled-components";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

import TagReadOnly from "./TagReadOnly";

interface StudyInfoData {
  content: string;
  summary: string;
  tags: string[];
  teamName: string;
}

const StudyInfo = () => {
  const { studyId } = useParams();
  const [studyInfoData, setStudyInfoData] = useState<StudyInfoData>();

  useEffect(() => {
    axios
      .get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/main`
      )
      .then((res) => setStudyInfoData(res.data.data));
  }, []);
  return (
    <InfoWrapper>
      <Info>
        <Title>{studyInfoData && studyInfoData.teamName}</Title>
        <Summary>{studyInfoData && studyInfoData.summary}</Summary>
        <TagReadOnly tags={studyInfoData?.tags} />
        <Content>{studyInfoData && studyInfoData.content}</Content>
      </Info>
    </InfoWrapper>
  );
};

export default StudyInfo;

//전체 배경색, 글로벌 폰트사이즈 및 색상
const InfoWrapper = styled.div`
  * {
    font-family: "mainM", Ariel;
    font-size: 14px;
    color: var(--green);
  }
  background-color: var(--beige-00);
`;

//박스 크기, 중앙 정렬
const Info = styled.div`
  width: 460px;
  margin: 0px auto;
  /* padding-top: 100px; */
`;

//Title 글씨크기
const Title = styled.div`
  font-family: "mainB";
  font-size: 35px;
  padding-top: 25px;
`;

const Summary = styled.div`
  width: 280px;
  padding-top: 50px;
  padding-bottom: 50px;
  font-size: 16px;
  font-family: "mainB";
`;

const Tag = styled;

const Content = styled.div`
  width: 350px;
  padding-top: 30px;
  padding-bottom: 50px;
`;
