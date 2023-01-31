import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { AiOutlineCloseCircle } from "react-icons/ai";
import styled, { keyframes } from "styled-components";
import axios from "axios";
import { useParams } from "react-router-dom";
import { toast } from "react-toastify";

interface UserDeleteType {
  showUserModal: boolean;
  setShowUserModal: Dispatch<SetStateAction<boolean>>;
}
interface MemberInfo {
  userId: number;
  username: string;
  imgUrl: string;
  role: string;
}
const DeleteUserModal = ({
  showUserModal,
  setShowUserModal,
}: UserDeleteType) => {
  const [memberInfo, setMemberInfo] = useState<MemberInfo[]>();
  const { studyId } = useParams();
  const notify = (username: string) =>
    toast.success(username + `님이 삭제 되었습니다.`);
  const notifyError = () => toast.error(`에러가 발생했습니다. `);
  useEffect(() => {
    axios
      .get(process.env.REACT_APP_API_URL + `/user/study?studyId=${studyId}`)
      .then((res) => setMemberInfo(res.data.data));
  }, [showUserModal]);
  const handleClickDeleteUser = (userId: number, username: string) => {
    try {
      axios.delete(
        process.env.REACT_APP_API_URL + `/study/${studyId}/${userId}`
      );
      notify(username);
      setShowUserModal(false);
    } catch (e) {
      notifyError();
    }
  };
  return (
    <>
      {showUserModal && (
        <ModalDiv showUserModal={showUserModal}>
          <ContentsDiv>
            <div className="flexDiv">
              <h2> UserEdit</h2>
              <AiOutlineCloseCircle onClick={() => setShowUserModal(false)} />
            </div>
            <div className="flexMember">
              {memberInfo &&
                memberInfo.map((el, idx) => (
                  <Notice>
                    {!el.role.includes("ADMIN") && (
                      <div className="wrapper">
                        <img src={el.imgUrl} />
                        <div className="name">{el.username}</div>
                        <AiOutlineCloseCircle
                          className="deleteIcon"
                          onClick={() =>
                            handleClickDeleteUser(el.userId, el.username)
                          }
                        />
                      </div>
                    )}
                  </Notice>
                ))}
            </div>
            <div className="directionChange"></div>
          </ContentsDiv>
        </ModalDiv>
      )}
    </>
  );
};
export default DeleteUserModal;
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
`;
const Notice = styled.div`
  margin-top: 5px;
  width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  .wrapper {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 4px;
    > img {
      width: 26px;
      height: 26px;
      border-radius: var(--radius-30);
    }
    > span {
      width: 26px;
      height: 26px;
      display: flex;
      align-items: center;
      > svg {
        width: 20px;
        height: 20px;
      }
    }
    .deleteIcon {
      font-size: 24px;
      color: var(--red-00);
    }
  }
  div {
    color: var(--beige-00);
    display: flex;
    align-items: center;
    padding: 3px;
  }
`;
