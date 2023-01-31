import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

import "../userSkeletonStyle.css";

const UserStudySkeleton = () => {
  return (
    <div className="mainDiv2">
      <Skeleton className="userIconSkeleton" />
      <div className="rightDiv">
        <Skeleton className="userNameSkeleton" />
        <Skeleton className="userNameSkeleton" />
      </div>
    </div>
  );
};

export default UserStudySkeleton;
