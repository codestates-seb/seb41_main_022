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
  const [totalPages, setTotalPages] = useState(10);
  const { studyId, i, limit, size } = useParams();
  //데이터 요청
  const [commentsData, setCommentsData] = useState<GroupType | undefined>();
  const getCommentsData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refeshToken,
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

  useEffect(() => {
    axios
      .get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${cookies.userData.userId}/user/${cookies.userData.userId}/auth`,
        {
          headers: {
            "access-Token": cookies.token.accessToken,
            "refresh-Token": cookies.token.accessToken,
          },
        }
      )
      .then((res) => setCookie("authData", { data: res.data.data }));
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
                chatId={el.chatId}
                el={el.el}
                content={el.content}
                answers={el.answers}
                totalElements={commentsData.pageInfo.totalElements}
                size={el.size}
                imgUrl={el.imgUrl}
              />
            ))}
        </div>
      </div>
    </MainWrapper>
  );
};
export default StudyHallMain;

const MainWrapper = styled.div``;
