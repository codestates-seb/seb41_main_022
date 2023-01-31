import styled from "styled-components";
import { Controller, useForm, SubmitHandler } from "react-hook-form";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useState, useRef, useCallback, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import { useNavigate, useParams } from "react-router-dom";
import { useCookies } from "react-cookie";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import EditWeekBar from "./EditWeekBar";
import ToggleOnline from "./ToggleOnline";
import EditPageTags from "./EditPageTags";
import TogglePublic from "./TogglePublic";
import { createStudyStore } from "../../util/zustandCreateStudy";
import { AiOutlinePlusCircle } from "react-icons/ai";

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
interface InitData {
  teamName: string;
  summary: string;
  dayOfWeek: string[];
  want: number;
  startDate: string;
  onOff: boolean;
  openClose: boolean;
  content: string;
  image: string;
}

const EditForm = () => {
  const fetch = (url: string): Promise<AxiosResponse<any>> => {
    return axios.get(url);
  };

  const { studyId } = useParams();
  const [cookies] = useCookies(["token"]);
  const { fetchEditStudy } = createStudyStore();
  const navigate = useNavigate();
  const [initData, setInitData] = useState<InitData | undefined>(undefined);
  const [isOpen, setIsOpen] = useState(false);
  const [tag, setTag] = useState<string[] | undefined>();
  const [initTags, setInitTags] = useState<string[] | undefined>();
  const [startDate, setStartDate] = useState();
  const textRef = useRef<HTMLTextAreaElement>(null);
  const notify = () => toast.success("스터디 내용이 수정 되었습니다.");
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
    fetchEditStudy(
      form,
      {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      },
      studyId
    );
    notify();
    setTimeout(() => {
      navigate(`/study-hall/main/${studyId}`);
    }, 1500);
  };
  // 스터디 수정 초기 데이터 가져오기
  useEffect(() => {
    const url = URL + "/tag";
    fetch(url).then((res) => setTag(res.data.data.tags));
    axios.get(`${URL}/study/${studyId}`).then((res) => {
      setInitData(res.data.data);
    });
  }, []);
  useEffect(() => {
    axios
      .get(`${URL}/tag/study/${studyId}`)
      .then((res) => setInitTags(res.data.data.tags));
  }, []);
  return (
    <Main>
      <ContentDiv>
        Edit My Study
        {initData && initTags && (
          <Form onSubmit={handleSubmit(onSubmitHandler)}>
            <input
              defaultValue={initData.teamName}
              id="teamName"
              type="text"
              placeholder="Team Name"
              maxLength={10}
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
              defaultValue={initData.summary}
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
                  <EditPageTags
                    tag={tag}
                    onChange={onChange}
                    isOpen={isOpen}
                    setIsOpen={setIsOpen}
                    initTags={initTags}
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
                  <EditWeekBar
                    onChange={onChange}
                    error={
                      errors.dayOfWeek && errors.dayOfWeek.type === "required"
                        ? true
                        : false
                    }
                    initDate={initData.dayOfWeek}
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
                defaultValue={initData.want}
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
                  <ToggleOnline
                    onChange={onChange}
                    initValue={initData.onOff}
                  />
                )}
              />
              <Controller
                name="openClose"
                control={control}
                render={({ field: { onChange } }) => (
                  <TogglePublic
                    onChange={onChange}
                    initValue={initData.openClose}
                  />
                )}
              />
            </div>
            <label htmlFor="content">내용</label>
            <div>
              <Controller
                name="content"
                control={control}
                rules={{ required: true }}
                render={({ field: { onChange } }) => (
                  <textarea
                    defaultValue={initData.content}
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
              <RedButton type="submit">Edit Study</RedButton>
              <div className="toastFontsize"></div>
            </div>
          </Form>
        )}
      </ContentDiv>
    </Main>
  );
};
export default EditForm;

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
  .toastFontsize {
    font-family: "mainM";
    font-size: 16px;
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
