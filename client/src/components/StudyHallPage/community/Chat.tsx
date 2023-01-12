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
      <img src={imgUrl} />
      <TextWrapper>
        <div className="name">{username}</div>
        <div className="content">{content}</div>
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
  margin-top: 8px;

  > img {
    border-radius: var(--radius-30);
    width: 20px;
    height: 20px;
    margin-right: 8px;
  }
`;

const TextWrapper = styled.div`
  * {
    font-family: "mainL";
  }
  display: flex;
  flex-direction: column;
  > .name {
    font-size: 10px;
  }
`;

export default Chat;
