import { Dispatch, SetStateAction, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";
import styled, { keyframes } from "styled-components";
import RedButton from "./RedButton";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { useCookies } from "react-cookie";
import { toast } from "react-toastify";

interface UserDeleteType {
  showStudyModal: boolean;
  setShowStudyModal: Dispatch<SetStateAction<boolean>>;
}
const DeleteStudyModal = ({
  showStudyModal,
  setShowStudyModal,
}: UserDeleteType) => {
  const { studyId } = useParams();
  const navigate = useNavigate();
  const [cookies] = useCookies(["token", "userData"]);
  const notify = () => toast.success("스터디가 삭제되었습니다. ");
  const handleClickDeleteStudy = () => {
    axios
      .delete(process.env.REACT_APP_API_URL + `/study/${studyId}`, {
        headers: {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        },
      })
      .then(() => {
        notify();
        navigate("/");
      });
  };
  return (
    <>
      {showStudyModal && (
        <ModalDiv showStudyModal={showStudyModal}>
          <ContentsDiv>
            <div className="flexDiv">
              <h2> Delete Study</h2>
              <AiOutlineCloseCircle onClick={() => setShowStudyModal(false)} />
            </div>
            <div className="marginL">정말로 스터디 삭제하시나요?</div>
            <div className="directionChange">
              <RedButton
                handleClick={handleClickDeleteStudy}
                text="yes"
              ></RedButton>
              <RedButton
                handleClick={() => setShowStudyModal(false)}
                text="no"
              ></RedButton>
            </div>
          </ContentsDiv>
        </ModalDiv>
      )}
    </>
  );
};
export default DeleteStudyModal;
const fadeIn = keyframes`
  0% {
    opacity: 0;
    margin-top: -20px;
  }
  100% {
    opacity: 1;
    margin-top: 0;
  }
`;

const fadeOut = keyframes`
  0% {
    opacity: 1;
    margin-top: 0;
  }
  100% {
    opacity: 0;
    margin-top: -20px;
  }
`;
const ModalDiv = styled.main`
  animation: ${(prop: { showStudyModal: boolean }) =>
      prop.showStudyModal ? fadeIn : fadeOut}
    0.2s ease-in;
  margin-top: 0;
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
  .directionChange {
    display: flex;
    flex-direction: row;
    > button {
      margin: 0 10px;
    }
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
  .marginL {
    text-align: center;
  }
`;
