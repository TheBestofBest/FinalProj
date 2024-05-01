"use client";
import "@fullcalendar/common/main.css";
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import "@/css/calendar.css";
import { useEffect, useRef, useState } from "react";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";

const SchedulePage = () => {
  const [calendarData, setCalenderData] = useState({});

  const getSchedules = async (relationName: string, relationId: number) => {
    await api.get(`/api/v1/schedules/getSchedules/dept/1`).then((res) => {
      console.log(res.data.data.scheduleDTOs);
      setCalenderData(res.data.data.scheduleDTOs);
    });
  };

  useEffect(() => {
    getSchedules("dept", 1);
  }, []);

  // function renderEventContent(eventInfo) {
  //   return (
  //     <div>
  //       <div className="fc-event-title">{eventInfo.event.title}</div>
  //       <div className="fc-event-description text-ellipsis">
  //         {eventInfo.event.extendedProps.description}
  //       </div>
  //     </div>
  //   );
  // }

  return (
    <DefaultLayout>
      <div className="bg-white p-3 shadow-3">
        <FullCalendar
          initialView="dayGridMonth"
          plugins={[dayGridPlugin, interactionPlugin]}
          editable={true} // 드래그 앤 드롭 기능 활성화
          selectable
          events={[
            {
              title: "Event 1",
              date: "2024-05-01",
              extendedProps: {
                description: "Additional content goes here", // 추가 내용을 지정합니다.
              },
            },
            { title: "Event 2", date: "2024-05-02" },
            { title: "Event 3", date: "2024-05-03" },
          ]}
          height="700px"
          // eventContent={renderEventContent}
        />
      </div>
    </DefaultLayout>
  );
};

export default SchedulePage;
