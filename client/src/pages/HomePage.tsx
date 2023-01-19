import styled from "styled-components";
import { useEffect } from "react";

import RecruitmentList from "../components/homepage/RecruitmentList";
import Banner from "../components/homepage/Banner";
import CreateStudyButton from "../components/homepage/CreateStudyButton";
import Search from "../components/homepage/Search";
import TagFilter from "../components/homepage/TagFilter";

const HomePage = () => {
  useEffect(() => {
    window.onbeforeunload = function pushRefresh() {
      window.scrollTo(0, 0);
    };
  }, []);
  return (
    <>
      <HomepageWrapper>
        <Banner />
        <ContentWrapper>
          <CreateStudyButton />
          <Search />
          <TagFilter />
          <RecruitmentList />
        </ContentWrapper>
      </HomepageWrapper>
    </>
  );
};

// 전체 감싸는 태그
const HomepageWrapper = styled.div`
  margin: 0 auto;
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
