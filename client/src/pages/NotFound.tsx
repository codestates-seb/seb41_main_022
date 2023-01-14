import styled from "styled-components";
import { Link } from "react-router-dom";

import AppleTree from "../assets/AppleTree.svg";

const NotFound = () => {
  return (
    <Wrap>
      <Div>
        <img src={AppleTree} />
      </Div>
      <Div>
        <Div>
          <Title>Page not found</Title>
        </Div>
        <Div>
          <Msg>We're sorry, we couldn't find the page you requested.</Msg>
          <Msg>
            Please return to our{" "}
            <Link className="link" to={"/"}>
              main page
            </Link>
          </Msg>
        </Div>
      </Div>
    </Wrap>
  );
};

const Wrap = styled.div`
  * {
    font-family: "mainM";
    color: var(--beige-00);
  }
  display: flex;
  background-color: var(--green);
  vertical-align: baseline;
  height: 100vh;
  justify-content: center;
  align-items: center;
  text-align: left;
  letter-spacing: normal;
  -webkit-font-smoothing: antialiased;
  font-weight: 700px;
`;
const Div = styled.div`
  margin-right: 38px;
  position: relative;
  .icon {
    position: relative;
    bottom: 55px;
  }
`;

const Title = styled.h1`
  font-size: 32px;
  font-family: "mainB";

  line-height: 35px;
  margin: 0px 0px 4px;
`;
const Msg = styled.p`
  clear: both;
  font-size: 19px;
  margin: 0px 0px 19px;
  line-height: 25px;
  > .link {
    color: var(--blue);
    :hover {
      color: #0a95ff;
    }
  }
`;
const List = styled.p`
  font-size: 15px;
  line-height: 19.6px;
  margin: 0px 0px 15px;
`;
export default NotFound;
