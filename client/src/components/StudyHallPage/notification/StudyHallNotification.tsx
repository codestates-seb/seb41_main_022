import styled from "styled-components";
import StudyHallNotificationSidebar from "./StudyHallNotificationSidebar";
import { useForm } from "react-hook-form";
import { IoIosCheckmarkCircle } from "react-icons/io";
import { IoIosCheckmarkCircleOutline } from "react-icons/io";
import CalendarApp from "./Calendar/CalendarApp";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import axios from "axios/index";
import NoticeStore from "../../../util/zustandNotice";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const StudyHallNotification = () => {
  const { notice, patchNotice } = NoticeStore();
  const { studyId } = useParams();
  const [tempNotice, setTempNotice] = useState("");
  useEffect(() => {
    setTempNotice(notice);
  }, [notice]);
  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    patchNotice(URL, studyId, { notice: tempNotice });
    alert("변경되었습니다");
  };
  const onPatch = (e: React.ChangeEvent<HTMLInputElement>) => {
    setTempNotice(e.target.value);
  };
  return (
    <>
      <NotificationWrapper>
        <div className="padding" />
        <div>
          <StudyHallNotificationSidebar />
          <div className="title">Notification</div>
          <NotificationCreate>
            <InputBorderForm className="inputBorder" onSubmit={handleSubmit}>
              <input
                className="input"
                id="notification"
                type="text"
                placeholder="공지사항..."
                onChange={onPatch}
                value={tempNotice}
              />
              <SubmitButton type="submit">
                <IoIosCheckmarkCircleOutline className="outline" />
                <IoIosCheckmarkCircle className="inline" />
              </SubmitButton>
            </InputBorderForm>
          </NotificationCreate>
        </div>
        <CalendarApp />
      </NotificationWrapper>
    </>
  );
};
export default StudyHallNotification;
const NotificationWrapper = styled.main`
  background-color: var(--beige-00);
  * {
    font-family: "mainB", Arial;
  }
  .padding {
    padding-top: 25px;
  }
  > div {
    width: 460px;
    margin: 0 auto;
  }
  .title {
    color: var(--green);
    font-size: 24px;
  }
  .sc-jIRcFI,
  .fvPoww,
  .demo-app {
    width: 600px;
    margin: 0 auto;
  }
`;
const NotificationCreate = styled.div`
  margin-top: 5px;
  width: 100%;
  height: 50px;
  background-color: var(--green);
  border-radius: var(--radius-20);
  display: flex;
  align-items: center;
`;
const InputBorderForm = styled.form`
  width: 432px;
  height: 26px;
  margin: 0 auto;
  border: 2px solid var(--beige-00);
  border-radius: var(--radius-30);
  display: flex;
  justify-content: space-between;
  * {
    outline: none;
    box-shadow: none;
    border: none;
    background: transparent;
  }
  > input {
    color: var(--beige-00);
    margin-left: 15px;
  }
  input::placeholder {
    color: var(--beige-00);
    opacity: 50%;
  }
`;
const SubmitButton = styled.button`
  font-size: 24px;
  color: var(--beige-00);
  .outline {
    display: block;
  }
  .inline {
    display: none;
  }
  :hover {
    .outline {
      display: none;
    }
    .inline {
      display: block;
    }
  }
`;
const CalendarWrapper = styled.div`
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  * {
    font-family: "mainB", Arial;
    font-size: 18px;
    color: var(--green);
  }
`;
const Month = styled.div`
  width: 70px;
  margin: 0 auto;
  display: flex;
  flex-direction: row;
  > div {
    margin: 0 5px;
  }
`;
