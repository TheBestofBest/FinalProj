"use client";
import "@fullcalendar/common/main.css";
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import "@/css/calendar.css";
import { useEffect, useRef, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { useQuery } from "@tanstack/react-query";
import { useRouter } from "next/navigation";
import SendModal from "./sendModal";
import DetailModal from "./detailModal";

// 종료일을 하루 더하는 함수
const addOneDayToEnd = (endDateString) => {
  const endDate = new Date(endDateString); // 종료일을 생성합니다.
  endDate.setDate(endDate.getDate() + 1); // 종료일에 하루를 더합니다.
  return endDate.toISOString().split("T")[0]; // ISO 8601 형식으로 변환하여 반환합니다.
};

const SchedulePage = () => {
  const router = useRouter();
  const [schedules, setSchedules] = useState<any[]>([]);
  const [todaySchedules, setTodaySchedules] = useState<any[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [detailModal, setDetailModal] = useState(false);
  const [detailData, setDetailData] = useState({});
  var today = new Date(
    new Date().getFullYear() +
      "-" +
      (new Date().getMonth() + 1) +
      "-" +
      new Date().getDate() +
      "-" +
      "09:00:00",
  );

  const { isLoading, data } = useQuery({
    queryKey: ["member"],
  });

  if (data == null) {
    router.push("/");
  }

  const handleCheckBox = () => {
    const checkList = document.querySelectorAll(
      'input[name="category"]:checked',
    );
    setSchedules([]);
    checkList.forEach((checkbox) => {
      if (checkbox.id == "all") {
        getSchedules("all", 0);
      }
      if (checkbox.id == "dept") {
        getSchedules("dept", data && data?.department.id);
      }
      if (checkbox.id == "member") {
        getSchedules("member", data && data?.id);
      }
    });
  };

  const getSchedules = async (relationName: string, relationId: number) => {
    await api
      .get(`/api/v1/schedules/getSchedules/${relationName}/${relationId}`)
      .then((res) => {
        const scheduleData = res.data.data.scheduleDTOs.map((schedule) => ({
          originalId: schedule.id,
          title: schedule.name,
          start: schedule.startDate,
          end:
            schedule.endDate === schedule.startDate
              ? schedule.endDate
              : addOneDayToEnd(schedule.endDate),
          inclusive: true,
          category: schedule.relationName,
          categoryId: schedule.relationId,
          originalEnd: schedule.endDate,
          originalStart: schedule.startDate,
        }));
        setSchedules((schedules) => [...schedules, ...scheduleData]);
      });
  };

  useEffect(() => {
    const todayData = schedules.filter((schedule) => {
      var start = new Date(schedule.originalStart);
      var end = new Date(schedule.originalEnd);

      if (start <= today && end >= today) {
        return schedule;
      }
    });
    setTodaySchedules(todayData);
  }, [schedules]);
  useEffect(() => {
    // 회사 일정
    getSchedules("all", 0);
    // 부서
    getSchedules("dept", data && data?.department.id);
    // 개인
    getSchedules("member", data && data?.id);
  }, []);

  function renderEventContent(eventInfo) {
    let color;
    switch (eventInfo.event.extendedProps.category) {
      case "all":
        color = "bg-red";
        break;
      case "dept":
        color = "bg-green-700";
        break;
      case "member":
        color = "bg-blue-700";
        break;
      default:
        color = "bg-gray-700";
        break;
    }
    return (
      <div className="flex">
        <div className={`${color} me-1 h-auto w-1`}></div>
        <div className="fc-event-title overflow-auto font-satoshi">
          {eventInfo.event.title}
        </div>
      </div>
    );
  }

  function renderTodayContent(today) {
    let color;
    switch (today.category) {
      case "all":
        color = "bg-red";
        break;
      case "dept":
        color = "bg-green-700";
        break;
      case "member":
        color = "bg-blue-700";
        break;
      default:
        color = "bg-gray-700";
        break;
    }
    return (
      <div
        onClick={() => handlerTodayEvent(today)}
        className="flex cursor-pointer bg-blue-50"
      >
        <div className={`${color} me-1 h-auto w-1`}></div>
        <div>
          <div className="fc-event-title">{today.title}</div>
          <div className="text-sm">
            {today.originalStart} ~ {today.originalEnd}
          </div>
        </div>
      </div>
    );
  }

  const handlerEvent = (eventInfo) => {
    const id = eventInfo.event.extendedProps.originalId;

    const detailData = schedules.find((schedule) => schedule.originalId === id);

    setDetailData(detailData);
    setDetailModal(true);
  };

  const handlerTodayEvent = (today) => {
    const id = today.originalId;

    const detailData = schedules.find((schedule) => schedule.originalId === id);

    setDetailData(detailData);
    setDetailModal(true);
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Schedule" />
      <div className="flex gap-7">
        <div className="flex w-1/5 flex-grow flex-col justify-between  bg-white p-3 shadow-3">
          <div className="selectArea">
            <div>선택</div>
            <div className="mt-3 flex justify-between">
              <div className="relative flex gap-x-1">
                <div className="flex h-6 items-center">
                  <input
                    id="all"
                    name="category"
                    type="checkbox"
                    className="border-gray-300 h-4 w-4 rounded accent-red"
                    defaultChecked
                    onClick={handleCheckBox}
                  />
                </div>
                <div className="text-sm leading-6">
                  <label htmlFor="all" className="text-gray-900 font-medium">
                    회사
                  </label>
                </div>
              </div>

              <div className="relative flex gap-x-1">
                <div className="flex h-6 items-center">
                  <input
                    id="dept"
                    name="category"
                    type="checkbox"
                    className="border-gray-300 h-4 w-4 rounded accent-green-700"
                    defaultChecked
                    onClick={handleCheckBox}
                  />
                </div>
                <div className="text-sm leading-6">
                  <label htmlFor="dept" className="text-gray-900 font-medium">
                    부서
                  </label>
                </div>
              </div>

              <div className="relative flex gap-x-1">
                <div className="flex h-6 items-center">
                  <input
                    id="member"
                    name="category"
                    type="checkbox"
                    className="border-gray-300 h-4 w-4 rounded accent-blue-700"
                    defaultChecked
                    onClick={handleCheckBox}
                  />
                </div>
                <div className="text-sm leading-6">
                  <label htmlFor="member" className="text-gray-900 font-medium">
                    개인
                  </label>
                </div>
              </div>
            </div>
          </div>
          <div className="todayArea  ">
            오늘 스케줄
            <div className="borde mt-3 h-125 flex-col space-y-2 overflow-auto">
              {todaySchedules.map((today) => renderTodayContent(today))}
            </div>
          </div>
          <div className="createArea">
            <div
              onClick={() => setShowModal(true)}
              className="block items-center justify-center rounded-full bg-slate-500 py-1 text-center font-medium text-white hover:cursor-pointer hover:bg-opacity-90"
            >
              등록
            </div>
          </div>
        </div>
        <div className="w-4/5 bg-white p-3 shadow-3">
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
            eventClick={handlerEvent}
          />
        </div>
      </div>
      {showModal ? (
        <SendModal
          setShowModal={setShowModal}
          handleCheckBox={handleCheckBox}
          data={data}
        />
      ) : null}
      {detailModal ? (
        <DetailModal
          setDetailModal={setDetailModal}
          handleCheckBox={handleCheckBox}
          detailData={detailData}
        />
      ) : null}
    </DefaultLayout>
  );
};

export default SchedulePage;
