import styled from "styled-components";
//내부 임포트
import Tags from "../../homepage/Tags";

//받아오는 인자
interface InfoProps {
  teamName: string;
  summary: string;
  content: string;
}

const StudyInfo = ({ teamName, summary, content }: InfoProps) => {
  return (
    <InfoWrapper>
      <Info>
        <Title>{teamName}</Title>
        <Summary>{summary}</Summary>
        <Tags />
        <Content>{content}</Content>
      </Info>
    </InfoWrapper>
  );
};

export default StudyInfo;

//전체 배경색, 글로벌 폰트사이즈 및 색상
const InfoWrapper = styled.div`
  * {
    font-family: "mainM", Ariel;
    font-size: 14px;
    color: var(--green);
  }
  background-color: var(--beige-00);
`;

//박스 크기, 중앙 정렬
const Info = styled.div`
  width: 460px;
  margin: 0px auto;
  padding-top: 100px;
`;

//Title 글씨크기
const Title = styled.div`
  font-family: "mainB";
  font-size: 35px;
  padding-top: 50px;
`;

const Summary = styled.div`
  width: 280px;
  padding-top: 50px;
  padding-bottom: 50px;
  font-size: 16px;
  font-family: "mainB";
`;

const Content = styled.div`
  width: 350px;
  padding-top: 30px;
  padding-bottom: 50px;
`;
