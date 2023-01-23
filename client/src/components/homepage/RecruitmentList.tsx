import styled from "styled-components";
import axios, { AxiosResponse } from "axios";
import { useState, useEffect, useCallback } from "react";

import Recruitment from "./Recruitment";
import HomeStore from "../../util/zustandHome";

interface Data {
  data: any;
}

const RecruitmentList = () => {
  const [page, setPage] = useState<number>(1);
  console.log("page: ", page);
  const {
    tags,
    filter,
    search,
    recruitmentData,
    totalPage,
    fetch,
    setRecruitment,
  } = HomeStore();
  console.log(totalPage);
  useEffect(() => {
    setRecruitment([]);
  }, []);
  // 필터링 api요청
  useEffect(() => {
    fetch(tags, filter, search, page);
  }, [tags, filter, search, page]);

  const handleScroll = useCallback((): void => {
    const { innerHeight } = window;
    // 브라우저창 내용의 크기 (스크롤을 포함하지 않음)

    const { scrollHeight } = document.body;
    // 브라우저 총 내용의 크기 (스크롤을 포함한다)

    const { scrollTop } = document.documentElement;
    // 현재 스크롤바의 위치

    if (Math.round(scrollTop + innerHeight + 300) >= scrollHeight) {
      // scrollTop과 innerHeight를 더한 값이 scrollHeight보다 크다면, 가장 아래에 도달했다는 의미이다.

      // setPosts(posts.concat(getPostList(page + 1)));
      // // 페이지에 따라서 불러온 배열을 posts 배열과 합쳐줍니다.
      setPage(page + 1);

      // 페이지 state 변수의 값도 1씩 늘려줍니다.
    }
  }, [page]);

  useEffect(() => {
    window.addEventListener("scroll", handleScroll, true);
    // 스크롤이 발생할때마다 handleScroll 함수를 호출하도록 추가합니다.

    return () => {
      window.removeEventListener("scroll", handleScroll, true);
      // 해당 컴포넌트가 언마운트 될때, 스크롤 이벤트를 제거합니다.
    };
  }, [handleScroll]);

  return (
    <RecruitmentWrapper>
      {recruitmentData &&
        recruitmentData.map((el, idx) => (
          <Recruitment
            key={idx}
            studyId={el.studyId}
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
