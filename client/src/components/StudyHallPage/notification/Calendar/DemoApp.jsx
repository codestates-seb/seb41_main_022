import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import styled from "styled-components";

import "./main.css";
import DetailModal from "./DetailModal";
import TodoListData from "../../../../util/dummyDataTodoList";

const DemoApp = () => {
  const [showAddModal, setShowAddModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [event, setEvent] = useState({});
  const [data, setData] = useState();

  useEffect(() => {
    let obj = TodoListData.data;
    setData(obj);
    console.log("data", data);
  }, []);

  const DetailToggle = () => {
    setShowDetailModal(!showDetailModal);
    console.log("event", event);
  };
  const handleEventClick = (arg) => {
    setEvent(arg.event);
    DetailToggle();
    console.log(
      "Clicked event",
      arg.event.title,
      arg.event.startStr,
      arg.event.endStr,
      arg.event.allDay
    );
  };
  const addEvent = (e) => {
    const calendarApi = e.view.calendar;
    calendarApi.addEvent({
      title: "event 3",
      date: e.dateStr,
      allDay: false,
      start: e.startStr,
      end: "2023-01-05",
      display: "list-item",
    });
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

export default DemoApp;
