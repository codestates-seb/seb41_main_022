import styled from "styled-components";

interface ToggleProps {
  isToggleClicked: boolean;
  handleToggleClick: () => void;
}

const Toggle = ({ isToggleClicked, handleToggleClick }: ToggleProps) => {
  return (
    <ToggleWrapper>
      <ToggleBorder className={`background${isToggleClicked}`}>
        <div
          onClick={() => handleToggleClick()}
          className={`${isToggleClicked}`}
        ></div>
      </ToggleBorder>
    </ToggleWrapper>
  );
};
export default Toggle;

const ToggleWrapper = styled.main`
  .backgroundfalse {
    background-color: var(--mopo-00);
  }
`;

const ToggleBorder = styled.div`
  margin-top: 5px;
  width: 40px;
  height: 20px;
  background-color: var(--green-00);
  border-radius: var(--radius-30);
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
