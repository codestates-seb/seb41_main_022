import styled from "styled-components";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const RecruitmentListSkeleton = () => {
  return (
    <RecruitmentWrapper>
      <Skeleton className="recruitmentSkeleton" />
      <Skeleton className="recruitmentSkeleton" />
      <Skeleton className="recruitmentSkeleton" />
      <Skeleton className="recruitmentSkeleton" />
      <Skeleton className="recruitmentSkeleton" />
      <Skeleton className="recruitmentSkeleton" />
    </RecruitmentWrapper>
  );
};

const RecruitmentWrapper = styled.section`
  display: flex;
  flex-wrap: wrap;
  height: auto;
  .recruitmentSkeleton {
    width: 321px;
    height: 246px;
    border-radius: var(--radius-10);
    margin: 16px 20px 16px 20px;
  }

  //모바일
  @media screen and (max-width: 768px) {
    width: 321px;
    align-items: center;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1200px) {
    width: 750px;
  }
  // 웹
  @media screen and (min-width: 1200px) {
    width: 1100px;
  }
`;

export default RecruitmentListSkeleton;
