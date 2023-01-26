import styled from "styled-components";
import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

const RecruitmentListSkeleton = () => {
  return (
    <RecruitmentWrapper>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
      <div className="wrapperSkeleton">
        <Skeleton className="titleSkeleton" />
        <Skeleton className="weekSkeleton" />
      </div>
    </RecruitmentWrapper>
  );
};

const RecruitmentWrapper = styled.section`
  display: flex;
  flex-wrap: wrap;
  height: auto;
  .wrapperSkeleton {
    width: 321px;
    height: 246px;
    border-radius: var(--radius-10);
    margin: 16px 20px 16px 20px;
    background-color: var(--gray-10);
  }
  .titleSkeleton {
    margin: 45px 43px 0 43px;
    width: 180px;
    height: 38px;
    border-radius: var(--radius-10);
  }
  .weekSkeleton {
    margin: 95px 43px 0 43px;
    width: 200px;
    height: 22px;
    border-radius: var(--radius-30);
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
