import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import styled from "styled-components";
import { useCookies } from "react-cookie";

import MyStudy from "./MyStudy";

interface MyStudyList {
  studyId: number;
  teamName: string;
  imgUrl: string;
  studies: [];
}

interface Data {
  data: any;
}

const MyStudyList = () => {
  const [cookies] = useCookies(["token", "userData"]);
  const [myStudyList, setMyStudyList] = useState<MyStudyList[] | undefined>();
  const getMyStudyData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
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
          <MyStudy
            key={el.studyId}
            studyId={el.studyId}
            teamName={el.teamName}
            imgUrl={el.imgUrl}
          />
        ))}
    </MyStudyWrapper>
  );
};

const MyStudyWrapper = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin: 0 100px;
`;

export default MyStudyList;
