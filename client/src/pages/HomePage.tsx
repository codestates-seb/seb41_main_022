import React from "react";
import styled from "styled-components";

import RecruitmentList from "../components/homepage/RecruitmentList";
import Banner from "../components/homepage/Banner";
import CreateStudyButton from "../components/homepage/CreateStudyButton";

const HomePage = () => {
  return (
    <>
      <HomepageWrapper>
        <Banner />
        <ContentWrapper>
          <CreateStudyButton />
          <RecruitmentList />
        </ContentWrapper>
      </HomepageWrapper>
    </>
  );
};

// 전체 감싸는 태그
const HomepageWrapper = styled.div`
  margin: 0, auto;
  height: auto;
  width: 100%;
  background-color: var(--green);
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: "mainM";
`;

const ContentWrapper = styled.article`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default HomePage;
