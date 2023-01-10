import styled from "styled-components";

const Toggle = () => {
  return (
    <ToggleBorder>
      <div></div>
    </ToggleBorder>
  );
};
export default Toggle;

const ToggleBorder = styled.main`
  margin-top: 5px;
  width: 40px;
  height: 20px;
  background-color: var(--green-00);
  border-radius: var(--radius-30);
  padding: 2px;
  :active {
    background-color: var(--mopo-00);
    > div {
      left: 20px;
    }
  }
  > div {
    position: relative;
    width: 20px;
    height: 20px;
    background-color: var(--beige-00);
    border-radius: var(--radius-30);
  }
`;
