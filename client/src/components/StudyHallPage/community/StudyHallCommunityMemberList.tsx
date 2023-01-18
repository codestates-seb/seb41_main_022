import styled from "styled-components";
import { useParams } from "react-router-dom";
import axios from "axios";

import { MemberData } from "../../../util/dummyData";
import StudyHallCommunityMember from "./StudyHallCommunityMember";
import { useEffect, useState } from "react";

interface MemberInfo {
  username: string;
  imgUrl: string;
  role: string;
}

const StudyHallCommunityMemberList = () => {
  const { studyId } = useParams();
  const [memberInfo, setMemberInfo] = useState<MemberInfo[]>();

  useEffect(() => {
    axios
      .get(
        `http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080/user/study?studyId=${studyId}`
      )
      .then((res) => setMemberInfo(res.data.data));
  }, []);
  return (
    <MemberListWrapper>
      <div className="member">MEMBERS</div>
      {memberInfo &&
        memberInfo.map((el, idx) => (
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
