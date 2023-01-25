import styled from "styled-components";

interface chatProps {
  username: string;
  content: string;
  dateTime: string;
  imgUrl: string;
}

const Chat = ({ username, content, dateTime, imgUrl }: chatProps) => {
  return (
    <Chatwrapper>
      <div className="time">{dateTime.replace("T", " / ")}</div>
      <img src={imgUrl} />
      <TextWrapper>
        <div className="name">{username}</div>
        <div className="content">
          {content.split("\n").map((el, idx) => (
            <span key={idx}>
              {el}
              <br />
            </span>
          ))}
        </div>
      </TextWrapper>
    </Chatwrapper>
  );
};

const Chatwrapper = styled.div`
  border: 1px solid var(--beige-00);
  border-radius: var(--radius-20);
  background-color: var(--green);
  max-width: 270px;
  display: flex;
  align-items: flex-start;
  font-size: 12px;
  color: var(--beige-00);
  padding: 8px;
  line-height: 16px;
  margin-top: 18px;
  .time {
    position: absolute;
    font-size: 8px;
    margin-top: -25px;
    margin-left: 10px;
    display: none;
  }
  :hover {
    .time {
      color: var(--gray-20);
      display: block;
    }
    .myChat {
      margin-top: 9px;
    }
  }
  .myChat {
    font-family: "mainL";
    margin-top: 0;
  }
  > img {
    border-radius: var(--radius-30);
    width: 20px;
    height: 20px;
    margin-right: 8px;
  }
`;

const TextWrapper = styled.div`
  .content {
    font-family: "mainL";
    white-space: pre;
  }
  display: flex;
  flex-direction: column;
  > .name {
    font-size: 10px;
  }
`;

export default Chat;
