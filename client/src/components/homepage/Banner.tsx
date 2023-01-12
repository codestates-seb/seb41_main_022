import styled from "styled-components";

import StudyTree from "../.././assets/StudyTree.svg";
import StudyTreeBackground from "../.././assets/StudyTreeBackground.svg";
const Banner = () => {
  return (
    <BannerWrapper>
      <div className="title">
        Stu<span>d</span>y Tree
      </div>
      <img src={StudyTreeBackground} />
    </BannerWrapper>
  );
};

const BannerWrapper = styled.div`
  width: 1000;
  height: 702px;
  position: relative;
  > .title {
    display: flex;
    font-family: "logo";
    color: var(--logo);
    font-size: 180px;
    letter-spacing: -3px;
    position: absolute;
    width: 100%;
    margin-top: 270px;
    text-align: center;
    justify-content: center;
    cursor: pointer;
    > span {
      color: var(--red-00);
    }
  }
`;
export default Banner;
