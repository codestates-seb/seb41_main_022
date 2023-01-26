import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import ScrollTop from "./components/ScrollTop";
import axios from "axios";
import { Cookies } from "react-cookie";

const cookies = new Cookies();
axios.interceptors.response.use((res) => {
  if (res.headers["access-token"]) {
    let refreshToken = cookies.get("token").refreshToken;
    cookies.remove("token");
    cookies.set("token", {
      accessToken: res.headers["access-token"],
      refreshToken,
    });
    window.location.reload();
  }
  return res;
});

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <BrowserRouter>
    <ScrollTop />
    <App />
  </BrowserRouter>
);
