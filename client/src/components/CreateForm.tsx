import styled from "styled-components";
import { useForm } from "react-hook-form";
import WeekBar from "./WeekBar";
import Toggle from "./Toggle";

const CreateForm = () => {
  const { register, handleSubmit } = useForm();
  return (
    <Main>
      <ContentDiv>
        Create New Study
        <Form onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}>
          <label htmlFor="title">Team Name</label>
          <input id="title" type="text" {...register("title")} />
          <label htmlFor="text">한 줄 설명</label>
          <input id="text" type="text" {...register("text")} />
          <div>Tags</div>
          <div>
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
          <div>
            <StartDateButton>시작 날짜</StartDateButton>
          </div>
          <label htmlFor="content">내용</label>
          <div>
            <textarea id="content" {...register("content")} />
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
  margin: 100px auto 0 auto;
  width: 1024px;
  background-color: var(--beige-00);
  * {
    font-family: "mainB", Arial;
  }
  button {
    border-style: none;
  }
`;
//내뷰 480px
const ContentDiv = styled.div`
  margin: 0 auto;
  padding-top: 100px;
  width: 480px;
  font-size: 35px;
  color: var(--green);
`;
// form
const Form = styled.form`
  width: 330px;
  margin-top: 45px;
  margin-left: 60px;
  font-size: 20px;
  display: flex;
  flex-direction: column;
  > input {
    margin: 5px 0 20px;
    font-family: "mainM", Arial;
  }
  > div {
    margin: 5px 0 20px;
    display: flex;
    align-items: center;
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
