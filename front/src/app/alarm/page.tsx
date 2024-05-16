"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { useQueryClient } from "@tanstack/react-query";
import { useSearchParams } from "next/navigation";
import AlarmDetail from "./alarmDetail";

const AlarmListPage = () => {
  const queryClient = useQueryClient();
  const data: any = queryClient.getQueryData(["member"]);
  const searchParams = useSearchParams();
  const [totalPages, setTotalPages] = useState(0);
  const [alarms, setAlarms] = useState([]);
  const [alarm, setAlarm] = useState({});
  const [alarmDetail, setAlarmDetail] = useState(false);

  const page: number =
    searchParams.get("page") === null ? 0 : Number(searchParams.get("page"));

  const getAlarms = async () => {
    const data = await api.get(`/api/v1/alarms?page=${page}`).then((res) => {
      console.log(res);
      if (res.data.isSuccess) {
        if (!res.data.data.empty) {
          return res.data.data.alarms;
        }
      }
    });

    setAlarms(data.content);
    setTotalPages(data.totalPages);
  };

  const rendering = () => {
    if (page === 0) {
      return pageATag(0, 2);
    } else if (Number(page + 1) === Number(totalPages)) {
      return pageATag(page - 2, page);
    } else {
      return pageATag(page - 2, Number(page + 2));
    }
  };

  const pageATag = (start: number, end: number) => {
    const result = [];
    var s = start < 0 ? 0 : start;
    var e = 0;
    if (totalPages <= 2) {
      e = totalPages - 1;
    } else {
      e = totalPages - 1 < end ? totalPages - 1 : end;
    }
    for (let i = s; i <= e; i++) {
      if (i == page) {
        result.push(
          <button
            key={i}
            disabled
            aria-current="page"
            className="relative z-10 inline-flex items-center bg-indigo-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          >
            {i + 1}
          </button>,
        );
      } else {
        result.push(
          <a
            key={i}
            href={"/alarm?page=" + i}
            className="text-gray-900 ring-gray-300 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 text-sm font-semibold ring-1 ring-inset focus:z-20 focus:outline-offset-0"
          >
            {i + 1}
          </a>,
        );
      }
    }
    return result;
  };

  useEffect(() => {
    getAlarms();
  }, []);
  const handlerModal = (id: number) => {
    const detailData = alarms.find((alarm) => alarm.id === id);
    setAlarm(detailData);
    setAlarmDetail(true);
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Alarm" />

      <div className="flex flex-col justify-between border-stroke bg-white align-bottom shadow-3 dark:border-strokedark dark:bg-boxdark">
        <div className="rounded-sm border border-stroke bg-white px-5 pb-2.5 pt-6 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
          <div className="flex flex-col">
            <div className="flex rounded-sm bg-gray-2 dark:bg-meta-4">
              <div className="w-1/12 p-2.5 text-center xl:p-5">
                <h5 className="truncate text-sm font-medium uppercase xsm:text-base">
                  Id
                </h5>
              </div>
              <div className="w-2/12 p-2.5 text-center xl:p-5">
                <h5 className="truncate text-sm font-medium uppercase xsm:text-base">
                  Category
                </h5>
              </div>
              <div className="w-7/12 p-2.5 text-center xl:p-5">
                <h5 className="truncate text-sm font-medium uppercase xsm:text-base">
                  Content
                </h5>
              </div>
              <div className="hidden w-2/12 p-2.5 text-center sm:block xl:p-5">
                <h5 className="truncate text-sm font-medium uppercase xsm:text-base">
                  Date
                </h5>
              </div>
            </div>

            {alarms.map((alarm) => {
              var color = "";
              var category = "";
              if (alarm.relationName === "all") {
                color = "text-rose-700";
                category = "전체";
              }
              if (alarm.relationName === "dept") {
                color = "text-green-700";
                category = "부서";
              }
              if (alarm.relationName === "member") {
                color = "text-blue-700";
                category = "개인";
              }
              return (
                <div
                  className={`flex hover:cursor-pointer hover:bg-slate-100 `}
                  key={alarm.id}
                  onClick={() => handlerModal(alarm.id)}
                >
                  <div className="w-1/12 p-2.5 text-center xl:p-5">
                    <p className="truncate text-black dark:text-white">
                      {alarm.id}
                    </p>
                  </div>

                  <div className="w-2/12  p-2.5 text-center xl:p-5">
                    <p className={"truncate font-bold " + color}>{category}</p>
                  </div>

                  <div className="w-7/12 p-2.5  text-center xl:p-5">
                    <p className="truncate text-center text-black dark:text-white">
                      {alarm.content}
                    </p>
                  </div>

                  <div className="w-2/12 p-2.5  text-center xl:p-5">
                    <p className="truncate text-meta-5">
                      {alarm.createDate.split("T")[0]}
                    </p>
                  </div>
                </div>
              );
            })}
          </div>
        </div>

        <div className="text-center">
          <div className="border-gray-200 flex justify-center border-t bg-white px-4 py-3 sm:px-6">
            <div>
              <nav
                className="isolate inline-flex -space-x-px rounded-md shadow-sm"
                aria-label="Pagination"
              >
                {rendering()}
              </nav>
            </div>
          </div>
        </div>
      </div>
      {alarmDetail ? (
        <AlarmDetail detailData={alarm} showAlarmDetail={setAlarmDetail} />
      ) : null}
    </DefaultLayout>
  );
};

export default AlarmListPage;
