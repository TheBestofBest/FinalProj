"use client";
import "@fullcalendar/common/main.css";
import "@/css/calendar.css";
import "react-datepicker/dist/react-datepicker.css";
import { useQuery } from "@tanstack/react-query";
import { Cog6ToothIcon } from "@heroicons/react/24/solid";
import { Menu, Transition } from "@headlessui/react";
import api from "@/util/api";
import DatePicker from "react-datepicker";
import { useState } from "react";
interface DetailModalProps {
  showAlarmDetail: (value: boolean) => void;
  detailData: {};
}
const AlarmDetail: React.FC<DetailModalProps> = ({
  showAlarmDetail,
  detailData,
}) => {
  var color = "black";
  var category = "";
  console.log(detailData);
  switch (detailData.relationName) {
    case "all":
      color = "text-red";
      category = "전체";
      break;
    case "dept":
      color = "text-green-700";
      category = "부서";
      break;
    case "member":
      color = "text-blue-700";
      category = "개인";
      break;
    default:
      color = "text-gray-700";
      break;
  }
  return (
    <>
      <div className="  fixed inset-0 z-50 flex items-center justify-center overflow-y-auto overflow-x-hidden outline-none focus:outline-none">
        <div className="relative mx-auto my-6 w-180">
          {/*content*/}
          <div className="relative flex w-full flex-col rounded-lg border-0 bg-white shadow-lg outline-none focus:outline-none">
            {/*header*/}
            <div className="border-blueGray-200 flex items-start justify-between rounded-t border-b border-solid p-5">
              <h3 className={`${color} text-3xl font-semibold `}>{category}</h3>
            </div>
            {/*body*/}
            <div className="relative p-6">
              <div className="grid h-50 content-between gap-3 overflow-auto">
                {detailData && detailData.content}
              </div>
              <div className="flex justify-end">
                {detailData && detailData.createDate.split("T")[0]}
              </div>
            </div>

            {/*  */}

            {/*footer*/}
            <div className="border-blueGray-200 flex items-center justify-end rounded-b border-t border-solid p-3">
              <button
                className="text-red-500 background-transparent mb-1 mr-1 px-6 py-2 text-sm font-bold uppercase outline-none transition-all duration-150 ease-linear focus:outline-none"
                type="button"
                onClick={() => showAlarmDetail(false)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="fixed inset-0 z-40 bg-black opacity-25"></div>
    </>
  );
};

export default AlarmDetail;
