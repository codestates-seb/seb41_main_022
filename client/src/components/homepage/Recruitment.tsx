import styled from "styled-components";
import WeekBar from "../WeekBar";

const Recruitment = () => {
  return (
    <RecruitmentBackground>
      <div className="recruitmentBody">
        <div className="border">
          <BodyWrapper>
            <div className="body-myStudy">My Study</div>
            <div className="body-content--wrapper">
              <div className="body-content">
                저희는 디코로 만나 각자 모여서 공부 하는 스터디 입니다 INFP
                환영해요
              </div>
            </div>
            <WeekBar />
            <div className="body-online">
              <span className="online">Online </span>
              <span className="online offline">&nbsp;/&nbsp;</span>
              <span className="offline"> Offline</span>
            </div>
          </BodyWrapper>
        </div>
      </div>
    </RecruitmentBackground>
  );
};

const RecruitmentBackground = styled.div`
  width: 321px;
  height: 246px;
  border-radius: var(--radius-10);
  background-color: #ffffff;

  > .recruitmentBody {
    border-radius: var(--radius-10);
    width: 321px;
    height: 246px;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    :hover {
      transition-duration: 0.5s;
      background-color: rgba(0, 0, 0, 0.75);
      .body-content {
        display: block;
      }
    }

    > .border {
      width: 311px;
      height: 236px;
      border-radius: var(--radius-10);
      border: 2px solid var(--green);
    }
  }
`;
// 콘텐츠들 감싸서 정렬
const BodyWrapper = styled.div`
  margin: 0 43px 0 43px;
  padding: 45px 0 0 0;
  // 마이스터디
  > .body-myStudy {
    width: 227px;
    color: #ffffff;
    font-weight: 800;
    font-size: 32px;
    margin-bottom: 22px;
  }
  // 한줄소개
  > .body-content--wrapper {
    width: 227px;
    height: 51px;
    margin-bottom: 25px;
    display: flex;
    align-items: center;
    > .body-content {
      color: #ffffff;
      font-size: 14px;
      line-height: auto;
      display: none;
    }
  }

  // 온오프라인
  > .body-online {
    font-size: 10px;
    font-weight: bold;
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;

    > .online {
      color: #7cc9a7;
    }
  }
`;
export default Recruitment;
