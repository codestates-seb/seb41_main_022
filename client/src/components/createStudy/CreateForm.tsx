import styled from "styled-components";
import { Controller, useForm, SubmitHandler } from "react-hook-form";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useState, useRef, useCallback, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

import CreateWeekBar from "./CreateWeekBar";
import ToggleOnline from "./ToggleOnline";
import CreatePageTags from "./CreatePageTags";
import TogglePublic from "./TogglePublic";
import { createStudyStore } from "../../util/zustandCreateStudy";
import { AiOutlinePlusCircle } from "react-icons/ai";
import Congratulation from "../../util/Congratulation";

const URL = process.env.REACT_APP_API_URL;

interface MyFormProps {
  teamName: string;
  summary: string;
  tags: string[];
  dayOfWeek: string[];
  want: number;
  startDate: string;
  onOff: boolean;
  openClose: boolean;
  content: string;
  image: string;
}

const CreateForm = () => {
  const fetch = (url: string): Promise<AxiosResponse<any>> => {
    return axios.get(url);
  };
  useEffect(() => {
    const url = URL + "/tag";
    fetch(url).then((res) => setTag(res.data.data.tags));
  }, []);
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  const fetchCreateStudy = createStudyStore((state) => state.fetchCreateStudy);
  const navigate = useNavigate();
  const [isOpen, setIsOpen] = useState(false);
  const [tag, setTag] = useState<string[] | undefined>();
  const [startDate, setStartDate] = useState();
  const [showCongrate, setShowCongrate] = useState(false);
  const textRef = useRef<HTMLTextAreaElement>(null);
  const handleResizeHeight = useCallback(() => {
    if (textRef.current) {
      textRef.current.style.height = "16px";
      textRef.current.style.height = textRef.current.scrollHeight + "px";
    }
  }, []);

  const {
    register,
    handleSubmit,
    control,
    watch,
    formState: { errors },
  } = useForm<MyFormProps>();
  const onSubmitHandler: SubmitHandler<MyFormProps> = (data) => {
    const form = {
      ...data,
    };
    fetchCreateStudy(form, {
      "access-Token": cookies.token.accessToken,
      "refresh-Token": cookies.token.refreshToken,
    });
    setShowCongrate(true);
    setTimeout(() => {
      window.scrollTo(0, 0);
      setShowCongrate(false);
      navigate("/");
    }, 4000);
  };
  return (
    <>
      {showCongrate ? (
        <Congratulation />
      ) : (
        <Main>
          <ContentDiv>
            Create New Study
            <Form onSubmit={handleSubmit(onSubmitHandler)}>
              <input
                id="teamName"
                type="text"
                placeholder="Team Name"
                maxLength={15}
                className={
                  errors.teamName && errors.teamName.type === "required"
                    ? "errorBorder"
                    : "ㅤ"
                }
                {...register("teamName", { required: true })}
              />
              <ErrorText>
                {errors.teamName && errors.teamName.type === "required"
                  ? "* 팀이름을 입력해 주세요!"
                  : " "}
              </ErrorText>
              <input
                id="summary"
                type="text"
                placeholder="한 줄 설명"
                className={
                  errors.summary && errors.summary.type === "required"
                    ? "errorBorder"
                    : "ㅤ"
                }
                {...register("summary", { required: true })}
              />
              <ErrorText>
                {errors.summary && errors.summary.type === "required"
                  ? "* 한 줄 설명을 입력해 주세요!"
                  : " "}
              </ErrorText>
              <div className="tagAddButton">
                <div className="AddButton">
                  Tags&nbsp;
                  <AiOutlinePlusCircle
                    onClick={() => setIsOpen(!isOpen)}
                    className={
                      errors.tags && errors.tags.type === "required"
                        ? "errorIcon"
                        : ""
                    }
                  />
                </div>
              </div>
              <Controller
                name="tags"
                control={control}
                rules={{ required: true }}
                render={({ field: { onChange } }) => (
                  <div className="tagSection">
                    <CreatePageTags
                      tag={tag}
                      onChange={onChange}
                      isOpen={isOpen}
                      setIsOpen={setIsOpen}
                    />
                  </div>
                )}
              />
              <ErrorText>
                {errors.tags && errors.tags.type === "required"
                  ? "* 태그를 하나 이상 선택해 주세요!"
                  : " "}
              </ErrorText>
              <div className="weekbarWrapper">
                <span>진행요일</span>
                <Controller
                  name="dayOfWeek"
                  control={control}
                  rules={{ required: true }}
                  render={({ field: { onChange } }) => (
                    <CreateWeekBar
                      onChange={onChange}
                      error={
                        errors.dayOfWeek && errors.dayOfWeek.type === "required"
                          ? true
                          : false
                      }
                    />
                  )}
                />
                <ErrorText>
                  {errors.dayOfWeek && errors.dayOfWeek.type === "required"
                    ? "* 요일을 하나 이상 선택해 주세요!"
                    : " "}
                </ErrorText>
              </div>
              <div>
                인원 :{" "}
                <input
                  id="want"
                  type="number"
                  min="1"
                  placeholder="0"
                  className={
                    errors.want && errors.want.type === "required"
                      ? "errorBorder person"
                      : "person"
                  }
                  {...register("want", { required: true })}
                />
                명
              </div>
              <ErrorText>
                {errors.want && errors.want.type === "required"
                  ? "* 인원을 선택해 주세요!"
                  : " "}
              </ErrorText>
              <div>
                <Controller
                  name="onOff"
                  control={control}
                  render={({ field: { onChange } }) => (
                    <ToggleOnline onChange={onChange} />
                  )}
                />
                <Controller
                  name="openClose"
                  control={control}
                  render={({ field: { onChange } }) => (
                    <TogglePublic onChange={onChange} />
                  )}
                />
              </div>
              <div className="dateWrapper">
                <span>시작날짜</span>
                <Controller
                  name="startDate"
                  control={control}
                  rules={{ required: true }}
                  render={({ field: { onChange } }) => (
                    <DatePickerDiv
                      className={
                        errors.startDate && errors.startDate.type === "required"
                          ? "errorBorder"
                          : "ㅤ"
                      }
                    >
                      <DatePicker
                        className="datepicker"
                        placeholderText="click and select the date"
                        dateFormat="yyyy/MM/dd"
                        selected={startDate}
                        onChange={(date: any) => {
                          setStartDate(date);
                          onChange(date.toISOString().split("T")[0]);
                        }}
                      />
                    </DatePickerDiv>
                  )}
                />
                <ErrorText>
                  {errors.startDate && errors.startDate.type === "required"
                    ? "* 날짜를 선택해 주세요!"
                    : " "}
                </ErrorText>
              </div>
              <label htmlFor="content">내용</label>
              <div>
                <Controller
                  name="content"
                  control={control}
                  rules={{ required: true }}
                  render={({ field: { onChange } }) => (
                    <textarea
                      id="content"
                      className={
                        errors.startDate && errors.startDate.type === "required"
                          ? "errorBorder textArea"
                          : "textArea"
                      }
                      onChange={onChange}
                      ref={textRef}
                      onInput={handleResizeHeight}
                    />
                  )}
                />
              </div>
              <ErrorText>
                {errors.content && errors.content.type === "required"
                  ? "* 내용을 입력해 주세요!"
                  : " "}
              </ErrorText>
              <div>
                <RedButton type="submit">Create Study</RedButton>
              </div>
            </Form>
          </ContentDiv>
        </Main>
      )}
    </>
  );
};
export default CreateForm;

