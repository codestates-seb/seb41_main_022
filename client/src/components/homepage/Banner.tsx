import styled from "styled-components";

import StudyTree from "../.././assets/StudyTree.svg";

const Banner = () => {
  return (
    <BannerWrapper>
      <img src={StudyTree} />
    </BannerWrapper>
  );
};

const BannerWrapper = styled.div`
  width: 1000;
`;
export default Banner;
