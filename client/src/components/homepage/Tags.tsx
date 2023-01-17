import styled from "styled-components";

import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";

interface Data {
  data: any;
}

const Tags = () => {
  const [tagList, setTagList] = useState<string[]>();
  const getTags = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url);
  };
  useEffect(() => {
    const url =
      "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/tag";
    getTags(url).then((res) => setTagList(res.data.data.tags));
  }, []);
  return (
    <TagWrapper>
      {tagList && tagList.map((el, idx) => <Tag key={idx}>{el}</Tag>)}
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