// 메인 베이지색 외형
const Main = styled.main`
  width: 1024px;
  margin: 0 auto;
  background-color: var(--beige-00);
  //모바일
  @media screen and (max-width: 768px) {
    width: 100%;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1200px) {
    width: 750px;
  }
  * {
    font-family: "mainB", Arial;
  }
  button {
    border-style: none;
  }
`;
//내뷰 480px
const ContentDiv = styled.div`
  //모바일
  @media screen and (max-width: 768px) {
    padding-left: 20px;
  }
  margin: 0 auto;
  padding: 80px 0;
  width: 480px;
  font-size: 35px;
  color: var(--green);
  font-family: "mainEB";
`;
// form
const Form = styled.form`
  width: 330px;
  margin-top: 45px;
  margin-left: 60px;
  font-size: 20px;
  display: flex;
  flex-direction: column;
  > .tagSection {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    justify-content: center;
  }
  .tagAddButton {
    display: flex;
  }
  .AddButton {
    display: flex;
    align-items: center;
    :hover {
      cursor: pointer;
    }
  }
  > .weekbarWrapper {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    > span {
      margin-bottom: 5px;
    }
  }
  > input {
    margin: 5px 0;
    font-family: "mainM", Arial;
    height: 30px;
    background-color: var(--beige-00);
    border: solid 2px var(--green);
    border-radius: 30px;
    padding: 4px 15px 4px 15px;
  }
  > input:focus {
    outline: none;
  }
  > .dateWrapper {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    > span {
      margin-bottom: 5px;
    }
  }
  > div {
    margin: 5px 0;
    display: flex;
    align-items: center;
    > .person {
      background-color: var(--mopo-00);
      border: 0px solid rgba(0, 0, 0, 1);
      padding-left: 8px;
      border-radius: var(--radius-20);
    }
    > textarea {
      background-color: var(--beige-00);
      border: solid 2px var(--green);
      border-radius: 10px;
      width: 100%;
      color: var(--green);
      max-height: 80px;
      resize: none;
      overflow: auto;
      padding: 8px;
      padding-right: 5px;
    }
    > textarea:focus {
      outline: none;
    }
    .textArea::-webkit-scrollbar {
      width: 5px; /* 스크롤바의 너비 */
    }

    .textArea::-webkit-scrollbar-thumb {
      height: 20%; /* 스크롤바의 길이 */
      background: var(--green); /* 스크롤바의 색상 */
      border-radius: 10px;
    }

    .textArea::-webkit-scrollbar-track {
      background: none; /*스크롤바 뒷 배경 색상*/
    }
  }
  .person {
    margin-left: 8px;
    width: 30px;
  }
  .toggleBox {
    font-size: 12px;
    margin-right: 25px;
  }
  .datepicker {
    :hover {
      cursor: pointer;
    }
  }
  .errorBorder {
    border: solid 2px var(--red-00);
  }
  .errorIcon {
    color: var(--red-00);
  }
`;

const RedButton = styled.button`
  width: 240px;
  padding: 3px;
  background-color: var(--red-00);
  color: var(--beige-00);
  border-radius: var(--radius-30);
  font-family: "mainL", Arial;
  font-size: 22px;
  :hover {
    background-color: var(--red-10);
  }
`;
const ErrorText = styled.div`
  color: var(--red-00);
  margin: 0;
  padding-left: 20px;
  font-size: 14px;
`;
const DatePickerDiv = styled.div`
  margin: 5px 0;
  border-radius: 5px;
`;
