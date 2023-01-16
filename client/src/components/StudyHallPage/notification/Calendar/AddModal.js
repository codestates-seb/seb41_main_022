import styled from "styled-components";
import { Controller, useForm } from "react-hook-form";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { useEffect, useState } from "react";
import DatePicker from "react-datepicker";

const AddModal = ({ showAddModal, setShowAddModal, event, setTodo }) => {
  const [startTime, setStartTime] = useState(new Date());
  const { register, handleSubmit, control } = useForm();

  return (
    showAddModal && (
      <ModalDiv>
        <ContentsDiv>
          <div className="flexDiv">
            <h2> DetailModal</h2>
            <AiOutlineCloseCircle onClick={() => setShowAddModal(false)} />
          </div>
          <div>
            <Form
              onSubmit={handleSubmit((data) => alert(JSON.stringify(data)))}
            >
              <div>
                날짜
                <br />
                {event.dateStr.slice(0, 19)}
              </div>
              <div className="time">
                시간
                <Controller
                  name="time"
                  control={control}
                  render={({ field: { onChange } }) => (
                    <DatePicker
                      selected={startTime}
                      onChange={(time) => {
                        setStartTime(time);
                      }}
                      showTimeSelect
                      showTimeSelectOnly
                      timeIntervals={15}
                      timeCaption="Time"
                      dateFormat="h:mm aa"
                    />
                  )}
                />
              </div>
              <div>
                일정
                <br />
                <input
                  type="text"
                  className="person"
                  id="title"
                  {...register("title")}
                  placeholder="what to do..."
                />
              </div>
              <RedButton type="submit" onClick={() => {}}>
                제출
              </RedButton>
            </Form>
          </div>
        </ContentsDiv>
      </ModalDiv>
    )
  );
};
export default AddModal;
const ModalDiv = styled.main`
  margin-left: 150px;
  margin-top: -50px;
  max-width: 300px;
  height: 250px;
  box-shadow: 0rem 1rem 2rem rgba(0, 0, 0, 0.5);
  filter: blur(0);
  opacity: 1;
  visibility: visible;
  z-index: 10001;
  position: fixed;
  border-radius: var(--radius-10);
  background-color: var(--green);
  padding: 2px;
  * {
    font-family: "mainB", Arial;
    color: var(--beige-00);
  }
`;
const ContentsDiv = styled.article`
  width: 256px;
  height: 206px;
  border: 2px solid var(--beige-00);
  padding: 20px;
  border-radius: var(--radius-10);
  .flexDiv {
    display: flex;
    justify-content: space-between;
    * {
      font-size: 24px;
    }
  }
`;
const Form = styled.form`
  margin: 10px;
  > div {
    margin: 10px;
    * {
      color: var(--green);
    }
  }
`;
const RedButton = styled.button`
  border-style: none;
  width: 100px;
  padding: 3px;
  background-color: var(--red-00);
  color: var(--beige-00);
  border-radius: var(--radius-30);
  font-family: "mainL", Arial;
  font-size: 18px;
  :hover {
    background-color: var(--red-10);
  }
`;
