import styled, { StyleSheetManager } from "styled-components";
import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";

interface Studies {
  chatId: any;
  username: string;
  content: string;
  answers: [];
  pageInfo: any;
  totalElements: number;
  size: number;
  imgUrl: string;
  isClosedChat: boolean;
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
  const [cookies, setCookie] = useCookies(["token"]);

  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const { studyId } = useParams();

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
      process.env.REACT_APP_API_URL +
        `/chat/${studyId}?page=${page}&size=${size}`
    ).then((res) => {
      setCommentsData(res.data);
      setTotalPages(res.data.pageInfo.totalElements);
    });
  }, [page, size]);

  return (
    <MainWrapper>
      <div className="padding">
        <div>
          <CommentsWrap>
            <StudyInfo />
            <CreateComment />
            {commentsData &&
              commentsData.data.map((el, idx) => (
                <Comments
                  key={idx}
                  chatId={el.chatId}
                  username={el.username}
                  imgUrl={el.imgUrl}
                  content={el.content}
                  isClosedChat={el.isClosedChat}
                  answers={el.answers}
                  totalElements={commentsData.pageInfo.totalElements}
                  size={el.size}
                />
              ))}
            {commentsData && size < 10 && page <= 1 ? null : (
              <Pagination>
                <div className="button-wrapper">
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
                </div>
              </Pagination>
            )}
          </CommentsWrap>
        </div>
      </div>
    </MainWrapper>
  );
};
export default StudyHallMain;

const MainWrapper = styled.div``;
const CommentsWrap = styled.div`
  display: flex;
  flex-direction: column;
`;
const Pagination = styled.div`
  background-color: var(--green);
  display: flex;
  justify-content: center;
  font-size: 2px;

  .button-wrapper {
    margin-top: 1px;
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
  }
`;
