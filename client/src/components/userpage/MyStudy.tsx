import React from "react";
import styled from "styled-components";

interface BoxProps {
  teamName: string;
  imgUrl: string;
}

const MyStudy = ({ imgUrl, teamName }: BoxProps) => {
  console.log(imgUrl, teamName);
  return (
    <Main>
      <Container>
        <Box>
          <img src={imgUrl} />
          <div className="study-name">{teamName}</div>
        </Box>
      </Container>
    </Main>
  );
};

export default MyStudy;

const Box = styled.div`
  display: flex;
  width: 273px;
  height: 100px;
  background-color: var(--mopo-00);
  border-radius: 10px;
  margin: 10px;
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
`;

const Container = styled.div`
  background-color: var(--beige-00);
`;

const Main = styled.div`
  background-color: var(--beige-00);
`;
