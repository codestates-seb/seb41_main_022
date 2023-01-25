import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

import "./userSkeletonStyle.css";

const UserSkeleton = () => {
  return (
    <div className="mainDiv">
      <Skeleton className="userIconSkeleton" />
      <div className="rightDiv">
        <Skeleton className="userNameSkeleton" />
        <Skeleton className="userNameSkeleton" />
      </div>
    </div>
  );
};

export default UserSkeleton;
