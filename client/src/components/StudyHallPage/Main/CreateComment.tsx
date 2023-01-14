<<<<<<< HEAD
import { useForm } from "react-hook-form";
import styled from "styled-components";
import { FaBeer } from "react-icons/fa";

const CreateComment = () => {
  const { register, handleSubmit } = useForm();

  return (
    <CreateWrapper>
      <Wrapper onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}>
        <Create>
          <img className="profile" />
          <Comment id="comment" type="text" {...register("comment")} />
          <AddButton type="submit">Add</AddButton>
        </Create>
        <CheckBox>
          <StyledInput type="checkbox" id="checkBox" {...register("secret")} />
          <div className="private">비공개</div>
        </CheckBox>
      </Wrapper>
    </CreateWrapper>
  );
=======
import styled from "styled-components";

const CreateComment = () => {
  return <CreateWrapper>Create</CreateWrapper>;
>>>>>>> dev
};

export default CreateComment;

const CreateWrapper = styled.div`
  * {
    font-family: "mainEB";
    font-size: 14px;
    color: var(--beige-00);
  }
  background-color: var(--green);
  color: var(--beige-00);
<<<<<<< HEAD
  padding-top: 20px;
`;

const Wrapper = styled.form`
  width: 460px;
  height: 50px;
  margin: 0px auto;
  border: 1px solid var(--beige-00);
  border-radius: 30px;
`;

const Create = styled.div`
  display: flex;
`;

const AddButton = styled.button`
  font-family: "mainL";
  font-size: 12px;
  width: 50px;
  height: 23px;
  color: var(--beige-00);
  border: 1px solid var(--beige-00);
  background-color: var(--green);
  border-radius: 30px;
  margin: 10px 0px 0px 0px;
`;

const Comment = styled.input`
  color: var(--green);
  background-color: var(--beige-00);
  border: solid 1px;
  border-radius: 30px;
  width: 300px;
  height: 20px;
  padding-left: 10px;
  margin: 10px 20px 0px 40px;
`;

const CheckBox = styled.div`
  display: flex;
  margin-left: 43px;

  > .private {
    font-family: "mainL";
    font-size: 8px;
    margin: 3px 0px 3px 0px;
  }
`;

const StyledInput = styled.input`
  color: var(--green);
  appearance: none;
  width: 8px;
  height: 8px;
  border: 0.5px solid gainsboro;
  border-radius: 0.35rem;
  &:checked {
    border-color: transparent;
    background-image: url("data:image/svg+xml,%3csvg viewBox='0 0 16 16' fill='white' xmlns='http://www.w3.org/2000/svg'%3e%3cpath d='M5.707 7.293a1 1 0 0 0-1.414 1.414l2 2a1 1 0 0 0 1.414 0l4-4a1 1 0 0 0-1.414-1.414L7 8.586 5.707 7.293z'/%3e%3c/svg%3e");
    background-size: 100% 100%;
    background-position: 50%;
    background-repeat: no-repeat;
    background-color: var(--green);
  }
=======
>>>>>>> dev
`;
