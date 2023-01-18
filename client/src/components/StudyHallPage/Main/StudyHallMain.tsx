import styled, { StyleSheetManager } from "styled-components";
import { useState, useEffect } from "react";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";
import Pagination from "../../Pagination";

//API Data import
import { CommentsData } from "../../../util/dummyDataStudyHall";

const StudyHallMain = () => {
  // const [page, setPage] = useState(1);
  // const [totalPages, setTotalPages] = useState(10);

  // const handlePrevPage = (prevPage: number) => {
  //   setPage((prevPage) => prevPage - 1);
  // };

  // const handleNextPage = (nextPage: number) => {
  //   setPage((nextPage) => nextPage + 1);
  // };

  // const fetchData = async () => {
  //   const response =
  //     await fetch(`https://api.coingecko.com/api/v3/coins/markets?
  //   vs_currency=usd&order=market_cap_desc&?${page}&per_page=10&sparkline=false`);
  //   const result = await response.json();

  //   setTotalPages(totalPages);
  // };

  // useEffect(() => {
  //   fetchData();
  // }, []);

  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {<StudyInfo />}
          <CreateComment />
          {CommentsData.data.map((el) => (
            <Comments
              key={el.chatId}
              chatUserId={el.chatUserId}
              content={el.content}
              answers={el.answers}
              totalElements={CommentsData.pageInfo.totalElements}
            />
          ))}
          {/* <Pagination
            totalPages={totalPages}
            currentPage={page}
            handlePrevPage={handlePrevPage}
            handleNextPage={handleNextPage}
          /> */}
        </div>
      </div>
    </MainWrapper>
  );
};
export default StudyHallMain;

const MainWrapper = styled.div``;
