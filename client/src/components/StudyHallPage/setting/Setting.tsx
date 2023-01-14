import styled from "styled-components";

import RedButton from "./RedButton";
import ApplicationData from "../../../util/data/dummydataSetting";
import Application from "./Application";

const Setting = () => {
  return (
    <SettingPageWrapper>
      <SettingSection>
        <div className="contentWrapper">
          <span className="text textSetting">Setting</span>
          <div className="buttonWrapper">
            <RedButton text="Delete Study"></RedButton>
            <RedButton text="Leave Study"></RedButton>
          </div>
        </div>
      </SettingSection>
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
  background-color: var(--beige-00);
  min-height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  > .contentWrapper {
    width: 447px;
    display: flex;
    flex-direction: column;
    > .textSetting {
      color: var(--green);
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
  background-color: var(--green);
  display: flex;
  flex-direction: column;
  justify-content: center;
  > article {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 32px;
    > .textApplication {
      color: var(--beige-00);
      margin-right: calc(400px - 80px);
    }
    .contentWrapper {
      margin-top: 16px;
      width: 447px;
      border: 1px solid var(--beige-00);
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
