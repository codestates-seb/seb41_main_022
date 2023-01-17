import React from "react";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";

import UserData from "../../util/dummyDataUser";
import { User } from "../../util/dummyDataStudyHall";
import Data from "../../util/dummyData";

interface User {
  data: any;
  userId: number;
  username: string;
  imgUrl: string;
}

const Profile = () => {
  const [obj, setObj] = useState<string[]>(); //

  const fetch = (url: string): Promise<AxiosResponse<User>> => {
    return axios.get(url, {
      headers: {
        "access-Token": "abc",
      },
    });
  };
  useEffect(() => {
    const urlUser =
      "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/user";
    fetch(urlUser).then((res) => console.log(res.data.data));
  }, []);

  return (
    <Main>
      <Container>
        <Banner>
          <img src={User.data.imgUrl} />
          <div className="info">
            <UserName>{User.data.username}</UserName>
            <div className="class-num">{UserData.data.studyCount} studies</div>
          </div>
        </Banner>
      </Container>
    </Main>
  );
};

export default Profile;

const Main = styled.div`
  /* background-color: var(--beige-00); */
  width: 1024px;
  height: 300px;
  margin: 0px auto;
`;

const UserName = styled.div``;

const Container = styled.div`
  height: 100vh;
  display: flex;
  background-color: var(--beige-00);
`;
const Banner = styled.div`
  width: 1024px;
  height: 183px;
  margin: 0px auto;
  background-color: var(--mopo-00);
  display: flex;
  position: absolute;
  > img {
    width: 85px;
    height: 85px;
    background-color: #b0ac93;
    border-radius: var(--radius-30);
    margin-top: 160px;
    margin-left: 11%;
    align-items: center;
  }
  > .info {
    color: var(--beige-00);
    margin-left: 10px;
    margin-top: 190px;
    > .class-num {
      color: var(#aaaaaa);
      font-size: 12px;
      display: flex;
      flex-wrap: wrap;
      height: auto;
    }
  }
`;
