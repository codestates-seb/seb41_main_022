import "./global.css";
import "./reset.css";
import styled from "styled-components";
function App() {
  const H2 = styled.h2`
    color: var(--mopo-10);
  `;
  return (
    <div>
      <h2>DITTO</h2>
      <p>뉴진스 사랑해요</p>
      <p>여로분 사랑해요</p>
    </div>
  );
}

export default App;
