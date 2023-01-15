import styled from "styled-components";
import { Controller, useForm } from "react-hook-form";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useState, useRef, useCallback } from "react";
import { AiOutlinePlusCircle } from "react-icons/ai";

import WeekBar from "../WeekBar";
import Toggle from "../Toggle";
import Tags from "./Tags";

const CreateForm = () => {
  const [startDate, setStartDate] = useState(new Date());
  const [isOpen, setIsOpen] = useState(false);
  const [tags, setTags] = useState([
    "가나다",
    "공부",
    "영어",
    "수학",
    "사회",
    "과학",
    "IT",
    "영어회화",
  ]);
  const [selectedTags, setSelectedTags] = useState(["가나다"]);
  const { register, handleSubmit, control } = useForm();
  const textRef = useRef<HTMLTextAreaElement>(null);
  const handleResizeHeight = useCallback(() => {
    if (textRef.current) {
      textRef.current.style.height = "16px";
      textRef.current.style.height = textRef.current.scrollHeight + "px";
    }
  }, []);

  const openModal = () => {
    setIsOpen(!isOpen);
  };

  return (
    <Main>
      <ContentDiv>
        Create New Study
        <Form onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}>
          <label htmlFor="title">Team Name</label>
          <input id="title" type="text" {...register("title")} />
          <label htmlFor="text">한 줄 설명</label>
          <input id="text" type="text" {...register("text")} />
          <div className="tagSection">
            <div className="tagAddButton">
              Tags&nbsp;
              <AiOutlinePlusCircle onClick={openModal} className="AddButton" />
            </div>
            {isOpen && <AddTagsModal>hi</AddTagsModal>}
            <TagsWrapper>
              {selectedTags.map((el, idx) => (
                <Tags key={idx} tagName={el} />
              ))}
            </TagsWrapper>
          </div>
          <div className="weekbarWrapper">
            <span>진행요일</span>
            <WeekBar />
          </div>
          <div>
            인원 :{" "}
            <input
              className="person"
              id="person"
              type="number"
              {...register("person")}
              min="1"
              placeholder="1"
            />
            명
          </div>
          <div>
            <div className="toggleBox">
              Online
              <Toggle />
            </div>
            <div className="toggleBox">
              Public
              <Toggle />
            </div>
          </div>
          <div className="dateWrapper">
            <span>시작날짜</span>
            <Controller
              name="date"
              control={control}
              render={({ field: { onChange } }) => (
                // antd의 datepicker에서 e.target.value는
                // moment 객체 그대로를 반환하기에,
                // "2021-04-15"와 같은 값을 얻고싶다면, 두번째 파라미터
                // "dateString"을 추가해서 값을 넣어야 한다.
                <DatePicker
                  dateFormat="yyyy/MM/dd"
                  selected={startDate}
                  onChange={(date: any) => {
                    setStartDate(date);
                    console.log(date);
                    onChange(date);
                  }}
                />
              )}
            />
          </div>
          <label htmlFor="content">내용</label>
          <div>
            <textarea
              id="content"
              className="textArea"
              {...register("content")}
              ref={textRef}
              onInput={handleResizeHeight}
            />
          </div>
          <div>
            <RedButton type="submit">Create Study</RedButton>
          </div>
        </Form>
      </ContentDiv>
    </Main>
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
    .AddButton {
      :hover {
        cursor: pointer;
      }
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
    margin: 5px 0 20px;
    font-family: "mainM", Arial;
    height: 30px;
    background-color: var(--mopo-00);
    border: none;
    padding: 4px;
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
    margin: 5px 0 20px;
    display: flex;
    align-items: center;
    > .person {
      background-color: var(--mopo-00);
      border: none;
      padding-left: 8px;
      border-radius: var(--radius-20);
    }
    > textarea {
      background-color: var(--mopo-00);
      border: none;
      width: 100%;
      color: var(--beige-00);
      max-height: 80px;
      resize: none;
      overflow: auto;
      padding: 8px;
    }
    > textarea:focus {
      outline: none;
    }
    .textArea::-webkit-scrollbar {
      width: 8px; /* 스크롤바의 너비 */
    }

    .textArea::-webkit-scrollbar-thumb {
      height: 30%; /* 스크롤바의 길이 */
      background: rgba(255, 255, 255, 0.15); /* 스크롤바의 색상 */
      border-radius: 10px;
    }

    .textArea::-webkit-scrollbar-track {
      background: rgba(0, 0, 0, 0.2); /*스크롤바 뒷 배경 색상*/
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
`;
const StartDateButton = styled.button`
  background-color: #dfe4e0;
  width: 200px;
  font-family: "mainM", Arial;
  border: 1px solid var(red);
  border-radius: var(--radius-10);
  padding: 6px;
  :hover {
    border: 1px solid var(--green-00);
    padding: 5px;
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

const AddTagsModal = styled.div``;
const TagsWrapper = styled.div`
  margin-top: 5px;
`;
