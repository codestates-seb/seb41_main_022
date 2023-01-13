import styles from "./modal.css";
import styled from "styled-components";

const AddModal = ({ show, event }) => {
  return show ? (
    <ModalDiv id="modal">
      <h2> {event.title}</h2>
      <div className={styles.content}>
        <ul>
          <li> {event.startStr} </li>
          <li> {event.endStr} </li>
          <li> {event.allDay.toString()} </li>
        </ul>
      </div>
    </ModalDiv>
  ) : null;
};

export default Modal;
const ModalDiv = styled.div`
  width: 460px;
  margin-left: 70px;
  background: white;
  border: 1px solid #ccc;
  box-shadow: -2rem 2rem 2rem rgba(0, 0, 0, 0.2);
  filter: blur(0);
  opacity: 1;
  visibility: visible;
  z-index: 10001;
  position: fixed;
`;
