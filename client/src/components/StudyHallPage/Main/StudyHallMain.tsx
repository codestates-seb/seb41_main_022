import styled from "styled-components";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useCookies } from "react-cookie";

import StudyInfo from "./StudyInfo";
import CreateComment from "./CreateComment";
import Comments from "./Comments";
import { commentStore } from "../../../util/zustandComment";

const StudyHallMain = () => {
  const [cookies] = useCookies(["token"]);

  const [page, setPage] = useState(1);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(0);
  const { studyId } = useParams();

  //데이터 요청
  const { fetchCommentData, commentsData } = commentStore();

  useEffect(() => {
    if (studyId) {
      fetchCommentData(
        cookies.token.accessToken,
        cookies.token.refreshToken,
        studyId,
        page,
        size
      );
      if (commentsData) {
        setTotalPages(commentsData.pageInfo.totalElements);
      }
    }
  }, [page, size]);

  return (
    <div>
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
                  chatCreatedAt={el.chatCreatedAt}
                  answers={el.answers}
                  totalElements={commentsData.pageInfo.totalElements}
                  size={el.size}
                  page={page}
                  requestSize={size}
                />
              ))}
            {(commentsData && commentsData.data.length > 9) || page > 1 ? (
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
            ) : null}
          </CommentsWrap>
        </div>
      </div>
    </div>
  );
};
export default StudyHallMain;

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
