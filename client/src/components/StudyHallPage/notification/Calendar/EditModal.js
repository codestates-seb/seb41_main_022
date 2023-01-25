import styled from "styled-components";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { useEffect, useState } from "react";
import { calendarStore } from "../../../../util/zustandCalendar";
import { useCookies } from "react-cookie";
import AuthStore from "../../../../util/zustandAuth";

const EditModal = ({ showEditModal, setShowEditModal, editData, event }) => {
  const [title, setTitle] = useState("");
  const [hour, setHour] = useState("00");
  const [participants, setParticipants] = useState([]);
  const [minute, setMinute] = useState("00");
  const [myUserId, setMyUserId] = useState();
  const [cookies, setCookie, removeCookie] = useCookies(["userData"]);
  const calendarPatch = calendarStore((state) => state.calendarPatch);
  const { authData } = AuthStore();
  useEffect(() => {
    setMyUserId(cookies.userData.userId);
    setParticipants(editData.participants);
    setTitle(editData.title);
  }, [showEditModal]);
  const handleSubmitHost = (e) => {
    e.preventDefault();
    calendarPatch(editData.calendarId, {
      ...editData,
      title,
      date: `${editData.date.slice(0, 10)}T${hour}:${minute}:00`,
      participants,
    });
    setShowEditModal(false);
  };
  const handleSubmitMember = (e) => {
    e.preventDefault();
    calendarPatch(URL, editData.calendarId, {
      ...editData,
      participants,
    });
    setShowEditModal(false);
  };
  return (
    showEditModal && (
      <ModalDiv>
        <ContentsDiv>
          <div className="flexDiv">
            <h2> EditModal</h2>
            <AiOutlineCloseCircle onClick={() => setShowEditModal(false)} />
          </div>
          <div>
            <Form
              onSubmit={authData?.host ? handleSubmitHost : handleSubmitMember}
            >
              <div className="titleText">
                날짜{" : "}
                {editData.date.split("T")[0]}
              </div>
              <div className="time">
                시간
                {authData?.host ? (
                  <>
                    <br />
                    <select name="h" onClick={(e) => setHour(e.target.value)}>
                      <option value="00">00</option>
                      <option value="01">01</option>
                      <option value="02">02</option>
                      <option value="03">03</option>
                      <option value="04">04</option>
                      <option value="05">05</option>
                      <option value="06">06</option>
                      <option value="07">07</option>
                      <option value="08">08</option>
                      <option value="09">09</option>
                      <option value="10">10</option>
                      <option value="11">11</option>
                      <option value="12">12</option>
                      <option value="13">13</option>
                      <option value="14">14</option>
                      <option value="15">15</option>
                      <option value="16">16</option>
                      <option value="17">17</option>
                      <option value="18">18</option>
                      <option value="19">19</option>
                      <option value="20">20</option>
                      <option value="21">21</option>
                      <option value="22">22</option>
                      <option value="23">23</option>
                    </select>
                    <select name="m" onClick={(e) => setMinute(e.target.value)}>
                      <option value="00">00</option>
                      <option value="15">15</option>
                      <option value="30">30</option>
                      <option value="45">45</option>
                    </select>
                  </>
                ) : (
                  <>
                    {" : "}
                    {editData.date.split("T")[1].slice(0, 5)}
                  </>
                )}
              </div>
              <div>
                일정
                {authData?.host ? (
                  <>
                    <br />
                    <input
                      type="text"
                      className="person"
                      id="title"
                      placeholder="what to do..."
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
                    />
                  </>
                ) : (
                  <>
                    {" : "}
                    {editData.title}
                  </>
                )}
              </div>
              <hr />
              {editData.participants.map((el) => (
                <div>
                  <ul>
                    {myUserId === el.userId ? (
                      <li key={el.userid}>
                        {el.username}
                        {" : "}
                        <select
                          onClick={(e) =>
                            setParticipants([
                              ...participants,
                              {
                                userId: el.userId,
                                username: el.username,
                                joinState: e.target.value,
                              },
                            ])
                          }
                        >
                          <option value="NONE ">NONE </option>
                          <option value="YES ">YES </option>
                          <option value="NO ">NO </option>
                        </select>
                      </li>
                    ) : (
                      <li key={el.userid}>
                        {el.username + " : " + el.joinState}
                      </li>
                    )}
                  </ul>
                </div>
              ))}
              <div className="flex-between">
                <RedButton type="submit">수정</RedButton>
                <RedButton
                  type="button"
                  onClick={() => setShowEditModal(false)}
                >
                  취소
                </RedButton>
              </div>
            </Form>
          </div>
        </ContentsDiv>
      </ModalDiv>
    )
  );
};
export default EditModal;

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
  select,
  option {
    color: var(--green);
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
const Form = styled.form`
  margin: 10px;
  div {
    margin: 20px 10px;
    * {
      color: var(--green);
    }
  }
  .flex-between {
    display: flex;
    color: var(--beige-00);
    justify-content: space-between;
    padding-top: 10px;
    button {
      color: var(--beige-00);
    }
  }
`;
const RedButton = styled.button`
  border-style: none;
  width: 80px;
  padding: 3px;
  background-color: var(--red-00);
  border-radius: var(--radius-30);
  font-family: "mainL", Arial;
  font-size: 16px;
  :hover {
    background-color: var(--red-10);
  }
`;
