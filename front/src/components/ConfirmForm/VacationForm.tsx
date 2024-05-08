"use client";
import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
const VacationForm = () => {
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  // 결재 신청 메서드 컴포넌트에서 해결
  return (
    <div className=" min-w-90 ">
      <br />
      <div>
        <label className="text-gray-900 mb-2 block text-base font-bold dark:text-white">
          제목
        </label>
        <input
          type="text"
          className="text-gray-900 border-gray-300 bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 text-s mb-2 block w-full rounded-lg border p-2 focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          placeholder={"결재 제목 입력 ex) 전율택 사원 휴가 신청"}
        />
        <label className="text-gray-900 mb-2 block text-base font-bold dark:text-white">
          결재 신청인
        </label>
        <input
          type="text"
          className="text-gray-900 border-gray-300 bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 text-s mb-2 block w-full cursor-not-allowed rounded-lg border p-2 focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          defaultValue={"member username + member grade 받아서 투입"}
          disabled
        />
        <label className="text-gray-900 mb-2 block text-base font-bold dark:text-white">
          결재 신청일
        </label>
        <input
          type="text"
          className="text-gray-900 border-gray-300 bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 text-s mb-2 block w-full cursor-not-allowed rounded-lg border p-2 focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          defaultValue={"현재 시간 작성"}
          disabled
        />
        <label className="text-gray-900 mb-2 block text-base font-bold dark:text-white">
          결재 설명
        </label>
        <input
          type="text"
          className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 text-s block w-full rounded-lg border p-2.5 focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          placeholder={"결재에 대해서 설명 ex) 이런저런 결재입니다."}
        />
        <label className="text-gray-900 mb-2 mt-2 block text-base font-bold dark:text-white">
          휴가 날짜 선택
        </label>
        <div className="flex items-center">
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            selectsStart
            startDate={startDate}
            endDate={endDate}
            className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
            placeholderText="Select date start"
          />
          <span className="text-gray-500 mx-4">to2</span>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            selectsEnd
            startDate={startDate}
            endDate={endDate}
            minDate={startDate}
            className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
            placeholderText="Select date end"
          />
        </div>
      </div>
    </div>
  );
};

export default VacationForm;
