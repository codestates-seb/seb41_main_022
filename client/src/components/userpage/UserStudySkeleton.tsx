import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

import "./userSkeletonStyle.css";

const UserStudySkeleton = () => {
  return (
    <div className="studyCardWrapper">
      <div>
        <Skeleton className="studyCard" />
      </div>
      <div>
        <Skeleton className="studyCard" />
      </div>
    </div>
  );
};

export default UserStudySkeleton;
