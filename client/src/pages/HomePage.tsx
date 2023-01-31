import styled from "styled-components";
import { useEffect, useRef } from "react";

import RecruitmentList from "../components/homepage/RecruitmentList";
import Banner from "../components/homepage/Banner";
import CreateStudyButton from "../components/homepage/CreateStudyButton";
import Search from "../components/homepage/Search";
import TagFilter from "../components/homepage/TagFilter";

const HomePage: React.FC = () => {
  const myRef = useRef<HTMLDivElement>(null);
  const studiesRef = useRef<HTMLDivElement>(null);

  const scrollToCreate = () => {
    if (myRef.current) {
      window.scrollTo(0, myRef.current.offsetTop);
    }
  };

  const scrollToStudies = () => {
    if (myRef.current) {
      window.scrollTo(0, myRef.current.offsetTop);
    }
  };

  useEffect(() => {
    window.onbeforeunload = function pushRefresh() {
      window.scrollTo(0, 0);
    };
  }, []);

  return (
    <>
      <HomepageWrapper>
        <Banner
          scrollToCreate={scrollToCreate}
          scrollToStudies={scrollToStudies}
        />
        <ContentWrapper>
          <CreateStudyButton scrollRef={myRef} />
          <Search />
          <TagFilter />
          <RecruitmentList scrollRef={studiesRef} />
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
  overflow-x: hidden;
`;

const ContentWrapper = styled.article`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export default HomePage;
