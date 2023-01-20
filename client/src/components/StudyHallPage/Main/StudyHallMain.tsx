import styled, { StyleSheetManager } from "styled-components";
import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";
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
interface GroupType {
  data: Studies[];
  pageInfo: PageInfo;
}

const StudyHallMain = () => {
  const [cookies, setCookie, removeCookie] = useCookies([
    "userData",
    "authData",
    "token",
  ]);

  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const { studyId, i, limit } = useParams();

  //데이터 요청
  const [commentsData, setCommentsData] = useState<GroupType | undefined>();
  const getCommentsData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      },
    });
  };

  useEffect(() => {
    getCommentsData(
      `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/chat/${studyId}?page=${page}&size=${size}`
    ).then((res) => {
      setTotalPages(res.data.pageInfo.totalElements);
      setCommentsData(res.data);
    });
  }, [page, size]);

  // useEffect(() => {
  // axios
  //   .get(
  //     `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${cookies.userData.userId}/user/${cookies.userData.userId}/auth`,
  //     {
  //       headers: {
  //         "access-Token": cookies.token.accessToken,
  //         "refresh-Token": cookies.token.accessToken,
  //       },
  //     }
  //   )
  //   .then((res) => setCookie("authData", { data: res.data.data }));
  // }, []);

  return (
    <MainWrapper>
      <div className="padding">
        <div>
          {<StudyInfo />}
          <CreateComment />
          {commentsData &&
            commentsData.data.map((el) => (
              <Comments
                chatId={el.chatId}
                el={el.el}
                content={el.content}
                answers={el.answers}
                totalElements={commentsData.pageInfo.totalElements}
                size={el.size}
                imgUrl={el.imgUrl}
              />
            ))}
          <Pagination>
            <button
              onClick={() => {
                if (page > 1) {
                  setPage(page - 1);
                }
              }}
            >
              이전
            </button>
            <button
              onClick={() => {
                if (page < totalPages) {
                  setPage(page + 1);
                }
              }}
            >
              다음
            </button>
          </Pagination>
        </div>
      </div>
    </MainWrapper>
  );
};
export default StudyHallMain;

const MainWrapper = styled.div``;

const Pagination = styled.div`
  background-color: var(--green);
  display: center;
  font-size: 2px;
  button {
    display: center;
    width: 50px;
    height: 30px;
    color: var(--beige-00);
    background-color: var(--green);
    border: solid 1px var(--beige-00);
    border-radius: 15px;
    font-size: 14px;
    margin: 3px;
    padding: 4px;
  }
`;
