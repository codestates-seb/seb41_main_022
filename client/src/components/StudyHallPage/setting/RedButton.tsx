import styled from "styled-components";

interface RedButtonProps {
  text: string;
  handleClick: () => void;
}

const RedButton = ({ text, handleClick }: RedButtonProps) => {
  return <Button onClick={() => handleClick()}>{text}</Button>;
};

const Button = styled.button`
  width: 240px;
  padding: 3px;
  background-color: var(--red-00);
  color: var(--beige-00);
  border-radius: var(--radius-30);
  font-family: "mainL", Arial;
  font-size: 22px;
  border: none;
  :hover {
    background-color: var(--red-10);
    cursor: pointer;
  }
`;
export default RedButton;
