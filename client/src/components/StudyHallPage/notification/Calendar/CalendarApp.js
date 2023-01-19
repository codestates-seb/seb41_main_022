import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import styled from "styled-components";

import "./main.css";
import DetailModal from "./DetailModal";
import TodoListData from "../../../../util/dummyDataTodoList";
import AddModal from "./AddModal";
import axios, { AxiosResponse } from "axios";
import { useParams } from "react-router-dom";

const URL = "http://ec2-13-209-56-72.ap-northeast-2.compute.amazonaws.com:8080";

const CalendarApp = () => {
  const [showAddModal, setShowAddModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [event, setEvent] = useState({});
  const [data, setData] = useState();
  const { studyId } = useParams();
  const fetch = (url) => {
    return axios.get(url + "/calendar?studyId=" + studyId);
  };
  useEffect(() => {
    fetch(URL).then((res) => {
      console.log(res.data.data);
      setData(res.data.data);
    });
  }, [showAddModal]);
  const handleEventClick = (arg) => {
    setEvent(arg.event);
    setShowDetailModal(true);
  };
  const addEvent = (e) => {
    setEvent(e);
    setShowAddModal(true);
  };
  return (
    <>
      <div className="demo-app">
        <DetailModal
          showDetailModal={showDetailModal}
          setShowDetailModal={setShowDetailModal}
          event={event}
          data={data}
        />
        <AddModal
          showAddModal={showAddModal}
          setShowAddModal={setShowAddModal}
          event={event}
        />
        <div className="demo-app-main">
          <FullCalendar
            plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
            headerToolbar={{
              start: "",
              center: "prev title next",
              end: "today",
            }}
            titleFormat={{ year: "numeric", month: `short` }} //Jan 2023
            buttonIcons={{ prev: "chevron-left", next: "chevron-right" }}
            initialView="dayGridMonth"
            editable={true}
            selectable={true}
            selectMirror={true}
            dayMaxEvents={true}
            dateClick={addEvent}
            eventClick={handleEventClick}
            events={data}
          />
        </div>
      </div>
    </>
  );
};

export default CalendarApp;
