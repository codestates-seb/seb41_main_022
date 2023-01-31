import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";
import styled, { keyframes } from "styled-components";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import { useCookies } from "react-cookie";
import RedButton from "./RedButton";

interface UserDeleteType {
  showLeaveStudyModal: boolean;
  setShowLeaveStudyModal: Dispatch<SetStateAction<boolean>>;
}

const LeaveStudyModal = ({
  showLeaveStudyModal,
  setShowLeaveStudyModal,
}: UserDeleteType) => {
  const { studyId } = useParams();
  const [cookies] = useCookies(["token", "userData"]);
  const navigate = useNavigate();
  const notify = () => toast.success("스터디를 떠납니다");
  const handleClickLeaveStudy = () => {
    axios
      .delete(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/${cookies.userData.userId}`,
        {
          headers: {
            "access-Token": cookies.token.accessToken,
            "refresh-Token": cookies.token.refreshToken,
          },
        }
      )
      .then(() => {
        notify();
        navigate("/");
      });
  };
  return (
    <>
      {showLeaveStudyModal && (
        <ModalDiv showUserModal={showLeaveStudyModal}>
          <ContentsDiv>
            <div className="flexDiv">
              <h2> Leave Study</h2>
              <AiOutlineCloseCircle
                onClick={() => setShowLeaveStudyModal(false)}
              />
            </div>
            <div className="marginL">정말로 스터디를 떠나시나요?</div>
            <div className="directionChange">
              <RedButton
                handleClick={handleClickLeaveStudy}
                text="yes"
              ></RedButton>
              <RedButton
                handleClick={() => setShowLeaveStudyModal(false)}
                text="no"
              ></RedButton>
            </div>
            <div className="directionChange"></div>
          </ContentsDiv>
        </ModalDiv>
      )}
    </>
  );
};
export default LeaveStudyModal;
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
  animation: ${(prop: { showUserModal: boolean }) =>
      prop.showUserModal ? fadeIn : fadeOut}
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
    margin: 20px 10px;
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
  .flexDiv {
    margin: 20px 10px;
  }
  .flexMember {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .marginL {
    text-align: center;
  }
`;
