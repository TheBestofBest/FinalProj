"use client";

import api from "@/util/api";
import { useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";

const MainTableFour: React.FC = () => {
  const addOneDayToEnd = (endDateString) => {
    const endDate = new Date(endDateString); // 종료일을 생성합니다.
    endDate.setDate(endDate.getDate() + 1); // 종료일에 하루를 더합니다.
    return endDate.toISOString().split("T")[0]; // ISO 8601 형식으로 변환하여 반환합니다.
  };
  var today = new Date(
    new Date().getFullYear() +
      "-" +
      (new Date().getMonth() + 1) +
      "-" +
      new Date().getDate() +
      "-" +
      "09:00:00",
  );

  const queryClient = useQueryClient();
  const [schedules, setSchedules] = useState<any[]>([]);
  const [todaySchedules, setTodaySchedules] = useState<any[]>([]);
  const data: any = queryClient.getQueryData(["member"]);

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
      <a className="flex w-full bg-blue-50 hover:bg-slate-400" href="/schedule">
        <div className={`${color} me-1 h-auto w-1`}></div>
        <div>
          <div className="fc-event-title truncate ">{today.title}</div>
          <div className="mt-1 truncate">
            {today.originalStart} ~ {today.originalEnd}
          </div>
        </div>
      </a>
    );
  }

  return (
    <div className="col-span-12 xl:col-span-7">
      <div className="rounded-sm border border-stroke bg-white px-5 pb-2.5 pt-6 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
        <div className="mb-6">
          <div>
            <h4 className="text-title-sm2 font-bold text-black dark:text-white">
              오늘 스케줄
            </h4>
            <div className="todayArea mt-7">
              <div className="mt-3 h-125 flex-col space-y-4 overflow-auto">
                {todaySchedules.length > 0 ? (
                  todaySchedules.map((today) => renderTodayContent(today))
                ) : (
                  <div className="flex h-full w-full items-center justify-center">
                    <div className="font-bold">오늘 일정이 없습니다</div>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MainTableFour;
