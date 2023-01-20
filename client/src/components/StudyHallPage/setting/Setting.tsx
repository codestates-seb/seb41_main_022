import styled from "styled-components";
import { useParams } from "react-router-dom";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useCookies } from "react-cookie";

import RedButton from "./RedButton";
import ApplicationData from "../../../util/data/dummydataSetting";
import Application from "./Application";

const Setting = () => {
  const { studyId } = useParams();
  const navigate = useNavigate();
  const [cookies] = useCookies(["token", "userData", "authData"]);

  const handleClickDeleteStudy = () => {
    axios
      .delete(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}`,
        {
          headers: {
            "access-Token": cookies.token.accessToken,
            "refresh-Token": cookies.token.refreshToken,
          },
        }
      )
      .then(() => navigate("/"));
  };
  const handleClickLeaveStudy = () => {
    axios
      .delete(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/study/${studyId}/${cookies.userData.userId}`,
        {
          headers: {
            "access-Token": cookies.token.accessToken,
            "refresh-Token": cookies.token.refreshToken,
          },
        }
      )
      .then(() => navigate("/"));
  };
  return (
    <SettingPageWrapper>
      <ApplicationSection>
        <article>
          <span className="text textApplication">Application</span>
          <div className="contentWrapper">
            <ApplicationWrapper>
              <ul>
                {ApplicationData.data.map((el) => (
                  <li>
                    <Application
                      key={el.userId}
                      username={el.username}
                      imgUrl={el.imgUrl}
                    />
                  </li>
                ))}
              </ul>
            </ApplicationWrapper>
          </div>
        </article>
      </ApplicationSection>
      <SettingSection>
        <div className="contentWrapper">
          <span className="text textSetting">Setting</span>
          <div className="buttonWrapper">
            {cookies.authData && cookies.authData.data.host ? (
              <RedButton
                handleClick={handleClickDeleteStudy}
                text="Delete Study"
              ></RedButton>
            ) : (
              <RedButton
                handleClick={handleClickLeaveStudy}
                text="Leave Study"
              ></RedButton>
            )}
          </div>
        </div>
      </SettingSection>
    </SettingPageWrapper>
  );
};

const SettingPageWrapper = styled.main`
  * {
    font-family: "mainM";
  }
  .text {
    font-family: "mainB";
    font-size: 28px;
  }
`;
const SettingSection = styled.section`
  background-color: var(--green);
  min-height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  > .contentWrapper {
    width: 447px;
    display: flex;
    flex-direction: column;
    > .textSetting {
      color: var(--beige-00);
    }
    .buttonWrapper {
      display: flex;
      flex-direction: column;
      align-items: center;
      button {
        margin-top: 16px;
      }
    }
  }
`;
const ApplicationSection = styled.section`
  min-height: 200px;
  background-color: var(--beige-00);
  display: flex;
  flex-direction: column;
  justify-content: center;
  > article {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 32px;
    > .textApplication {
      color: var(--green);
      margin-right: calc(400px - 80px);
    }
    .contentWrapper {
      margin-top: 16px;
      width: 447px;
      border: 1px solid var(--green);
      border-radius: var(--radius-20);
      padding: 8px 8px 24px 8px;
    }
  }
`;
const ApplicationWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default Setting;
