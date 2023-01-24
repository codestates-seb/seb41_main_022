import React from "react";
import CreateForm from "../components/createStudy/CreateForm";
import styled from "styled-components";
import CreateFormTest from "../components/createStudy/CreateFormTest";

const CreatePage = () => {
  return (
    <CreateFormDiv>
      {/*<CreateForm />*/}
      <CreateFormTest />
    </CreateFormDiv>
  );
};

export default CreatePage;
const CreateFormDiv = styled.div`
  background-color: var(--green);
  height: auto;
  width: 100%;
  padding: 100px 0;
`;
