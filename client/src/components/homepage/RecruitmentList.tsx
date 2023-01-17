import styled from "styled-components";
import axios, { AxiosResponse } from "axios";
import { useState, useEffect } from "react";

import Data from "../../util/dummyData";
import Recruitment from "./Recruitment";
import HomeStore from "../../util/zustandHome";

interface Data {
  data: any;
}

const RecruitmentList = () => {
  const { tags, filter, search, recruitmentData, fetch, setRecruitment } =
    HomeStore();
  const getRecruitmentData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url);
  };

  // 초기 데이터 요청
  useEffect(() => {
    getRecruitmentData(
      `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/first-cards?page=${1}&size=${10}`
    ).then((res) => {
      setRecruitment(res.data.data);
    });
  }, []);

  // 필터링 api요청
  useEffect(() => {
    console.log(fetch(tags, filter, search));
  }, [tags, filter, search]);

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
