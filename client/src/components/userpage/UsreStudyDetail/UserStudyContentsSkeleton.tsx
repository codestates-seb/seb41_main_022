import Skeleton from "react-loading-skeleton";
import "react-loading-skeleton/dist/skeleton.css";

import "../userSkeletonStyle.css";

const UserStudyContentsSkeleton = () => {
  return (
    <div className="studyContentsWrapper">
      <Skeleton className="studyContentsH2" />
      <div className="studyContentsFlexCenter">
        <Skeleton className="studyContentsTree" />
      </div>
      <div className="divide">
        <div>
          <Skeleton className="studyContentsH4" />
          <Skeleton className="studyContentsTextarea" />
        </div>
        <div>
          <Skeleton className="studyContentsP" />
          <Skeleton className="studyContentsWeek" />
          <Skeleton className="studyContentsP" />
          <Skeleton className="studyContentsNotice" />
          <Skeleton className="studyContentsP" />
          <Skeleton className="studyContentsMember" />
          <Skeleton className="studyContentsMember" />
        </div>
      </div>
    </div>
  );
};

export default UserStudyContentsSkeleton;
