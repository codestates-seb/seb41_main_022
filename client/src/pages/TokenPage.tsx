import React, { useEffect } from "react";
import { useNavigate, useParams, useSearchParams } from "react-router-dom";
import { useCookies } from "react-cookie";

const TokenPage = () => {
  const navigate = useNavigate();
  const [searchParams, SetSearchParams] = useSearchParams();
  const accessToken = searchParams.get("access-Token");
  const refreshToken = searchParams.get("refresh-Token");
  const [cookies, setCookie, removeCookie] = useCookies(["token"]);
  useEffect(() => {
    setCookie("token", { accessToken, refreshToken }, { path: "/" });
    navigate("/");
  }, []);
  return <></>;
};
export default TokenPage;
