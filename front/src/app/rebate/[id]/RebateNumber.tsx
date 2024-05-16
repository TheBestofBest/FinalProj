'use client'

import Link from "next/link";
import { useQueryClient } from "@tanstack/react-query";
import RebateModify from "../ModifyModal";

interface memberInfo {
  id: BigInteger;
  username: String;
}

const RebateNumber = ({rebate}) => {

  const queryClient = useQueryClient();
  const member = queryClient.getQueryData<memberInfo>(["member"]);

    return (
      <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
        <div className="px-4 py-6 md:px-6 xl:px-7.5">
          <h4 className="text-xl font-semibold text-black dark:text-white">
            {rebate?.year}년 {rebate?.month}월 급여명세서
          </h4>
          {member.username === "admin" ? (
            <div className="flex justify-between">
          <Link
            href="/rebate"
            ><button
            className="whitespace-nowrap rounded bg-primary px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
            >
              뒤로가기
            </button>
          </Link>
            <RebateModify rebateId = {rebate.rebateId}/>
          </div>) :
          (<Link
          href="/rebate/me"
          ><button
          className="whitespace-nowrap rounded bg-primary px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
          >
            뒤로가기
          </button>
        </Link>
        )}
        </div>
  
        <div className="grid grid-cols-4 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-7 md:px-6 2xl:px-7.5">
          <div className="col-span-3 flex items-center">
            <p className="font-medium">직원명 : {rebate?.memberName}</p>
          </div>
          <div className="col-span-2 hidden items-center sm:flex">
            <p className="font-medium">부서 : {rebate?.dept} / 직급 : {rebate?.grade}</p>
          </div>
          <div className="col-span-1 flex items-center">
            <p className="font-medium">사원번호 : {rebate?.memberId}</p>
          </div>
          <div className="col-span-1 flex items-center">
            <p className="font-medium">근무일수 : {rebate?.workedDate}/{rebate?.workDate}</p>
          </div>
        </div>
  
          <div
            className="grid grid-cols-2 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
          >
            <div className="col-span-7 flex items-center">
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
                <div className="h-12.5 w-15 rounded-md">
                </div>
                <p className="text-sm text-black dark:text-white">
                  급여
                </p>
              </div>
            </div>
            <div className="col-span-1 flex items-center">
              <p className="text-sm text-meta-3">{rebate?.salary?.toLocaleString()}￦</p>
            </div>
          </div>
  
          <div
            className="grid grid-cols-2 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
          >
            <div className="col-span-7 flex items-center">
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
                <div className="h-12.5 w-15 rounded-md">
                </div>
                <p className="text-sm text-black dark:text-white">
                  상여금
                </p>
              </div>
            </div>
            <div className="col-span-1 flex items-center">
              <p className="text-sm text-meta-3">{rebate?.bonus?.toLocaleString()}￦</p>
            </div>
          </div>
  
          <div
            className="grid grid-cols-2 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
          >
            <div className="col-span-7 flex items-center">
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
                <div className="h-12.5 w-15 rounded-md">
                </div>
                <p className="text-sm text-black dark:text-white">
                  소득세
                </p>
              </div>
            </div>
            <div className="col-span-1 flex items-center">
              <p className="text-sm text-meta-3">{rebate?.tax?.toLocaleString()}￦</p>
            </div>
          </div>
  
          <div
            className="grid grid-cols-2 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
          >
            <div className="col-span-7 flex items-center">
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
                <div className="h-12.5 w-15 rounded-md">
                </div>
                <p className="text-sm text-black dark:text-white">
                  고용,건강보험
                </p>
              </div>
            </div>
            <div className="col-span-1 flex items-center">
              <p className="text-sm text-meta-3">{rebate?.insurance?.toLocaleString()}￦</p>
            </div>
          </div>
  
          <div
            className="grid grid-cols-2 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5"
          >
            <div className="col-span-7 flex items-center">
              <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
                <div className="h-12.5 w-15 rounded-md">
                </div>
                <p className="text-sm text-black dark:text-white">
                  최종급여
                </p>
              </div>
            </div>
            <div className="col-span-1 flex items-center">
              <p className="text-sm text-meta-3">{rebate?.totalSalary?.toLocaleString()}￦</p>
            </div>
          </div>
  
      </div>
    );
  };
  
  export default RebateNumber;
  