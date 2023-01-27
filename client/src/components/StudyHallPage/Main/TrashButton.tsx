import styled from "styled-components";
import { FiTrash2 } from "react-icons/fi";

interface RedButtonProps {
  handleClick: () => void;
}

const TrashButton = ({ handleClick }: RedButtonProps) => {
  return <FiTrash2 onClick={() => handleClick()} />;
};
export default TrashButton;
