import styled from "styled-components";
import { useEffect, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { FiTrash2, FiEdit2 } from "react-icons/fi";

import { calendarStore } from "../../../../util/zustandCalendar";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const DetailModal = ({
  showDetailModal,
  setShowDetailModal,
  setShowEditModal,
  setEditData,
  event,
  data,
}) => {
  const [todoThatDay, setTodoThatDay] = useState();
  const calendarDelete = calendarStore((state) => state.calendarDelete);
  useEffect(() => {
    if (data) {
      setTodoThatDay(
        data.filter((el) => el.date === event.startStr.slice(0, 19))
      );
    }
  }, [showDetailModal]);
  const clickEdit = () => {
    setShowDetailModal(false);
    setShowEditModal(true);
    setEditData(todoThatDay[0]);
  };
  const clickDelete = () => {
    calendarDelete(URL, todoThatDay[0].calendarId);
    setShowDetailModal(false);
    window.location.reload();
  };
  return (
    showDetailModal && (
      <ModalDiv>
        <ContentsDiv>
          <div className="flexDiv">
            <h2> DetailModal</h2>
            <AiOutlineCloseCircle onClick={() => setShowDetailModal(false)} />
          </div>
          <div>
            {todoThatDay && (
              <div>
                <ul>
                  <li> 날짜 : {todoThatDay[0].date.slice(0, 10)} </li>
                  <li> 시간 : {todoThatDay[0].date.slice(11, 16)} </li>
                  <li> 일정 : {todoThatDay[0].title} </li>
                  <hr />
                  {todoThatDay[0].participants.map((el) => (
                    <div key={el.userId}>
                      {el.username}:{el.joinState}
                    </div>
                  ))}
                </ul>
              </div>
            )}
            <IconDiv>
              <div className="icon" onClick={clickEdit}>
                <FiEdit2 />
              </div>
              <div className="icon" onClick={clickDelete}>
                <FiTrash2 />
              </div>
            </IconDiv>
          </div>
        </ContentsDiv>
      </ModalDiv>
    )
  );
};

export default DetailModal;
const Back = styled.div`
  position: absolute;
  background-color: black;
  width: 1000px;
  height: 1000px;
`;
const ModalDiv = styled.main`
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
  * {
    font-family: "mainB", Arial;
    color: var(--beige-00);
  }
`;
const ContentsDiv = styled.article`
  width: 256px;
  border: 2px solid var(--beige-00);
  padding: 20px;
  border-radius: var(--radius-10);
  .flexDiv {
    display: flex;
    justify-content: space-between;
    * {
      font-size: 24px;
    }
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
