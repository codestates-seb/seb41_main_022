import styled from "styled-components";
import { useEffect, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";

const DetailModal = ({ showDetailModal, setShowDetailModal, event, data }) => {
  const [todoThatDay, setTodoThatDay] = useState();
  useEffect(() => {
    if (data) {
      setTodoThatDay(
        data.filter((el) => el.start === event.startStr.slice(0, 19))
      );
    }
  }, [showDetailModal]);
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
                  <li> 날짜 : {todoThatDay[0].start.slice(0, 10)} </li>
                  <li> 시간 : {todoThatDay[0].start.slice(11)} </li>
                  <li> 일정 : {todoThatDay[0].title} </li>
                  <hr />
                  {todoThatDay[0].participant.map((el) => (
                    <li key={el.userId}>
                      {el.name} : {el.joinState}
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        </ContentsDiv>
      </ModalDiv>
    )
  );
};

export default DetailModal;
const ModalDiv = styled.main`
  margin-left: 150px;
  margin-top: -100px;
  max-width: 300px;
  max-height: 300px;
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
  height: 256px;
  border: 2px solid var(--beige-00);
  padding: 20px;
  border-radius: var(--radius-10);
  .flexDiv {
    display: flex;
    justify-content: space-between;
  }
`;
