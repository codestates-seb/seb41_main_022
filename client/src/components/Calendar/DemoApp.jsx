import React from "react";
import FullCalendar from "@fullcalendar/react";
import { CalendarApi, formatDate } from "@fullcalendar/core";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { INITIAL_EVENTS, createEventId } from "./event-utils";
import "./main.css";
import styled from "styled-components";
import axios from "axios";
export default class DemoApp extends React.Component {
  state = {
    weekendsVisible: true,
    currentEvents: [],
  };
  // 데이터 가져오기
  componentDidMount() {
    this._getEvents();
  }

  _getEvents = async () => {
    const events = await this._axiosEvents();
    this.setState({
      events,
    });
  };

  _axiosEvents = () => {
    return axios.get("http://localhost:3001/data").then((res) => res.data);
  };
  render() {
    let { events } = this.state;
    return (
      <Calendar className="demo-app">
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
            weekends={this.state.weekendsVisible}
            //initialEvents={INITIAL_EVENTS} // alternatively, use the `events` setting to fetch from a feed
            select={this.handleDateSelect}
            eventContent={renderEventContent} // custom render function
            eventClick={this.handleEventClick}
            eventsSet={this.handleEvents} // called after events are initialized/added/changed/removed
            events={this.state.events} //데이터 화면에 보이기
          />
        </div>
      </Calendar>
    );
  }
  handleWeekendsToggle = () => {
    this.setState({
      weekendsVisible: !this.state.weekendsVisible,
    });
  };

  handleDateSelect = (selectInfo) => {
    let title = prompt("Please enter a new title for your event");
    let calendarApi = selectInfo.view.calendar;
    calendarApi.unselect(); // clear date selection
    if (title) {
      calendarApi.addEvent({
        id: createEventId(),
        title,
        start: selectInfo.startStr,
        end: selectInfo.endStr,
        allDay: selectInfo.allDay,
      });
      axios
        .post("http://localhost:3001/data", {
          id: createEventId(),
          title,
          start: selectInfo.startStr,
        })
        .then((res) => console.log(res));
    }
  };

  handleEventClick = (clickInfo) => {
    if (
      alert(
        `Are you sure you want to delete the event '${clickInfo.event.title}'`
      )
    ) {
      clickInfo.event.remove();
    }
  };

  handleEvents = (events) => {
    this.setState({
      currentEvents: events,
    });
  };
}

function renderEventContent(eventInfo) {
  return (
    <>
      <b>{eventInfo.timeText}</b>
      <i>{eventInfo.event.title}</i>
    </>
  );
}

function renderSidebarEvent(event) {
  return (
    <li key={event.id}>
      <b>
        {formatDate(event.start, {
          year: "numeric",
          month: "short",
          day: "numeric",
        })}
      </b>
      <i>{event.title}</i>
    </li>
  );
}
const Calendar = styled.div`
  button {
    color: white;
  }
`;
