import React from "react";
import styled from "styled-components";

interface BoxProps {
  teamName: string;
  image: string;
}

const MyStudy = ({ image, teamName }: BoxProps) => {
  return (
    <Main>
      <Container>
        <Box>
          <img src={image} />
          <div className="study-name">{teamName}</div>
        </Box>
      </Container>
    </Main>
  );
};

export default MyStudy;

const img = styled.div`
  width: 70px;
  height: 70px;
  background-color: #b0ac93;
  border-radius: var(--radius-30);
  margin: auto 0px;
  margin-left: 15px;
`;

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
`;

const Container = styled.div`
  background-color: var(--beige-00);
`;

const Main = styled.div`
  background-color: var(--beige-00);
`;
