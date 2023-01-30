import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import React, { useRef, useEffect } from "react";

import SecondBannerPage from "./HomeBanner/SecondBannerPage";
import FirstBannerPage from "./HomeBanner/FirstBannerPage";
import ThirdBannerPage from "./HomeBanner/ThirdBannerPage";

interface ClickProps {
  scrollToCreate: () => void;
  scrollToStudies: () => void;
}

const Banner = ({ scrollToCreate, scrollToStudies }: ClickProps) => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 1000,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 10000,
    pauseOnHover: false,
    arrows: false,
  };

  return (
    <SlideWrapper>
      <Slider {...settings}>
        <BannerWrapper>
          <FirstBannerPage />
        </BannerWrapper>
        <BannerWrapper>
          <SecondBannerPage scrollToCreate={scrollToCreate} />
        </BannerWrapper>
        <BannerWrapper>
          <ThirdBannerPage scrollToStudies={scrollToStudies} />
        </BannerWrapper>
      </Slider>
    </SlideWrapper>
  );
};
export default Banner;
const SlideWrapper = styled.main`
  margin-top: 64px;
  width: 100vw;
  * {
    font-family: "BannerM";
  }
  .slick-dots {
    //margin-top: -30px;
    bottom: 20px;
  }
`;

const BannerWrapper = styled.div`
  width: 100vw;
  height: 95vh;
  position: relative;
`;
