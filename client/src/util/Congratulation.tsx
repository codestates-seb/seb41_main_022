import CongratulationImgSrc from "../assets/congrtulate.svg";
import styled from "styled-components";
import JSConfetti from "js-confetti";
const jsConfetti = new JSConfetti();

const Congratulation = () => {
  jsConfetti.addConfetti({
    emojis: ["🎄", "🌱", "🌲", "🌳", "🍎", "🍊", "🍐"],
    emojiSize: 60,
    confettiNumber: 100,
  });

  return (
    <Div className="con02_event">
      <Imgs className="chatbox" src={CongratulationImgSrc} />
    </Div>
  );
};
const Div = styled.div`
  background-color: #d7d9db47;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  text-align: center;
  position: relative;
  cursor: pointer;
  transition: all 15s;

  .chatbox {
    animation: motion 3s;
    transform-origin: 80% 100%;
  }

  @keyframes motion {
    0% {
      opacity: 0;
      transform: translate3d(0, -100%, 0);
    }
    to {
      opacity: 1;
      transform: translateZ(0);
    }
  }
`;
const Imgs = styled.img`
  width: 700px;
`;
export default Congratulation;
