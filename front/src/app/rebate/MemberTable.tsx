'use client'

import { BRAND } from "@/types/brand";
import Image from "next/image";
import Link from "next/link";
import { useEffect, useState } from "react";
import RebateDropDown from "./RebateDropDown";
import { useRouter } from "next/navigation";
import { Checkbox } from '@headlessui/react'
import { CheckIcon } from '@heroicons/react/16/solid'

interface Rebate {
  rebateId : number;
  year: string;
  month: string;
  memberName: string;
  memberId: string;
  grade: string;
  dept: string;
  workDate: number;
  workedDate: number;
  salary: number;
  bonus: number;
  tax: number;
  insurance: number;
  totalSalary: number;
}


interface MemberTableProps {
  rebates: Rebate[];
  totalSum: number;
}

const MemberTable: React.FC<MemberTableProps> = ({ rebates, totalSum }) => {

  const [currentYear, setCurrentYear] = useState('');
  const [currentMonth, setCurrentMonth] = useState('');
  const router = useRouter();

  const [isCheckedList, setIsCheckedList] = useState(Array(rebates.length).fill(false));

  const [isChecked, setIsChecked] = useState<boolean>(false);

  const handleCheckboxChange = (key) => {
    const newList = [...isCheckedList];
    newList[key] = !newList[key];
    setIsCheckedList(newList);
  };

  const handleAllCheckboxesChange = () => {
    // 모든 체크박스가 선택되어 있는지 여부 확인
    const allChecked = isCheckedList.every((isChecked) => isChecked);
  
    // 모든 체크박스의 상태를 전부 변경
    const newList = isCheckedList.map(() => !allChecked);
    setIsCheckedList(newList);
  };

  useEffect(() => {
    setCurrentYear(rebates[0]?.year);
    setCurrentMonth(rebates[0]?.month);
  }, [rebates]);



  return (
    <div className="h-40r px-5 pb-2.5 pt-6 shadow-default dark:border-strokedark dark:bg-boxdark overflow-scroll">
      <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
        {currentYear}년 {rebates[0]?.memberId === rebates[1]?.memberId ? (<span>정산내역</span>) : (<span>{currentMonth}월 정산내역</span>)}
      </h4>

      <div className="flex justify-between py-4">
        총 정산 금액 : {totalSum.toLocaleString()}원
          <button
              className="flex items-center whitespace-nowrap rounded bg-primary px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]">
              저장하기
          </button>
      </div>

      <div className="flex flex-col">
        <div className="h-14 grid grid-cols-3 rounded-sm bg-gray-2 dark:bg-meta-4 sm:grid-cols-7">
          <div className="p-2.5 xl:p-5">
            
          <div>
      <label
        htmlFor="checkboxLabelTwo"
        className="flex cursor-pointer select-none items-center"
      >
        <div className="relative">
          <input
            type="checkbox"
            id="checkboxLabelTwo"
            className="sr-only"
            onChange={handleAllCheckboxesChange}
          />
          <div
            className={`mr-4 flex h-5 w-5 items-center justify-center rounded border ${
              isChecked && "border-primary bg-gray dark:bg-transparent"
            }`}
          >
            <span className={`opacity-0 ${isChecked && "!opacity-100"}`}>
              <svg
                width="11"
                height="8"
                viewBox="0 0 11 8"
                fill="none"
                xmlns="http://www.w3.org/2000/svg"
              >
                <path
                  d="M10.0915 0.951972L10.0867 0.946075L10.0813 0.940568C9.90076 0.753564 9.61034 0.753146 9.42927 0.939309L4.16201 6.22962L1.58507 3.63469C1.40401 3.44841 1.11351 3.44879 0.932892 3.63584C0.755703 3.81933 0.755703 4.10875 0.932892 4.29224L0.932878 4.29225L0.934851 4.29424L3.58046 6.95832C3.73676 7.11955 3.94983 7.2 4.1473 7.2C4.36196 7.2 4.55963 7.11773 4.71406 6.9584L10.0468 1.60234C10.2436 1.4199 10.2421 1.1339 10.0915 0.951972ZM4.2327 6.30081L4.2317 6.2998C4.23206 6.30015 4.23237 6.30049 4.23269 6.30082L4.2327 6.30081Z"
                  fill="#3056D3"
                  stroke="#3056D3"
                  strokeWidth="0.4"
                ></path>
              </svg>
            </span>
          </div>
        </div>
      </label>
    </div>

          </div>
          <div className="p-2.5 xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              임직원
            </h5>
          </div>
          <div className="p-2.5 text-center xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              소속부서
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

        <form>
        {rebates.map((rebate, key) => (
          <div
            className={`grid grid-cols-3 sm:grid-cols-7 h-16 ${
              key === rebates.length - 1
                ? ""
                : "border-b border-stroke dark:border-strokedark"
            }`}
            key={key}
          >
          <div className="flex items-center gap-3 p-2.5 xl:p-5">
            <label
              htmlFor={`${rebate.rebateId}`}
              className="cursor-pointer select-none items-center"
            >
              <div className="relative">
                <input
                  type="checkbox"
                  id={`${rebate.rebateId}`}
                  value={`${rebate.rebateId}`}
                  className="sr-only"
                  checked={isCheckedList[key]}
                  onChange={() => handleCheckboxChange(key)}
                />
                <div
                  className={`mr-4 flex h-5 w-5 items-center justify-center rounded border ${
                    isCheckedList[key] && "border-primary bg-gray dark:bg-transparent"
                  }`}
                >
                  <span className={`opacity-0 ${isCheckedList[key] && "!opacity-100"}`}>
                    <svg
                      width="11"
                      height="8"
                      viewBox="0 0 11 8"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                    >
                      <path
                        d="M10.0915 0.951972L10.0867 0.946075L10.0813 0.940568C9.90076 0.753564 9.61034 0.753146 9.42927 0.939309L4.16201 6.22962L1.58507 3.63469C1.40401 3.44841 1.11351 3.44879 0.932892 3.63584C0.755703 3.81933 0.755703 4.10875 0.932892 4.29224L0.932878 4.29225L0.934851 4.29424L3.58046 6.95832C3.73676 7.11955 3.94983 7.2 4.1473 7.2C4.36196 7.2 4.55963 7.11773 4.71406 6.9584L10.0468 1.60234C10.2436 1.4199 10.2421 1.1339 10.0915 0.951972ZM4.2327 6.30081L4.2317 6.2998C4.23206 6.30015 4.23237 6.30049 4.23269 6.30082L4.2327 6.30081Z"
                        fill="#3056D3"
                        stroke="#3056D3"
                        strokeWidth="0.4"
                      ></path>
                    </svg>
                  </span>
                </div>
              </div>
            </label>
          </div>


            <div className="flex items-center gap-3 p-2.5 xl:p-5">
              <p className="hidden text-black dark:text-white sm:block">
                {rebate.memberName}
              </p>
            </div>

            <div className="flex items-center justify-center p-2.5 xl:p-5">
              <p className="text-black dark:text-white">{rebate.dept}</p>
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
              className="flex items-center whitespace-nowrap rounded-md bg-meta-3 px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
            >
              {/* className="rounded-md bg-meta-3 px-3 py-2 text-center text-white hover:bg-opacity-90" */}
              상세보기
            </Link>
            </div>
          </div>
        ))}
        </form>
      </div>
    </div>
  );
};

export default MemberTable;