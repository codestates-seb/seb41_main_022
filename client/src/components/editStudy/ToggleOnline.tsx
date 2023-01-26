import styled from "styled-components";
import { useEffect, useState } from "react";

const ToggleOnline = ({ onChange, initValue }: any) => {
  const [isTrue, setIsTrue] = useState(false);
  useEffect(() => {
    onChange(isTrue);
  }, [isTrue]);
  useEffect(() => {
    setIsTrue(initValue);
  }, []);
  return (
    <div className="toggleBox">
      {isTrue ? "Online" : ""}
      {!isTrue ? "Offline" : ""}
      <ToggleWrapper onClick={() => setIsTrue(!isTrue)}>
        <ToggleBorder className={`background${isTrue}`}>
          <div className={`${isTrue}`}></div>
        </ToggleBorder>
      </ToggleWrapper>
    </div>
  );
};
export default ToggleOnline;
const ToggleWrapper = styled.main`
  .backgroundfalse {
    background-color: var(--mopo-00);
  }
  :hover {
    cursor: pointer;
  }
`;

const ToggleBorder = styled.div`
  margin-top: 5px;
  width: 40px;
  height: 20px;
  border-radius: var(--radius-30);
  background-color: var(--green-00);
  padding: 2px;
  .true {
    left: 20px;
  }
  > div {
    position: relative;
    width: 20px;
    height: 20px;
    background-color: var(--beige-00);
    border-radius: var(--radius-30);
  }
`;
