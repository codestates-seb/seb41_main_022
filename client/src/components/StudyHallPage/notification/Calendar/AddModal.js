import styled from "styled-components";
import { useEffect, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";

const AddModal = ({ showAddModal, setShowAddModal, event, setTodo }) => {
  return (
    showAddModal && (
      <ModalDiv>
        <ContentsDiv>
          <div className="flexDiv">
            <h2> DetailModal</h2>
            <AiOutlineCloseCircle onClick={() => setShowAddModal(false)} />
          </div>
          <div>
            <ul>
              <li> 날짜 : </li>
              <li> 시간 : </li>
              <li>
                일정 : <input type="text" />
              </li>
            </ul>
          </div>
        </ContentsDiv>
      </ModalDiv>
    )
  );
};

export default AddModal;
const ModalDiv = styled.main`
  margin-left: 150px;
  margin-top: -50px;
  max-width: 300px;
  max-height: 200px;
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
  height: 156px;
  border: 2px solid var(--beige-00);
  padding: 20px;
  border-radius: var(--radius-10);
  .flexDiv {
    display: flex;
    justify-content: space-between;
  }
`;
