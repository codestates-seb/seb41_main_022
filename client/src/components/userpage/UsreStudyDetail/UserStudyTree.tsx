import React, { useEffect, useState } from "react";
import styled from "styled-components";
import Slider from "react-slick";
interface treeType {
  createdAt: string;
  makeMonth: number;
  treeId: number;
  treeImage: string;
  treePoint: number;
}
interface treePropsType {
  treeData?: treeType[];
}
const UserStudyTree = ({ treeData }: treePropsType) => {
  const settings = {
    dots: true,
    speed: 1000,
    slidesToShow: 1,
    slidesToScroll: 1,
    arrows: false,
  };
  const renderMapTrees = (): JSX.Element[] => {
    if (treeData) {
      const mapTrees = treeData.map((el, idx) => {
        return (
          <TreeSlide>
            <div className="flexDiv">
              <Img src={el.treeImage} />
            </div>
            <div className="flexDiv">{el.makeMonth}월 나무</div>
          </TreeSlide>
        );
      });
      return mapTrees;
    } else {
      return [];
    }
  };
  return (
    <SlideWrapper>
      <Slider {...settings}>{renderMapTrees()}</Slider>
    </SlideWrapper>
  );
};

export default UserStudyTree;
const SlideWrapper = styled.main`
  width: 100%;
  margin-bottom: 20px;
  .bannerWrapper {
    display: flex;
    justify-content: center;
  }
`;
const TreeSlide = styled.div`
  .flexDiv {
    display: flex;
    justify-content: center;
  }
`;
const Img = styled.img`
  width: 160px;
`;
