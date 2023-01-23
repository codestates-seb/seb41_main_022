import styled from "styled-components";
import StudyHallNotificationSidebarList from "./StudyHallNotificationSidebarList";

const StudyHallNotificationSidebar = (data: any) => {
  return (
    <Margin25>
      <Notice>
        <div className="wrapper">
          <StudyHallNotificationSidebarList data={data} />
        </div>
      </Notice>
    </Margin25>
  );
};
export default StudyHallNotificationSidebar;
const Margin25 = styled.main`
  position: absolute;
  margin-left: -225px;
  margin-top: -54px;
  * {
    font-family: "mainB", Arial;
    font-size: 12px;
  }
`;
const Notice = styled.div`
  margin-top: 5px;
  width: 198px;
  background-color: var(--green);
  border-radius: var(--radius-30);
  padding: 1px;
  .wrapper {
    border: 1px solid var(--beige-00);
    border-radius: var(--radius-30);
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 2px;
  }
  div {
    color: var(--beige-00);
    display: flex;
    align-items: center;
    padding: 3px;
  }
`;
const TopNavHover = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: var(--green);
  border-radius: var(--radius-30);
  margin: 1px;
  width: 12px;
  height: 12px;
  color: var(--gray-00);
  :hover {
    background-color: var(--beige-00);
    color: var(--green);
  }
`;
