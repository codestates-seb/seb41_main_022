import styled from "styled-components";
import { useForm } from "react-hook-form";
import WeekBar from "./WeekBar";

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
          <button type="submit">로그인</button>
          <WeekBar />
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
`;
