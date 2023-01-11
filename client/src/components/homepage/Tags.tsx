import styled from "styled-components";

import { TagData } from "../../util/dummyData";

const Tags = () => {
  return (
    <TagWrapper>
      {TagData.data.tags.map((el, idx) => (
        <Tag key={idx}>{el}</Tag>
      ))}
    </TagWrapper>
  );
};

const TagWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-content: flex-start;
  width: 320px;
  //모바일
  @media screen and (max-width: 768px) {
    width: 160px;
  }
`;
const Tag = styled.span`
  height: 16px;
  background-color: var(--mopo-00);
  border-radius: var(--radius-30);
  min-width: 40px;
  padding: 4px;
  text-align: center;
  margin-bottom: 7px;
  :hover {
    transition-duration: 0.2s;
    background-color: var(--mopo-10);
    cursor: pointer;
  }
`;

export default Tags;
