'use client'

import { BRAND } from "@/types/brand";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import DropDownMenu from "../RebateDropDown";

const RebateLists = ({rebates}) => {


  return (
    <div className="rounded-sm border border-stroke bg-white px-5 pb-2.5 pt-6 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
      <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
        급여명세서 목록
      </h4>

      <div className="flex flex-col">
        <div className="grid grid-cols-3 rounded-sm bg-gray-2 dark:bg-meta-4 sm:grid-cols-6">
          <div className="p-2.5 xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              날짜
            </h5>
          </div>
          <div className="p-2.5 text-center xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              부서 / 직원
            </h5>
          </div>
          <div className="p-2.5 text-center xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              직급
            </h5>
          </div>
          <div className="hidden p-2.5 text-center sm:block xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              사원번호
            </h5>
          </div>
          <div className="hidden p-2.5 text-center sm:block xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              급여
            </h5>
          </div>
        </div>

        {rebates.map((rebate, key) => (
          <div
            className={`grid grid-cols-3 sm:grid-cols-6 ${
              key === rebates.length - 1
                ? ""
                : "border-b border-stroke dark:border-strokedark"
            }`}
            key={key}
          >
            <div className="flex items-center gap-3 p-2.5 xl:p-5">
              <p className="hidden text-black dark:text-white sm:block">
                {rebate.year}년 {rebate.month}월
              </p>
            </div>

            <div className="flex items-center justify-center p-2.5 xl:p-5">
              <p className="text-black dark:text-white">{rebate.dept} / {rebate.memberName}</p>
            </div>

            <div className="flex items-center justify-center p-2.5 xl:p-5">
              <p className="text-meta-3">{rebate.grade}</p>
            </div>

            <div className="hidden items-center justify-center p-2.5 sm:flex xl:p-5">
              <p className="text-black dark:text-white">{rebate.memberId}</p>
            </div>

            <div className="hidden items-center justify-center p-2.5 sm:flex xl:p-5">
              <p className="text-meta-5">{rebate.totalSalary.toLocaleString()}원</p>
            </div>

            <div className="hidden items-center justify-center p-2.5 sm:flex xl:p-5">
            <Link
              href={`/rebate/${rebate.id}`}
              className="rounded-md bg-meta-3 px-3 py-2 text-center text-white hover:bg-opacity-90"
            >
              상세보기
            </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RebateLists;