import styled from "styled-components";
import { Dispatch, SetStateAction, useEffect, useState } from "react";
import { AiOutlinePlusCircle } from "react-icons/ai";

interface TagsProps {
  tag: string[] | undefined;
  onChange: any;
  isOpen: boolean;
  setIsOpen: Dispatch<SetStateAction<boolean>>;
  initTags: string[];
}

const EditPageTags = ({
  tag,
  onChange,
  isOpen,
  setIsOpen,
  initTags,
}: TagsProps) => {
  const [selectedTags, setSelectedTags] = useState<string[]>([]);
  useEffect(() => {
    onChange(selectedTags);
  }, [selectedTags]);
  useEffect(() => {
    setSelectedTags(initTags);
  }, []);
  const addTag = (tagName: string) => {
    if (selectedTags) {
      if (selectedTags.includes(tagName)) {
        setSelectedTags([...selectedTags.filter((el) => el !== tagName)]);
      } else {
        setSelectedTags([...selectedTags, tagName]);
      }
    }
  };
  return (
    <>
      {isOpen && (
        <AddTagsModal>
          {tag &&
            tag.map((el, idx) => (
              <Tag key={idx} onClick={() => addTag(el)}>
                {el}
              </Tag>
            ))}
        </AddTagsModal>
      )}
      <TagsWrapper>
        {selectedTags.map((el, idx) => (
          <Tag key={idx} onClick={() => addTag(el)}>
            {el}
          </Tag>
        ))}
      </TagsWrapper>
    </>
  );
};
const AddTagsModal = styled.div`
  border: 1px solid var(--green);
  display: flex;
  flex-wrap: wrap;
  padding: 8px;
  align-items: center;
  margin-top: 5px;
  border-radius: var(--radius-20);
`;
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
const TagsWrapper = styled.div`
  margin-top: 5px;
  display: flex;
  flex-wrap: wrap;
  padding: 8px;

  align-items: center;
`;
export default EditPageTags;
