import React from "react";
import styled from "styled-components";
import RecruitmentList from "../components/homepage/RecruitmentList";

const HomePage = () => {
  return (
    <>
      <HomepageWrapper>
        <div className="test">HomePage</div>
        <RecruitmentList />
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
  > .test {
    color: white;
  }
`;

export default HomePage;
