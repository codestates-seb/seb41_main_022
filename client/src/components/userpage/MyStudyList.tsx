import { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import styled from "styled-components";
import { useCookies } from "react-cookie";

import MyStudy from "./MyStudy";

interface MyStudyList {
  studyId: number;
  teamName: string;
  image: string;
  summary: string;
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
    getMyStudyData(process.env.REACT_APP_API_URL + "/study/user").then(
      (res) => {
        setMyStudyList(res.data.data.studies);
      }
    );
  }, []);

  return (
    <MyStudyWrapper>
      {myStudyList &&
        myStudyList.map((el) => (
          <MyStudy
            key={el.studyId}
            studyId={el.studyId}
            teamName={el.teamName}
            summary={el.summary}
            imgUrl={el.image}
          />
        ))}
    </MyStudyWrapper>
  );
};

const MyStudyWrapper = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin: 0 150px;
`;

export default MyStudyList;
