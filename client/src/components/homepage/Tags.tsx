import styled from "styled-components";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";
import HomeStore from "../../util/zustandHome";

interface Data {
  data: any;
}

const Tags = () => {
  const { setTags, tags, setRecruitment } = HomeStore();
  const [tagList, setTagList] = useState<string[]>();
  const [selectedTagList, setSelectedTagList] = useState<string[]>([]);
  const getTags = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url);
  };

  const handleClickTag = (tagName: string) => {
    if (selectedTagList.includes(tagName)) {
      setSelectedTagList([...selectedTagList.filter((el) => el !== tagName)]);
    } else {
      setSelectedTagList([...selectedTagList, tagName]);
    }
  };

  useEffect(() => {
    setTags(selectedTagList.join(","));
  }, [selectedTagList]);

  useEffect(() => {
    const url = process.env.REACT_APP_API_URL + "/tag";
    getTags(url).then((res) => setTagList(res.data.data.tags));
  }, []);
  return (
    <TagWrapper>
      {tagList &&
        tagList.map((el, idx) => (
          <Tag
            key={idx}
            onClick={() => handleClickTag(el)}
            className={selectedTagList.includes(el) ? "active" : undefined}
          >
            {el}
          </Tag>
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

  > .active {
    background-color: var(--mopo-10);
  }

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
