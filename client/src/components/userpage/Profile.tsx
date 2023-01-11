import React from "react";
import styled from "styled-components";
import UserData from "../../util/dummyDataUser";

const Profile = () => {
  return (
    <Main>
      <Container>
        <Banner>
          <Picture />
          <div className="info">
            <h3>kimhw7</h3>
            <div className="class-num">{UserData.data.studyCount} studies</div>
          </div>
        </Banner>
      </Container>
    </Main>
  );
};

const Main = styled.div`
  /* background-color: var(--beige-00); */
  width: 1024px;
  height: 300px;
  margin: 0px auto;
`;
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
  > .info {
    color: var(--beige-00);
    margin-left: 10px;
    margin-top: 190px;
    > .class-num {
      color: var(#aaaaaa);
      font-size: 12px;
    }
  }
`;
const Picture = styled.div`
  width: 85px;
  height: 85px;
  background-color: #b0ac93;
  border-radius: var(--radius-30);
  margin-top: 160px;
  margin-left: 11%;
  /* margin-left: 135px; */
`;

export default Profile;
