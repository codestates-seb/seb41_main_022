import styled from "styled-components";
import axios, { AxiosResponse } from "axios";
import { useState, useEffect } from "react";

import { useCookies } from "react-cookie";

interface User {
  studyCount: number;
}

interface Data {
  data: any;
}

const TotalClass = () => {
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
    getUserdata(process.env.REACT_APP_API_URL + "/study/user").then((res) => {
      setUserData(res.data.data);
      console.log(res.data.data);
    });
  }, []);

  return (
    <>
      <Classes>{userData && userData.studyCount} studies</Classes>
    </>
  );
};

export default TotalClass;

const Classes = styled.div`
  color: var(#aaaaaa);
  font-size: 12px;
  display: flex;
  flex-wrap: wrap;
  height: auto;
`;
