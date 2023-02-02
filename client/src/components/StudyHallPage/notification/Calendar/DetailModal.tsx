import styled, { keyframes } from "styled-components";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { FiTrash2, FiEdit2 } from "react-icons/fi";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { calendarStore } from "../../../../util/zustandCalendar";
import AuthStore from "../../../../util/zustandAuth";
interface DetailModalType {
  showDetailModal: boolean;
  setShowDetailModal: Dispatch<SetStateAction<boolean>>;
  setShowEditModal: Dispatch<SetStateAction<boolean>>;
  setEditData: Dispatch<SetStateAction<string>>;
  event: any;
  data: any;
}
const DetailModal = ({
  showDetailModal,
  setShowDetailModal,
  setShowEditModal,
  setEditData,
  event,
  data,
}: DetailModalType) => {
  const [todoThatDay, setTodoThatDay] = useState<any[]>();
  const { authData } = AuthStore();
  const calendarDelete = calendarStore((state) => state.calendarDelete);
  const notify = () =>
    toast.success("삭제 되었습니다.", {
      toastId: "successDelete",
    });

  useEffect(() => {
    if (data) {
      setTodoThatDay(
        data.filter(
          (el: any) => el.calendarId === event.extendedProps.calendarId
        )
      );
    }
  }, [showDetailModal]);
  const clickEdit = () => {
    setShowDetailModal(false);
    setShowEditModal(true);
    todoThatDay && setEditData(todoThatDay[0]);
  };
  const clickDelete = () => {
    todoThatDay && calendarDelete(todoThatDay[0].calendarId);
    notify();
    setShowDetailModal(false);
  };
  return (
    <>
      {showDetailModal && (
        <ModalDiv showDetailModal={showDetailModal}>
          <ContentsDiv>
            <div className="flexDiv">
              <h2> DetailModal</h2>
              <AiOutlineCloseCircle onClick={() => setShowDetailModal(false)} />
            </div>
            <div>
              {todoThatDay && (
                <div>
                  <div> 날짜 : {todoThatDay[0].date.slice(0, 10)} </div>
                  <div> 시간 : {todoThatDay[0].date.slice(11, 16)} </div>
                  <div> 일정 : {todoThatDay[0].title} </div>
                  <hr />
                  {todoThatDay[0].participants.map(
                    (el: {
                      userId: number;
                      username: string;
                      joinState: string;
                    }) => (
                      <div key={el.userId}>
                        {el.username}:{el.joinState}
                      </div>
                    )
                  )}
                </div>
              )}
              <IconDiv>
                <div className="icon" onClick={clickEdit}>
                  <FiEdit2 />
                </div>
                {authData?.host && (
                  <div className="icon" onClick={clickDelete}>
                    <FiTrash2 />
                  </div>
                )}
              </IconDiv>
            </div>
          </ContentsDiv>
        </ModalDiv>
      )}
    </>
  );
};

export default DetailModal;
const fadeIn = keyframes`
  0% {
    opacity: 0;
    margin-top: -120px;
  }
  100% {
    opacity: 1;
    margin-top: -100px;
  }
`;

const fadeOut = keyframes`
  0% {
    opacity: 1;
    margin-top: -100px;
  }
  100% {
    opacity: 0;
    margin-top: -120px;
  }
`;

const ModalDiv = styled.main`
  animation: ${(prop: { showDetailModal: boolean }) =>
      prop.showDetailModal ? fadeIn : fadeOut}
    0.2s ease-in;
  margin-left: 150px;
  margin-top: -100px;
  max-width: 300px;
  box-shadow: 0rem 1rem 2rem rgba(0, 0, 0, 0.5);
  filter: blur(0);
  opacity: 1;
  visibility: visible;
  z-index: 10001;
  position: fixed;
  border-radius: var(--radius-10);
  background-color: var(--green);
  padding: 2px;
  ul,
  li {
    margin: 0;
    padding: 0;
    color: var(--beige-00) !important;
  }
  * {
    font-family: "mainB", Arial;
    color: var(--beige-00);
  }
`;
const ContentsDiv = styled.article`
  width: 256px;
  border: 2px solid var(--beige-00);
  padding: 5px 20px;
  border-radius: var(--radius-10);
  .flexDiv {
    display: flex;
    justify-content: space-between;
    * {
      font-size: 24px;
    }
  }
  div {
    margin: 20px 10px;
  }
`;
const IconDiv = styled.div`
  display: flex;
  font-size: 18px;
  justify-content: right;
  .icon {
    margin: 0 5px;
  }
`;
