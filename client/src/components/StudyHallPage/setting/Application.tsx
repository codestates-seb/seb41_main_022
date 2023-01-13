import styled from "styled-components";
import { IoIosCheckmarkCircleOutline } from "react-icons/io";
import { IoIosCheckmarkCircle } from "react-icons/io";
import { IoIosCloseCircle } from "react-icons/io";
import { IoIosCloseCircleOutline } from "react-icons/io";

interface ApplicationProps {
  username: string;
  imgUrl: string;
}

const Application = ({ username, imgUrl }: ApplicationProps) => {
  return (
    <EachApplication>
      <img src={imgUrl} />
      <div className="nameWrapper">{username}</div>
      <div className="buttonWrapper">
        <CheckIcon>
          <IoIosCheckmarkCircle className="inLineIcon icon" />
          <IoIosCheckmarkCircleOutline className="outLineIcon icon" />
        </CheckIcon>
        <XIcon>
          <IoIosCloseCircle className="inLineIcon icon" />
          <IoIosCloseCircleOutline className="outLineIcon icon" />
        </XIcon>
      </div>
    </EachApplication>
  );
};

const EachApplication = styled.div`
  width: 370px;
  height: 71px;
  color: var(--beige-00);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 16px 0 16px;
  border-radius: var(--radius-30);
  border: 1px solid var(--beige-00);
  margin-top: 16px;
  > img {
    border-radius: var(--radius-30);
    width: 30px;
    height: 30px;
  }
  > .nameWrapper {
    font-family: "mainB";
    font-size: 18px;
    width: 230px;
  }

  .icon {
    width: 25px;
    height: 25px;
    margin-right: 8px;
  }
`;

const CheckIcon = styled.div`
  > .inLineIcon {
    display: none;
  }
  :hover {
    .inLineIcon {
      margin-bottom: 1px;
      display: block;
    }
    .outLineIcon {
      display: none;
    }
  }
`;
const XIcon = styled.div`
  > .inLineIcon {
    display: none;
  }
  :hover {
    .inLineIcon {
      display: block;
    }
    .outLineIcon {
      display: none;
    }
  }
`;

export default Application;
