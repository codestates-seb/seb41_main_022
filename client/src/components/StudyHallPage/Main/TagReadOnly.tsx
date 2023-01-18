import styled from "styled-components";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";

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
  height: 16px;
  background-color: var(--mopo-00);
  border-radius: var(--radius-30);
  min-width: 40px;
  padding: 4px;
  text-align: center;
  margin: 0 4px 7px 0;
`;

export default TagReadOnly;
