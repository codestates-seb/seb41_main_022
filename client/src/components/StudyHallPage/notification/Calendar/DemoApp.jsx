import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import styled from "styled-components";

import "./main.css";
import Modal from "./Modal";
import TodoListData from "../../../../util/dummyDataTodoList";

const DemoApp = () => {
  const [show, setShow] = useState(false);
  const [event, setEvent] = useState({});
  const [data, setData] = useState();

  useEffect(() => {
    let obj = TodoListData.data;
    setData(obj);
  }, []);

  const toggle = () => {
    setShow(!show);
    console.log("event", event);
  };
  const handleEventClick = (arg) => {
    setEvent(arg.event);
    toggle();
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
  const getEvents = () => {
    setData(TodoListData.data);
    return data;
  };
  // const state = {
  //   weekendsVisible: true,
  //   currentEvents: [],
  // };
  // // 데이터 가져오기
  // const componentDidMount = () => {
  //   this.getEvents();
  // };
  //
  // const getEvents = async () => {
  //   const events = await this._axiosEvents();
  //   this.setState({
  //     events,
  //   });
  // };
  //
  // const axiosEvents = () => {
  //   return axios.get("http://localhost:3001/data").then((res) => res.data);
  // };
  // const handleWeekendsToggle = () => {
  //   this.setState({
  //     weekendsVisible: !this.state.weekendsVisible,
  //   });
  // };
  //
  // const handleDateSelect = (selectInfo) => {
  //   let title = prompt("Please enter a new title for your event");
  //   let calendarApi = selectInfo.view.calendar;
  //   calendarApi.unselect(); // clear date selection
  //   if (title) {
  //     axios
  //       .post("http://localhost:3001/data", {
  //         id: uuidv4(),
  //         title,
  //         start: selectInfo.startStr,
  //       })
  //       .then((res) => console.log("post : ", res))
  //       .then(() => {
  //         window.location.reload();
  //       });
  //   }
  // };
  //
  // const handleEventClick = (clickInfo) => {
  //   if (
  //     window.confirm(
  //       `Are you sure you want to delete the event '${clickInfo.event.title}'`
  //     )
  //   ) {
  //     axios
  //       .delete("http://localhost:3001/data/" + clickInfo.event.id)
  //       .then((res) => console.log("delete : ", res))
  //       .then(() => {
  //         window.location.reload();
  //       });
  //   }
  // };
  //
  // const handleEvents = (events) => {
  //   this.setState({
  //     currentEvents: events,
  //   });
  // };
  //
  // function renderEventContent(eventInfo) {
  //   return (
  //     <>
  //       <i>{eventInfo.event.title}</i>
  //     </>
  //   );
  // }
  return (
    <>
      <div className="demo-app">
        <div className="demo-app-main">
          <Modal show={show} event={event} />
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
            // weekends={this.state.weekendsVisible}
            // //initialEvents={INITIAL_EVENTS} // alternatively, use the `events` setting to fetch from a feed
            // select={this.handleDateSelect}
            // eventContent={renderEventContent} // custom render function
            // eventClick={this.handleEventClick}
            // eventsSet={this.handleEvents} // called after events are initialized/added/changed/removed
            // events={this.state.events} //데이터 화면에 보이기
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
