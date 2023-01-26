import styled from "styled-components";

interface TreeProps {
  treeId: number;
  treePoint: number;
  treeImage: string;
  createdAt: string;
  makeMonth: number;
  teamName: string;
}

const Tree = ({
  treeId,
  treePoint,
  treeImage,
  createdAt,
  makeMonth,
  teamName,
}: TreeProps) => {
  return (
    <ContentsWrap>
      <Img src={treeImage} />
    </ContentsWrap>
  );
};

export default Tree;

const ContentsWrap = styled.div`
  display: flex;
  margin: 0px, 150px, 0px, 150px;
`;

const Img = styled.img`
  position: relative;
  display: flex;
  width: 25px;
  height: 25px;
`;
