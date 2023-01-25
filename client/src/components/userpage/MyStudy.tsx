import React from "react";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

interface BoxProps {
  studyId: number;
  teamName: string;
  imgUrl: string;
}

const MyStudy = ({ studyId, imgUrl, teamName }: BoxProps) => {
  const navigate = useNavigate();
  return (
    <Main>
      <Box onClick={() => navigate(`/study-hall/main/${studyId}`)}>
        <img src={imgUrl} />
        <div className="study-name">{teamName.toString()}</div>
      </Box>
    </Main>
  );
};

export default MyStudy;

const Main = styled.div`
  background-color: var(--beige-00);
  height: 100%;
`;

const Box = styled.button`
  display: flex;
  width: 254px;
  height: 100px;
  background-color: var(--mopo-00);
  border-radius: 10px;
  margin: 10px;
  border-style: none;
  > .study-name {
    margin-left: 20px;
    margin-top: 20px;
  }
  > .img {
    width: 70px;
    height: 70px;
    background-color: #b0ac93;
    border-radius: var(--radius-30);
    margin: auto 0px;
    margin-left: 15px;
  }
  :hover {
    background-color: var(--mopo-10);
  }
`;
