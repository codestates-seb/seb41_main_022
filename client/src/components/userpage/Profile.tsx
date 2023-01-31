import React from "react";
import styled from "styled-components";
import { useEffect, useState } from "react";
import axios, { AxiosResponse } from "axios";

import { useCookies } from "react-cookie";
import UserSkeleton from "./UserSkeleton";
import userBanner from "../.././assets/userBanner.svg";

interface User {
  userId: number;
  username: string;
  imgUrl: string;
  data: any;
}
interface Count {
  studyCount: number;
}
interface Data {
  data: any;
}

const Profile = () => {
  const [cookies, setCookie] = useCookies(["token", "userData"]);
  const [userData, setUserData] = useState<User | undefined>();
  const [studyCount, setStudyCount] = useState<Count | undefined>();
  const getUserdata = (url: string): Promise<AxiosResponse<Data>> => {
    return axios.get(url, {
      headers: {
        "access-Token": cookies.token.accessToken,
        "refresh-Token": cookies.token.refreshToken,
      },
    });
  };
  useEffect(() => {
    setTimeout(() => {
      axios
        .all([
          getUserdata(process.env.REACT_APP_API_URL + "/user"),
          getUserdata(process.env.REACT_APP_API_URL + "/study/user"),
        ])
        .then(
          axios.spread((res1, res2) => {
            setUserData(res1.data.data);
            setStudyCount(res2.data.data);
          })
        );
    }, 500);
  }, []);

  return (
    <Main>
      <Container>
        <Banner>
          {userData && studyCount && (
            <>
              <img src={userData.imgUrl} />
              <div className="info">
                <UserName>{userData.username}</UserName>
                <Classes>{studyCount.studyCount} studies</Classes>
              </div>
            </>
          )}
          {!userData && <UserSkeleton />}
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
  height: 200px;
  margin: 0px auto;
  background-image: url(${userBanner});
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
const Classes = styled.div`
  color: var(#aaaaaa);
  font-size: 12px;
  display: flex;
  flex-wrap: wrap;
  height: auto;
`;
