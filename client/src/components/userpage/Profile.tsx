import React from "react";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";

import { useCookies } from "react-cookie";
import TotalClass from "./TotalClass";

interface User {
  userId: number;
  username: string;
  imgUrl: string;
  data: any;
}
interface Data {
  data: any;
}

const Profile = () => {
  const [cookies, setCookie] = useCookies(["token", "userData"]);
  const [userData, setUserData] = useState<User | undefined>();
  const getUserdata = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      },
    });
  };
  useEffect(() => {
    getUserdata(
      "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/user"
    ).then((res) => {
      setUserData(res.data.data);
      console.log(res.data.data);
    });
  }, []);

  return (
    <Main>
      <Container>
        <Banner>
          <img src={userData && userData.imgUrl} />
          <div className="info">
            <UserName>{userData && userData.username}</UserName>
            <TotalClass />
          </div>
        </Banner>
      </Container>
    </Main>
  );
};

export default Profile;

const Main = styled.div`
  width: 1024px;
  height: 300px;
  margin: 0px auto;
`;

const UserName = styled.div``;

const Container = styled.div`
  height: 100%;
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
  }
`;
