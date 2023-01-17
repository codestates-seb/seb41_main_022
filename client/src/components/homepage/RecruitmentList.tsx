import styled from "styled-components";
import axios, { AxiosResponse } from "axios";
import { useState, useEffect } from "react";

import Data from "../../util/dummyData";
import Recruitment from "./Recruitment";

interface Data {
  data: any;
}

interface AxiosData {
  content: string;
  dayOfWeek: string[];
  image: string;
  leaderId: number;
  notice?: null;
  openClose: boolean;
  procedure: boolean;
  requester: [];
  startDate: string;
  studyId: number;
  summary: string;
  teamName: string;
  want: number;
}

const RecruitmentList = () => {
  const [recruitmentData, setRecruitmentData] = useState<AxiosData[]>();
  const getRecruitmentData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url);
  };

  useEffect(() => {
    getRecruitmentData(
      `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/first-cards?page=${1}&size=${10}`
    ).then((res) => {
      setRecruitmentData(res.data.data);
    });
  }, []);

  return (
    <RecruitmentWrapper>
      {recruitmentData &&
        recruitmentData.map((el) => (
          <Recruitment
            key={el.studyId}
            teamName={el.teamName}
            summary={el.summary}
            dayOfWeek={el.dayOfWeek}
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
