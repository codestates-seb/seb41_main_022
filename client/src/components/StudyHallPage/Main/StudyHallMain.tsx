import styled, { StyleSheetManager } from "styled-components";
import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";
import Pagination from "../../Pagination";
import { CommentsData } from "../../../util/dummyDataStudyHall";
interface Studies {
  chatId: number;
  username: string;
  content: string;
  answers: [];
  pageInfo: any;
  totalElements: number;
}
interface Data {
  data: any;
}

const StudyHallMain = () => {
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(10);
  const { studyId, i, limit } = useParams();
  //데이터 요청
  const [commentsData, setCommentsData] = useState<Studies[] | undefined>();
  const getCommentsData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": "abcd",
      },
    });
  };

  const handlePrevPage = (prevPage: number) => {
    setPage((prevPage) => prevPage - 1);
  };

  const handleNextPage = (nextPage: number) => {
    setPage((nextPage) => nextPage + 1);
  };

  const fetchData = async () => {
    const response = await fetch(
      `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080//chat/${studyId}?page=${i}&size=${limit}`
    );
    const result = await response.json();
    setTotalPages(totalPages);
  };

  // API를 받아서 코멘트로 쏴준다

  useEffect(() => {
    getCommentsData(
      `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/chat/${studyId}?page=${1}&size=${15}`
    ).then((res) => {
      setCommentsData(res.data.data);
    });
  });
  useEffect(() => {
    fetchData();
  }, []);

  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {<StudyInfo />}
          <CreateComment />
          {commentsData &&
            commentsData.map((el) => (
              <Comments
                key={el.chatId}
                chatUserId={el.username}
                content={el.content}
                answers={el.answers}
                totalElements={el.pageInfo.totalElements}
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
