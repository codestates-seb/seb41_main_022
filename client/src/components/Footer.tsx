import styled from "styled-components";

const Footer = () => {
  return (
    <FooterWrapper>
      <ContentWrapper>
        <ul className="textWrapper team">
          Team
          <li className="top">
            <a href="https://github.com/codestates-seb/seb41_main_022">
              율곡22|55눈룡
            </a>
          </li>
        </ul>
        <ul className="textWrapper member">
          Member
          <li className="top">
            <a href="https://github.com/kimhw7">김현우</a>
          </li>
          <li>
            <a href="https://github.com/vinyangda">양다빈</a>
          </li>
          <li>
            <a href="https://github.com/leewang31">기완곤듀</a>
          </li>
          <li>
            <a href="https://github.com/kung036">신우경</a>
          </li>
          <li>
            <a href="https://github.com/lisia004">김철현</a>
          </li>
          <li>
            <a href="https://github.com/jackcmh1">최민혁</a>
          </li>
        </ul>
        <ul className="textWrapper rightMargin">
          Role
          <li className="top">최고수령</li>
          <li>에러 아티스트</li>
          <li>노비</li>
          <li>🐞풀스텍🐞</li>
          <li>백엔드 ENFP</li>
          <li>측두엽 활성 문제 No.2</li>
        </ul>
      </ContentWrapper>
    </FooterWrapper>
  );
};

const FooterWrapper = styled.footer`
  background-color: var(--green);
  height: 320px;
  padding: 50px 0;
  display: flex;
  justify-content: center;
`;

const ContentWrapper = styled.div`
  height: 270px;
  border-top: 1px solid var(--beige-00);
  display: flex;
  justify-content: end;
  padding-top: 40px;
  color: var(--gray-10);
  //모바일
  @media screen and (max-width: 768px) {
    width: 321px;
  }
  //태블릿
  @media screen and (min-width: 768px) and (max-width: 1200px) {
    width: 750px;
  }
  // 웹
  @media screen and (min-width: 1200px) {
    width: 1100px;
  }

  > ul {
    margin: 0 16px 0 16px;
    font-family: "mainEB";
    > li {
      font-size: 12px;
      font-family: "mainL";
      margin-top: 8px;
      > a {
        color: var(--gray-10);
        :hover {
          color: var(--gray-20);
        }
      }
    }
    > .top {
      margin-top: 16px;
    }
  }
  > .member {
    display: flex;
    flex-direction: column;
    align-items: flex-end;
  }
  > .team {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  > .rightMargin {
    display: flex;
    flex-direction: column;
    @media screen and (max-width: 768px) {
      margin-right: 0;
    }
  }
`;
export default Footer;
