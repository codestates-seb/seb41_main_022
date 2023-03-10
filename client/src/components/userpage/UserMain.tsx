import { AiOutlineCloseCircle } from "react-icons/ai";
import { toast } from "react-toastify";
import MyStudyList from "./MyStudyList";
import axios from "axios";
import { useEffect, useState } from "react";
import LoginStore from "../../util/zustandLogin";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";
import styled from "styled-components";

const UserMain = () => {
  const [cookies, setCookie, removeCookie] = useCookies(["token", "userData"]);
  const [isOpenAgreePage, setIsOpenAgreePage] = useState(false);
  const [agreeWithdraw, setAgreeWithdraw] = useState(false);
  const [totalStudyCount, setTotalStudyCount] = useState<number | undefined>(
    undefined
  );
  useEffect(() => {
    axios
      .get(process.env.REACT_APP_API_URL + "/study/user", {
        headers: {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        },
      })
      .then((res) => setTotalStudyCount(res.data.data.studyCount));
  }, []);
  const error = () =>
    toast.error("가입되어 있는 모든 스터디에서 탈퇴 후 진행해주세요");
  const { setIsLogin } = LoginStore();
  const navigate = useNavigate();
  const withdraw = () => {
    if (agreeWithdraw) {
      totalStudyCount === 0
        ? axios
            .delete(process.env.REACT_APP_API_URL + "/user", {
              headers: {
                "access-Token": cookies.token.accessToken,
                "refresh-Token": cookies.token.refreshToken,
              },
            })
            .then(() => {
              navigate("/");
              removeCookie("token");
              removeCookie("userData");
              setIsLogin(false);
            })
        : error();
      setAgreeWithdraw(false);
      setIsOpenAgreePage(false);
    }
  };
  const handleOpenWithdraw = () => {
    setIsOpenAgreePage(!isOpenAgreePage);
  };

  const handleActiveWithdrawButton = () => {
    setAgreeWithdraw(!agreeWithdraw);
  };
  return (
    <>
      <h3 className="title">My Study</h3>
      {isOpenAgreePage ? (
        <WithdrawAgree>
          <div className="textZone">
            <div className="closeButtonWrapper">
              <h2>회원탈퇴 안내사항</h2>
              <AiOutlineCloseCircle
                className="closeButton"
                onClick={() => {
                  setIsOpenAgreePage(!isOpenAgreePage);
                  setAgreeWithdraw(false);
                }}
              />
            </div>
            <ul>
              <li>
                • 회원탈퇴가 진행된 후에는 다시 되돌릴 수 없습니다. 아래 내용을
                잘 읽어보신 후 회원탈퇴를 진행해 주시기 바랍니다.
              </li>
              <li>
                • 회원탈퇴 후에는 스터디장으로서 운영하시던 스터디의 다음
                스터디원에게 자동으로 스터디장 위임이 됩니다. 원하시는 인원에게
                스터디장 위임을 하시려면 스터디홀 - setting 페이지에서 스터디장
                위임을 진행해주세요.
              </li>
              <li>
                • 회원탈퇴 후에도 존재하는 스터디는 삭제되지 않으며, 스터디의
                데이터는 웹사이트에 남게 됩니다. 스터디 삭제를 원하시면 스터디홀
                - setting 페이지에서 스터디 삭제를 진행해주세요.
              </li>
            </ul>
            <div className="checkboxWrapper">
              <p>위 내용을 확인했으며, 회원 탈퇴에 동의합니다.</p>
              <input type={"checkbox"} onClick={handleActiveWithdrawButton} />
            </div>
          </div>
          <div className="buttonWrapper">
            <WithdrawButton
              onClick={withdraw}
              className={
                agreeWithdraw ? "withdrawButton active" : "withdrawButton"
              }
            >
              회원탈퇴
            </WithdrawButton>
          </div>
        </WithdrawAgree>
      ) : null}
      <MyStudyList />
      <button
        onClick={handleOpenWithdraw}
        className={isOpenAgreePage ? "button buttonActive" : "button"}
        type="submit"
      >
        회원탈퇴 열기
      </button>
    </>
  );
};
const WithdrawAgree = styled.div`
  position: absolute;
  display: flex;
  flex-direction: column;
  padding: 20px;
  width: 784px;
  margin-left: 120px;
  margin-top: 20px;
  margin-bottom: 20px;
  background-color: white;
  border: 1px orange solid;
  border-radius: 3px;
  > .buttonWrapper {
    display: flex;
    justify-content: end;
    > .active {
      background-color: var(--red-00);
      color: var(--beige-00);
      hover {
        cursor: pointer;
      }
    }
  }
  .textZone {
    * {
      font-family: "mainM";
      margin: 8px 0;
    }
    .closeButtonWrapper {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding-right: 16px;
      > .closeButton {
        fill: var(--red-00);
        font-size: 25px;
        cursor: pointer;
      }
    }
    h2 {
      display: inline;
      font-family: "mainB";
      font-size: 24px;
      margin: 14px 0;
    }
  }
  .checkboxWrapper {
    margin-top: 24px;
    text-align: right;
    margin-right: 40px;
    p {
      display: inline;
    }
  }
`;

const WithdrawButton = styled.button`
  background-color: #976663;
  color: var(--grey-00);
  width: 80px;
  padding: 4px 8px;
  border-radius: var(--radius-10);
  border: none;
`;
export default UserMain;
