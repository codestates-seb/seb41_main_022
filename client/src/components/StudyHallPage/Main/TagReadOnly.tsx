import styled from "styled-components";

interface TagProps {
  tags: string[] | undefined;
}

const TagReadOnly = ({ tags }: TagProps) => {
  return (
    <TagWrapper>
      {tags && tags.map((el, idx) => <Tag key={idx}>{el}</Tag>)}
    </TagWrapper>
  );
};

const TagWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  align-content: flex-start;
  width: 320px;
`;
const Tag = styled.span`
  font-family: "BannerL";
  font-size: 12px;
  height: 16px;
  background-color: var(--mopo-00);
  border-radius: var(--radius-30);
  min-width: 40px;
  padding: 2px 7px 2px 4px;
  text-align: center;
  margin: 0 4px 7px 0;
`;

export default TagReadOnly;
