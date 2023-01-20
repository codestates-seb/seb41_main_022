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
  chatId: any;
  el: string;
  content: string;
  answers: [];
  pageInfo: any;
  totalElements: number;
  size: number;
  imgUrl: string;
}
interface PageInfo {
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}
interface Data {
  data: any;
  pageInfo: any;
}
interface Hi {
  data: Studies[];
  pageInfo: PageInfo;
}

const StudyHallMain = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token", "userData"]);

  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(10);
  const { studyId, i, limit, size } = useParams();
  //데이터 요청
  const [commentsData, setCommentsData] = useState<Hi | undefined>();
  const getCommentsData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        // "refresh-Token": cookies.token.accessToken,
      },
    });
  };
  useEffect(() => {
    getCommentsData(
      `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/chat/${studyId}?page=${page}&size=10`
    ).then((res) => {
      setCommentsData(res.data);
      console.log(res.data);
    });
  }, []);

  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {<StudyInfo />}
          <CreateComment />
          {commentsData &&
            commentsData.data.map((el) => (
              <Comments
                el={el.el}
                content={el.content}
                answers={el.answers}
                totalElements={commentsData.pageInfo.totalElements}
                imgUrl={el.imgUrl}
              />
            ))}
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
