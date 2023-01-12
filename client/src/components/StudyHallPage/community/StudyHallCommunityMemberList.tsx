import styled from "styled-components";

import { MemberData } from "../../../util/dummyData";
import StudyHallCommunityMember from "./StudyHallCommunityMember";

const StudyHallCommunityMemberList = () => {
  return (
    <MemberListWrapper>
      <div className="member">MEMBERS</div>
      {MemberData.data.map((el, idx) => (
        <StudyHallCommunityMember
          key={idx}
          username={el.username}
          imgUrl={el.imgUrl}
          role={el.role}
        />
      ))}
    </MemberListWrapper>
  );
};

const MemberListWrapper = styled.div`
  font-family: "mainEB";
  position: absolute;
  margin-left: 474px;
  margin-top: 60px;

  display: flex;
  flex-direction: column;
  align-items: start;

  > .member {
    font-size: 16px;
    color: var(--green);
  }
`;

export default StudyHallCommunityMemberList;
