import styled from "styled-components";

interface TagsProps {
  tagName: string;
  setSelectedTags?: any;
  selectedTags?: string[];
}

const CreatePageTags = ({
  tagName,
  setSelectedTags,
  selectedTags,
}: TagsProps) => {
  const addTag = () => {
    if (selectedTags) {
      if (selectedTags?.includes(tagName)) {
        return;
      } else {
        setSelectedTags([...selectedTags, tagName]);
      }
    }
  };

  return (
    <>
      <Tag onClick={addTag}>{tagName}</Tag>
    </>
  );
};

const Tag = styled.div`
  font-family: "mainB";
  font-size: 14px;
  height: 16px;
  background-color: var(--mopo-00);
  border-radius: var(--radius-30);
  min-width: 40px;
  padding: 4px;
  text-align: center;
  margin: 2px;
  :hover {
    transition-duration: 0.2s;
    background-color: var(--mopo-10);
    cursor: pointer;
  }
`;

export default CreatePageTags;
