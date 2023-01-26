import React from "react";
import styled from "styled-components";

import EditForm from "../components/editStudy/EditForm";

const CreatePage = () => {
  return (
    <EditFormDiv>
      <EditForm />
    </EditFormDiv>
  );
};

export default CreatePage;
const EditFormDiv = styled.div`
  background-color: var(--green);
  height: auto;
  width: 100%;
  padding: 100px 0;
`;
