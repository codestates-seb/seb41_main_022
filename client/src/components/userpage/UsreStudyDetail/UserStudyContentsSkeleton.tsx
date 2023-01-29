import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

import "../userSkeletonStyle.css";

const UserStudyContentsSkeleton = () => {
  return (
    <div className="studyContentsWrapper">
      <Skeleton className="studyContentsH2" />
      <div className="studyContentsFlexCenter">
        <Skeleton className="studyContentsH2" />
      </div>
    </div>
  );
};

export default UserStudyContentsSkeleton;
