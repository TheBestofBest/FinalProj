"use client";
import "@fullcalendar/common/main.css";
import FullCalendar from "@fullcalendar/react";
import interactionPlugin from "@fullcalendar/interaction";
import dayGridPlugin from "@fullcalendar/daygrid";
import "@/css/calendar.css";
import { useEffect, useRef, useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

// 종료일을 하루 더하는 함수
const addOneDayToEnd = (endDateString) => {
  const endDate = new Date(endDateString); // 종료일을 생성합니다.
  endDate.setDate(endDate.getDate() + 1); // 종료일에 하루를 더합니다.
  return endDate.toISOString().split("T")[0]; // ISO 8601 형식으로 변환하여 반환합니다.
};

const SchedulePage = () => {
  const [schedules, setSchedules] = useState<any[]>([]);
  const [todaySchedules, setTodaySchedules] = useState<any[]>([]);
  const [showModal, setShowModal] = useState(false);
  const [startDate, setStratDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const today = new Date();

  const handleCheckBox = () => {
    console.log("돌아요");
    const checkList = document.querySelectorAll(
      'input[name="category"]:checked',
    );
    setSchedules([]);
    checkList.forEach((checkbox) => {
      if (checkbox.id == "all") {
        getSchedules("all", 0);
      }
      if (checkbox.id == "dept") {
        getSchedules("dept", 1);
      }
      if (checkbox.id == "member") {
        getSchedules("member", 2);
      }
    });
  };

  const getSchedules = async (relationName: string, relationId: number) => {
    await api
      .get(`/api/v1/schedules/getSchedules/${relationName}/${relationId}`)
      .then((res) => {
        const scheduleData = res.data.data.scheduleDTOs.map((schedule) => ({
          title: schedule.name,
          start: schedule.startDate,
          end:
            schedule.endDate === schedule.startDate
              ? schedule.endDate
              : addOneDayToEnd(schedule.endDate),
          inclusive: true,
          category: schedule.relationName,
          originalEnd: schedule.endDate,
        }));
        setSchedules((schedules) => [...schedules, ...scheduleData]);
      });
  };

  // useEffect(() => {
  //   schedules.map((schedule) => {
  //     console.log(today);
  //     console.log(schedule.start);
  //     console.log(today <= schedule.start);
  //     if (today >= schedule.start && today <= schedule.originalEnd) {
  //       console.log(schedule.title);
  //       console.log(schedule.start);
  //     }
  //   });
  // }, [schedules]);

  useEffect(() => {
    // 회사 일정
    getSchedules("all", 0);
    // 부서
    getSchedules("dept", 1);
    // 개인
    getSchedules("member", 2);
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
        <div className="fc-event-title font-satoshi">
          {eventInfo.event.title}
        </div>
      </div>
    );
  }

  const handlerScheduleSend = () => {
    const sendCategory = document.querySelector("#scheduleCategory").value;
    const sendName = document.querySelector("#scheduleName").value;

    if (sendName == "") {
      alert("스케줄명을 입력해 주세요");
      return;
    }

    if (startDate > endDate) {
      alert("시작시간보다 종료시간이 늦을 수 없습니다.");
      return;
    }

    if (!confirm("스케줄을 등록하시겠습니까?")) {
      return;
    }

    const sendData = {
      relationName: sendCategory,
      relationId: 0,
      name: sendName,
      startDate:
        startDate.getFullYear() +
        "-" +
        (startDate.getMonth() + 1 <= 9
          ? "0" + (startDate.getMonth() + 1)
          : startDate.getMonth() + 1) +
        "-" +
        (startDate.getDate() <= 9
          ? "0" + startDate.getDate()
          : startDate.getDate()),
      endDate:
        endDate.getFullYear() +
        "-" +
        (endDate.getMonth() + 1 <= 9
          ? "0" + (endDate.getMonth() + 1)
          : endDate.getMonth() + 1) +
        "-" +
        (endDate.getDate() <= 9 ? "0" + endDate.getDate() : endDate.getDate()),
    };
    console.log(startDate.getMonth());
    console.log(endDate);

    console.log(JSON.stringify(sendData));
    api.post("api/v1/schedules", sendData).then((res) => {
      console.log(res);
      alert(res.data.msg);
      if (res.data.isSuccess) {
        console.log("돌아요2");
        handleCheckBox();
        setShowModal(false);
      }
    });
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Schedule" />
      <div className="flex gap-7">
        <div className="w-1/5 bg-white p-3 shadow-3">
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
          <div className="createArea mt-10">
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
          />
        </div>
      </div>
      {showModal ? (
        <>
          <div className="  fixed inset-0 z-50 flex items-center justify-center overflow-y-auto overflow-x-hidden outline-none focus:outline-none">
            <div className="relative mx-auto my-6 w-180">
              {/*content*/}
              <div className="relative flex w-full flex-col rounded-lg border-0 bg-white shadow-lg outline-none focus:outline-none">
                {/*header*/}
                <div className="border-blueGray-200 flex items-start justify-between rounded-t border-b border-solid p-5">
                  <h3 className="text-3xl font-semibold">스케줄 등록</h3>
                  <button
                    className="float-right ml-auto border-0 bg-transparent p-1 text-3xl font-semibold leading-none text-black opacity-5 outline-none focus:outline-none"
                    onClick={() => setShowModal(false)}
                  >
                    <span className="block h-6 w-6 bg-transparent text-2xl text-black opacity-5 outline-none focus:outline-none">
                      ×
                    </span>
                  </button>
                </div>
                {/*body*/}
                <div className="relative  p-6">
                  <div className="flex gap-3 ">
                    <div className="w-1/2">
                      <label
                        htmlFor="scheduleCategory"
                        className="block text-xl"
                      >
                        category
                      </label>
                      <div className="mt-1">
                        <select
                          id="scheduleCategory"
                          name="scheduleCategory"
                          className="text-gray-900 ring-gray-300 block w-full rounded-md border-0 py-2 shadow-sm ring-1 ring-inset focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                        >
                          <option value="all">회사</option>
                          <option value="dept">부서</option>
                          <option value="member">개인</option>
                        </select>
                      </div>
                    </div>
                    <div className="w-1/2">
                      <label htmlFor="name" className="block text-xl">
                        name
                      </label>
                      <div className="mt-1">
                        <div className="ring-gray-300 flex rounded-md shadow-sm ring-1 ring-inset focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                          <input
                            type="text"
                            name="scheduleName"
                            id="scheduleName"
                            className="text-gray-900 placeholder:text-gray-400 block flex-1 border-0 bg-transparent py-1.5 pl-1 focus:ring-0 sm:text-sm sm:leading-6"
                            placeholder="스케줄명을 입력해 주세요"
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="mt-5 flex gap-3 ">
                    <div className="w-1/2">
                      <div>시작일</div>
                      <DatePicker
                        className="mt-1 inline-block rounded-md border border-blue-300 px-19 py-1"
                        dateFormat={"yyyy년 MM월 dd일"}
                        selected={startDate}
                        onChange={(date) => setStratDate(date)}
                        closeOnScroll={true}
                      />
                    </div>
                    <div className="w-1/2">
                      <div>종료일</div>
                      <DatePicker
                        className="mt-1 inline-block rounded-md border border-blue-300 px-20 py-1"
                        dateFormat={"yyyy년 MM월 dd일"}
                        selected={endDate}
                        onChange={(date) => setEndDate(date)}
                        closeOnScroll={true}
                      />
                    </div>
                  </div>
                </div>
                {/*footer*/}
                <div className="border-blueGray-200 flex items-center justify-end rounded-b border-t border-solid p-3">
                  <button
                    className="text-red-500 background-transparent mb-1 mr-1 px-6 py-2 text-sm font-bold uppercase outline-none transition-all duration-150 ease-linear focus:outline-none"
                    type="button"
                    onClick={() => setShowModal(false)}
                  >
                    Close
                  </button>
                  <button
                    className="mb-1 mr-1 rounded bg-emerald-500 px-6 py-3 text-sm font-bold uppercase text-white shadow outline-none transition-all duration-150 ease-linear hover:shadow-lg focus:outline-none active:bg-emerald-600"
                    type="button"
                    onClick={handlerScheduleSend}
                  >
                    Save Changes
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div className="fixed inset-0 z-40 bg-black opacity-25"></div>
        </>
      ) : null}
    </DefaultLayout>
  );
};

export default SchedulePage;
