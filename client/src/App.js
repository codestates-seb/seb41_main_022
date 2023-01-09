import "./global.css";
import "./reset.css";
import styled from "styled-components";
function App() {
  const H2 = styled.h2`
    color: var(--mopo-10);
  `;
  return (
    <div>
      <H2>DITTO</H2>
      <p>안녕하세용</p>
      <p>방가워용</p>
    </div>
  );
}

export default App;
