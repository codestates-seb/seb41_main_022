import React, { useEffect, useState } from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

import "./main.css";
import DetailModal from "./DetailModal";
import AddModal from "./AddModal";
import axios from "axios";
import { useParams } from "react-router-dom";
import EditModal from "./EditModal";
import StudyHallNotificationSidebar from "../StudyHallNotificationSidebar";

const URL = process.env.REACT_APP_API_URL;

const CalendarApp = () => {
  const [showAddModal, setShowAddModal] = useState(false);
  const [showDetailModal, setShowDetailModal] = useState(false);
  const [showEditModal, setShowEditModal] = useState(false);
  const [event, setEvent] = useState({});
  const [data, setData] = useState();
  const [editData, setEditData] = useState("0");
  const { studyId } = useParams();
  const fetch = (url: string | undefined) => {
    return axios.get(url + "/calendar?studyId=" + studyId);
  };
  useEffect(() => {
    setTimeout(
      () =>
        fetch(URL).then((res) => {
          setData(res.data.data);
        }),
      100
    );
  }, [showAddModal, showDetailModal, showEditModal]);
  const handleEventClick = (arg: any) => {
    setEvent(arg.event);
    setShowDetailModal(true);
    setShowAddModal(false);
  };
  const addEvent = (e?: any) => {
    setEvent(e);
    setShowAddModal(true);
    setShowDetailModal(false);
    setShowEditModal(false);
  };
  return (
    <>
      <div className="demo-app">
        <StudyHallNotificationSidebar data={data} />
        <DetailModal
          showDetailModal={showDetailModal}
          setShowDetailModal={setShowDetailModal}
          setShowEditModal={setShowEditModal}
          setEditData={setEditData}
          event={event}
          data={data}
        />
        <AddModal
          showAddModal={showAddModal}
          setShowAddModal={setShowAddModal}
          event={event}
        />
        <EditModal
          showEditModal={showEditModal}
          setShowEditModal={setShowEditModal}
          editData={editData}
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
