"use client";
import "@fullcalendar/common/main.css";
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import "@/css/calendar.css";
import { useEffect, useRef, useState } from "react";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";

// 종료일을 하루 더하는 함수
const addOneDayToEnd = (endDateString) => {
  const endDate = new Date(endDateString); // 종료일을 생성합니다.
  endDate.setDate(endDate.getDate() + 1); // 종료일에 하루를 더합니다.
  return endDate.toISOString().split("T")[0]; // ISO 8601 형식으로 변환하여 반환합니다.
};

const SchedulePage = () => {
  const [schedules, setSchedules] = useState<any[]>([]);

  const getSchedules = async (relationName: string, relationId: number) => {
    await api
      .get(`/api/v1/schedules/getSchedules/${relationName}/${relationId}`)
      .then((res) => {
        console.log(res.data.data.scheduleDTOs);
        const scheduleData = res.data.data.scheduleDTOs.map((schedule) => ({
          title: schedule.name,
          start: schedule.startDate,
          end:
            schedule.endDate === schedule.startDate
              ? schedule.endDate
              : addOneDayToEnd(schedule.endDate),
          inclusive: true,
          category: schedule.relationName,
        }));
        setSchedules((schedules) => [...schedules, ...scheduleData]);
      });
  };

  useEffect(() => {
    // 개인
    // getSchedules("dept", 1);
    // 부서
    getSchedules("dept", 1);
    // 전체 일정
    getSchedules("all", 0);
  }, []);

  function renderEventContent(eventInfo) {
    var color = "bg-blue-700";
    console.log(eventInfo.event);
    return (
      <div className="flex">
        <div
          className={
            eventInfo.event.category === "dept"
              ? "me-1 h-auto w-1 bg-blue-700"
              : "me-1 h-auto w-1 bg-blue-700"
          }
        ></div>
        <div className="fc-event-title">{eventInfo.event.title}</div>
      </div>
    );
  }

  return (
    <DefaultLayout>
      <div className="bg-white p-3 shadow-3">
        <FullCalendar
          initialView="dayGridMonth"
          plugins={[dayGridPlugin, interactionPlugin]}
          // editable={true} // 드래그 앤 드롭 기능 활성화
          selectable={true}
          selectMirror={true}
          dayMaxEvents={true}
          weekends={true}
          events={schedules}
          height="700px"
          eventContent={renderEventContent}
          eventHint="123"
          views={{
            dayGridMonth: {
              eventLimit: 3, // 더 보기 링크를 표시할 이벤트 개수
            },
          }}
        />
      </div>
    </DefaultLayout>
  );
};

export default SchedulePage;
