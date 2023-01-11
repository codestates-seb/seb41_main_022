import styled from "styled-components";
import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";

class MyCalendar extends Component {
  render() {
    return (
      <div className="App">
        <FullCalendar
          defaultView="dayGridMonth"
          plugins={[dayGridPlugin]}
          events={[
            { title: "event 1", date: "2023-01-01" },
            { title: "event 2", date: "2023-01-02" },
          ]}
        />
      </div>
    );
  }
}
export default MyCalendar;
