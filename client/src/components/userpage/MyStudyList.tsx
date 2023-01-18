import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import styled from "styled-components";

import MyStudy from "./MyStudy";

interface MyStudyList {
  studyId: number;
  teamName: string;
  image: string;
  studies: [];
}

interface Data {
  data: any;
}

const MyStudyList = () => {
  const [myStudyList, setMyStudyList] = useState<MyStudyList[] | undefined>();
  const getMyStudyData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": "abcd",
      },
    });
  };
  useEffect(() => {
    getMyStudyData(
      "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/user"
    ).then((res) => {
      setMyStudyList(res.data.data.studies);
    });
  }, []);

  return (
    <MyStudyWrapper>
      {myStudyList &&
        myStudyList.map((el) => (
          <MyStudy key={el.studyId} teamName={el.teamName} image={el.image} />
        ))}
    </MyStudyWrapper>
  );
};

const MyStudyWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  height: auto;
  margin-left: 10%;
`;

export default MyStudyList;
