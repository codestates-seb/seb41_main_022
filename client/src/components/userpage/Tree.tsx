import styled from "styled-components";

interface TreeProps {
  treeId: number;

  treeImage: string;
}

const Tree = ({
  treeId,

  treeImage,
}: TreeProps) => {
  return (
    <>
      <div>{treeImage}</div>
    </>
  );
};

export default Tree;
