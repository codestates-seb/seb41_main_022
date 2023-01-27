import React, { useState, useEffect } from "react";
import axios, { AxiosResponse } from "axios";
import styled from "styled-components";
import { useCookies } from "react-cookie";

import MyStudy from "./MyStudy";
import UserStudySkeleton from "./UserStudySkeleton";

interface MyStudyList {
  studyId: number;
  teamName: string;
  image: string;
  summary: string;
}

interface Data {
  data: any;
}

interface treeType {
  treeId: number;
  treePoint: number;
  treeImage: string;
  createdAt: string;
  makeMonth: number;
  teamName: string;
}

const MyStudyList = () => {
  const [cookies] = useCookies(["token", "userData"]);
  const [myStudyList, setMyStudyList] = useState<MyStudyList[] | undefined>();
  const [treeData, setTreeData] = useState<treeType[] | undefined>();
  const getMyStudyData = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      },
    });
  };
  useEffect(() => {
    setTimeout(() => {
      getMyStudyData(process.env.REACT_APP_API_URL + "/study/user").then(
        (res) => {
          setMyStudyList(res.data.data.studies);
        }
      );
    }, 500);
  }, []);
  useEffect(() => {
    axios
      .get(process.env.REACT_APP_API_URL + "/tree/user", {
        headers: {
          "access-Token": cookies.token.accessToken,
          "refresh-Token": cookies.token.refreshToken,
        },
      })
      .then((res) => {
        setTreeData(res.data.data.trees);
      });
  }, []);
  const getTree = (propsTeamName: string) => {
    if (treeData) {
      return treeData.find((el) => el.teamName === propsTeamName);
    } else {
      return [];
    }
  };
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
            tree={getTree(el.teamName)}
          />
        ))}
      {!myStudyList && <UserStudySkeleton />}
    </MyStudyWrapper>
  );
};

const MyStudyWrapper = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  margin: 0 150px 16px 150px;
  height: 100%;
`;

export default MyStudyList;
