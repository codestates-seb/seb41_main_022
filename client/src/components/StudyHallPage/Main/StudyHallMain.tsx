import styled, { StyleSheetManager } from "styled-components";
import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";
import Pagination from "../../Pagination";
import { CommentsData } from "../../../util/dummyDataStudyHall";
interface Studies {
  chatId: number;
  el: string;
  content: string;
  answers: [];
  pageInfo: any;
  totalElements: number;
  size: number;
  studyId: string;
  page: number;
}
interface Data {
  data: any;
}

const StudyHallMain = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token", "userData"]);

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(10);
  const { studyId, i, limit } = useParams();
  //데이터 요청
  const [answers, setAnswers] = useState<Studies[] | undefined>();
  const getAnswersData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
      },
    });
  };
  // useEffect(() => {
  //   getAnswersData(
  //     `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/answer/${chatId}`
  //   ).then((res) => {
  //     setAnswers(res.data.data);
  //   });
  // });
  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {<StudyInfo />}
          <CreateComment />
          {/* {commentsData &&
            commentsData.map((el) => (
              <Comments
                key={el.chatId}
                el={el.el}
                content={el.content}
                answers={el.answers}
                totalElements={el.pageInfo.totalElements}
              />
            ))} */}
          {/* <Pagination
            // total={totalQuestions}
            limit={limit}
            page={page}
            setPage={setPage}
          /> */}
        </div>
      </div>
    </MainWrapper>
  );
};
export default StudyHallMain;

const MainWrapper = styled.div``;

// const handlePrevPage = (prevPage: number) => {
//   setPage((prevPage) => prevPage - 1);
// };

// const handleNextPage = (nextPage: number) => {
//   setPage((nextPage) => nextPage + 1);
// };

// const fetchData = async () => {
//   const response = await fetch(
//     `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080//chat/${studyId}?page=${i}&size=${limit}`
//   );
//   const result = await response.json();
//   setTotalPages(totalPages);
// };

// // API를 받아서 코멘트로 쏴준다

// useEffect(() => {
//   getCommentsData(
//     `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080//study/${studyId}/main`
//   ).then((res) => {
//     setCommentsData(res.data.data);
//   });
// });
// useEffect(() => {
//   fetchData();
// }, []);
