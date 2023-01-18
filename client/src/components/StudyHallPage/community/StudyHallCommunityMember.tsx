import styled from "styled-components";
import { TbCrown } from "react-icons/tb";

interface MemberProps {
  username: string;
  imgUrl: string;
  role: string;
}

const StudyHallCommunityMember = ({ username, imgUrl, role }: MemberProps) => {
  return (
    <Notice>
      <div className="wrapper">
        <img src={imgUrl} />
        <div className="name">{username}</div>
        {/* 역할이 방장일때 왕관 표시 */}
        {role.includes("ADMIN") ? (
          <span>
            <TbCrown />
          </span>
        ) : (
          <span></span>
        )}
      </div>
    </Notice>
  );
};

const Notice = styled.div`
  margin-top: 5px;
  width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  .wrapper {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 4px;
    > img {
      width: 26px;
      height: 26px;
      border-radius: var(--radius-30);
    }
    > span {
      width: 26px;
      height: 26px;
      display: flex;
      align-items: center;
      > svg {
        width: 20px;
        height: 20px;
      }
    }
  }
  div {
    color: var(--beige-00);
    display: flex;
    align-items: center;
    padding: 3px;
  }
`;

export default StudyHallCommunityMember;
