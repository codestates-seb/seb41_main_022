import styled from "styled-components";
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import React, { useRef, useEffect } from "react";

import first from "../.././assets/first.svg";
import second from "../.././assets/second.svg";
import third from "../.././assets/third.svg";

const Banner = () => {
  const settings = {
    dots: true,
    infinite: true,
    speed: 1000,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: false,
    autoplaySpeed: 10000,
    pauseOnHover: true,
    arrows: false,
  };
  return (
    <SlideWrapper>
      <Slider {...settings}>
        <BannerWrapper className="bannerWrapper">
          <div className="title">
            Stu<span>d</span>y Tree
          </div>
          <div className="subtitle">
            너와 나의 &nbsp; <span>스터디</span>
          </div>
          <img src={first} />
        </BannerWrapper>
        <BannerWrapper className="bannerWrapper">
          <div className="moveCreate">
            <span>스터디</span> 생성하기
          </div>
          <div className="second-title">
            <div className="s-first">나와 같은</div>
            <div className="s-second">
              <span>사람을</span> 모으고 싶다면?
            </div>
          </div>
          <img src={second} />
        </BannerWrapper>
        <BannerWrapper className="bannerWrapper">
          <div className="third-title">
            <div className="t-first">
              나와 <span>같은</span>
            </div>
            <div className="t-second">
              사람이 있을까 <span>?</span>
            </div>
            <div className="moveStudies">
              <span>스터디</span> 둘러보기
            </div>
            <img src={third} />
          </div>
        </BannerWrapper>
      </Slider>
    </SlideWrapper>
  );
};
export default Banner;
const SlideWrapper = styled.main`
  width: 100vw;
  height: 100%;
  .bannerWrapper {
    display: flex;
    justify-content: center;
  }
`;
const BannerWrapper = styled.div`
  * {
    font-family: "MainM";
  }
  width: 100vw;
  height: 100vh;
  position: relative;
  justify-content: center;
  img {
    width: 100vw;
  }
  > .title {
    display: flex;
    font-family: "logo";
    color: var(--logo);
    font-size: 1125%;
    letter-spacing: -3px;
    position: absolute;
    width: 100%;
    margin-top: 230px;
    text-align: center;
    justify-content: center;
    cursor: pointer;
    > span {
      color: var(--red-00);
      font-family: "logo";
    }
  }

  > .moveCreate {
    font-size: 25px;
    position: absolute;
    display: flex;
    justify-content: center;
    margin-top: 400px;
    margin-left: 150px;
    color: var(--logo);
    > span {
      color: var(--red-00);
    }
  }

  > .second-title {
    display: flex;
    color: var(--logo);
    font-size: 100px;
    letter-spacing: -3px;
    position: absolute;
    width: 100%;
    margin-top: 400px;
    padding-right: 50px;
    text-align: right;
    justify-content: right;
    > .s-first {
      position: absolute;
    }
    > .s-second {
      display: flex;
      margin-top: 100px;
      cursor: pointer;
      > span {
        justify-content: flex;
        display: flex;
        color: var(--red-00);
      }
    }
  }
  > .third-title {
    display: flex;
    color: var(--logo);
    font-size: 100px;
    letter-spacing: -3px;
    position: absolute;
    width: 100%;
    margin: 50px, 100px, 0px, 50px;
    text-align: right;

    > .t-first {
      display: flex;
      color: var(--logo);
      font-size: 100px;
      letter-spacing: -3px;
      position: absolute;
      width: 100%;
      margin-top: 100px;
      padding-right: 50px;

      > span {
        justify-content: flex;
        display: flex;
        color: var(--red-00);
      }
    }
    > .moveStudies {
      font-size: 25px;
      position: absolute;
      display: flex;
      justify-content: center;
      margin-top: 450px;
      margin-left: 950px;
      color: var(--logo);
      > span {
        justify-content: flex;
        display: flex;
        color: var(--red-00);
      }
    }
    > .t-second {
      display: flex;
      color: var(--logo);
      font-size: 100px;
      letter-spacing: -3px;
      position: absolute;
      width: 100%;
      margin-top: 200px;
      margin-right: 50px;
      > span {
        justify-content: flex;
        display: flex;
        color: var(--red-00);
      }
    }
  }
  .subtitle {
    height: 100%;
    width: 100%;
    font-size: 20px;
    display: flex;
    position: absolute;
    background-color: none;
    width: 100%;
    text-align: center;
    justify-content: center;
    margin-top: 450px;
    color: var(--beige-10);

    > span {
      color: var(--red-00);
      margin: 0px, 10px, 0px, 10px;
    }
  }
`;
