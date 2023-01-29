import React, { useRef } from "react";
// import ScrollingComponent from './ScrollingComponent';

const ButtonComponent: React.FC = () => {
  const myRef = useRef<HTMLDivElement>(null);

  const scrollToRef = (ref: React.MutableRefObject<HTMLDivElement>) => {
    if (ref.current) {
      window.scrollTo(0, ref.current.offsetTop);
    }
  };

  return (
    <div>
      {/* <button onClick={() => scrollToRef(myRef)}> Scroll Down </button>
      <ScrollingComponent scrollRef={myRef} /> */}
    </div>
  );
};

export default ButtonComponent;
