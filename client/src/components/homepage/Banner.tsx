import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import StudyTreeBackground from "../.././assets/StudyTreeBackground.svg";
const settings = {
  dots: true,
  infinite: true,
  speed: 1000,
  slidesToShow: 1,
  slidesToScroll: 1,
  autoplay: true,
  autoplaySpeed: 10000,
  pauseOnHover: true,
  arrows: false,
};
const Banner = () => {
  return (
    <SlideWrapper>
      <Slider {...settings}>
        <BannerWrapper className="bannerWrapper">
          <div className="title">
            Stu<span>d</span>y Tree
          </div>
          <img src={StudyTreeBackground} />
        </BannerWrapper>
        <BannerWrapper>
          <div className="title">
            Stu<span>d</span>y Tree
          </div>
          <img src={StudyTreeBackground} />
        </BannerWrapper>
        <BannerWrapper>
          <div className="title">
            Stu<span>d</span>y Tree
          </div>
          <img src={StudyTreeBackground} />
        </BannerWrapper>
      </Slider>
    </SlideWrapper>
  );
};
export default Banner;
const SlideWrapper = styled.main`
  width: 100%;
  .bannerWrapper {
    display: flex;
    justify-content: center;
  }
`;
const BannerWrapper = styled.div`
  width: 100%;
  position: relative;
  img {
    margin: 0 auto;
  }
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
