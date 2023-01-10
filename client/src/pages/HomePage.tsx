import React from "react";
import styled from "styled-components";

import Header from "../components/Header";
import Recruitment from "../components/homepage/Recruitment";
import WeekBar from "../components/WeekBar";

const HomePage = () => {
  return (
    <>
      <HomepageWrapper>
        <Header />
        <div className="test">HomePage</div>
      </HomepageWrapper>
    </>
  );
};

// 전체 감싸는 태그
const HomepageWrapper = styled.div`
  background-color: var(--green);
  > .test {
    color: white;
  }
`;

export default HomePage;
