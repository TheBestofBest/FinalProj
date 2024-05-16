"use client";
import { useEffect, useState } from "react";
import {
  DocumentIcon,
  DocumentTextIcon,
  EnvelopeIcon,
  EnvelopeOpenIcon,
  TrashIcon,
  PaperAirplaneIcon
} from '@heroicons/react/24/solid'
import api from "@/util/api";
import { useQueryClient } from "@tanstack/react-query";

const MainTableOne = () => {
  const queryClient = useQueryClient();
  const memberData: any = queryClient.getQueryData(["member"]);

  const [isChecked, setIsChecked] = useState<boolean>(false);
  const [allMails, setAllMails] = useState({});

  const fetchAllMails = async () => {
    try {
      const memberId = memberData.memberId;
      const response = await api.get(`/api/v1/mailboxes/${memberId}/mails/all`);
      console.log(response);

      if (response.data.isSuccess) {
        const allMails = response.data.data;
        if (allMails) {
          console.log("============================================================");
          console.log(allMails); // 전체 메일 목록 확인
          console.log("전체 메일 목록(allMails 상태):", allMails); // 상태 값 확인
          setAllMails(allMails);
        }
      } else {
        console.error("전체 메일 불러오기 실패:", response.data.message);
      }
    } catch (error) {
      console.error("전체 메일 불러오기 에러:", error);
    }
  };

  useEffect(() => {
    fetchAllMails();
  }, []);
  return (
    <div className="min-h-[590px] rounded-sm border border-stroke  bg-white pb-2.5 pt-4.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
      <div className="px-4 py-6 md:px-6 xl:px-7.5">
        <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
          전체 메일
        </h4>
        <div className="grid grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5">
          <div className="col-span-2 flex items-center">
            <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
              <div className="relative">
                <input
                  type="checkbox"
                  id="checkboxMail"
                  className="sr-only"
                  onChange={() => {
                    setIsChecked(!isChecked);
                  }}
                />
                <div
                  className={`mr-4 flex h-5 w-5 items-center justify-center rounded border ${isChecked && "border-primary bg-gray dark:bg-transparent"
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
              <div className="h-5 w-15 rounded-md text-sm font-semibold text-black dark:text-white">
                확인
              </div>
              <p className="text-sm font-semibold text-black dark:text-white">
                보낸 사람
              </p>
            </div>
          </div>
          <div className="ml-4 col-span-4 hidden items-center sm:flex">
            <p className="text-sm font-semibold text-black dark:text-white">제목</p>
          </div>
          <div className="col-span-1 flex items-center">
            <p className="text-sm font-semibold text-black dark:text-white">답장</p>
          </div>
          <div className="col-span-1 flex items-center">
            <p className="text-sm font-semibold text-black dark:text-white">삭제</p>
          </div>
        </div>
        {allMails.length > 0 ? (
          allMails.map(mail => (
            <div
              className="grid h-screen grid-cols-6 border-t border-stroke px-4 py-4.5 dark:border-strokedark sm:grid-cols-8 md:px-6 2xl:px-7.5 duration-300 ease-in-out hover:bg-meta-2 dark:hover:bg-meta-4"
              key={mail.id}
            >
              <div className="col-span-2 flex items-center">
                <div className="flex flex-col gap-4 sm:flex-row sm:items-center">
                  <div className="relative">
                    <input
                      type="checkbox"
                      id="checkboxMail"
                      className="sr-only"
                      onChange={() => {
                        setIsChecked(!isChecked);
                      }}
                    />
                    <div
                      className={`mr-4 flex h-5 w-5 items-center justify-center rounded border ${isChecked && "border-primary bg-gray dark:bg-transparent"
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
                  <div className="h-7 w-15 rounded-md text-primary">
                    <EnvelopeIcon
                      className="fill-current mb-1.5"
                      width="18"
                      height="18"
                      viewBox="0 0 18 18"
                      fill="none"
                      xmlns="http://www.w3.org/2000/svg"
                      style={{ overflow: 'visible' }}
                    >
                      <path
                        d="M15.7499 2.9812H14.2874V2.36245C14.2874 2.02495 14.0062 1.71558 13.6405 1.71558C13.2749 1.71558 12.9937 1.99683 12.9937 2.36245V2.9812H4.97803V2.36245C4.97803 2.02495 4.69678 1.71558 4.33115 1.71558C3.96553 1.71558 3.68428 1.99683 3.68428 2.36245V2.9812H2.2499C1.29365 2.9812 0.478027 3.7687 0.478027 4.75308V14.5406C0.478027 15.4968 1.26553 16.3125 2.2499 16.3125H15.7499C16.7062 16.3125 17.5218 15.525 17.5218 14.5406V4.72495C17.5218 3.7687 16.7062 2.9812 15.7499 2.9812ZM1.77178 8.21245H4.1624V10.9968H1.77178V8.21245ZM5.42803 8.21245H8.38115V10.9968H5.42803V8.21245ZM8.38115 12.2625V15.0187H5.42803V12.2625H8.38115ZM9.64678 12.2625H12.5999V15.0187H9.64678V12.2625ZM9.64678 10.9968V8.21245H12.5999V10.9968H9.64678ZM13.8374 8.21245H16.228V10.9968H13.8374V8.21245ZM2.2499 4.24683H3.7124V4.83745C3.7124 5.17495 3.99365 5.48433 4.35928 5.48433C4.7249 5.48433 5.00615 5.20308 5.00615 4.83745V4.24683H13.0499V4.83745C13.0499 5.17495 13.3312 5.48433 13.6968 5.48433C14.0624 5.48433 14.3437 5.20308 14.3437 4.83745V4.24683H15.7499C16.0312 4.24683 16.2562 4.47183 16.2562 4.75308V6.94683H1.77178V4.75308C1.77178 4.47183 1.96865 4.24683 2.2499 4.24683ZM1.77178 14.5125V12.2343H4.1624V14.9906H2.2499C1.96865 15.0187 1.77178 14.7937 1.77178 14.5125ZM15.7499 15.0187H13.8374V12.2625H16.228V14.5406C16.2562 14.7937 16.0312 15.0187 15.7499 15.0187Z"
                        fill=""
                      />
                    </EnvelopeIcon>
                  </div>
                  <p className="text-sm font-semibold text-black dark:text-white">
                    {mail.senderName}
                  </p>
                </div>
              </div>
              <div className="col-span-4 hidden items-center sm:flex">
                <p className="text-sm font-semibold text-black dark:text-white">
                  {mail.title}
                </p>
              </div>
              <div className="col-span-1 flex items-center">
                <p className="text-sm font-semibold text-meta-3">삭제</p>
              </div>
              <div className="col-span-1 flex items-center">
                <p className="text-sm font-semibold text-meta-3">{mail.receiveDate}</p>
              </div>
            </div>
          ))
        ) : (
          <div className="min-h-[390px] border-t border-stroke px-4 py-4.5 dark:border-strokedark md:px-6 2xl:px-7.5 duration-300 ease-in-out bg-meta-2 dark:bg-meta-4 flex justify-center items-center">
            <div className="flex items-center">
              <div className="flex sm:flex-row sm:items-center">
                <div className="h-7 w-15 rounded-md">
                  <EnvelopeIcon
                    className="fill-current mb-1.5"
                    width="18"
                    height="18"
                    viewBox="0 0 18 18"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                    style={{ overflow: 'visible' }}
                  >
                    <path
                      d="M15.7499 2.9812H14.2874V2.36245C14.2874 2.02495 14.0062 1.71558 13.6405 1.71558C13.2749 1.71558 12.9937 1.99683 12.9937 2.36245V2.9812H4.97803V2.36245C4.97803 2.02495 4.69678 1.71558 4.33115 1.71558C3.96553 1.71558 3.68428 1.99683 3.68428 2.36245V2.9812H2.2499C1.29365 2.9812 0.478027 3.7687 0.478027 4.75308V14.5406C0.478027 15.4968 1.26553 16.3125 2.2499 16.3125H15.7499C16.7062 16.3125 17.5218 15.525 17.5218 14.5406V4.72495C17.5218 3.7687 16.7062 2.9812 15.7499 2.9812ZM1.77178 8.21245H4.1624V10.9968H1.77178V8.21245ZM5.42803 8.21245H8.38115V10.9968H5.42803V8.21245ZM8.38115 12.2625V15.0187H5.42803V12.2625H8.38115ZM9.64678 12.2625H12.5999V15.0187H9.64678V12.2625ZM9.64678 10.9968V8.21245H12.5999V10.9968H9.64678ZM13.8374 8.21245H16.228V10.9968H13.8374V8.21245ZM2.2499 4.24683H3.7124V4.83745C3.7124 5.17495 3.99365 5.48433 4.35928 5.48433C4.7249 5.48433 5.00615 5.20308 5.00615 4.83745V4.24683H13.0499V4.83745C13.0499 5.17495 13.3312 5.48433 13.6968 5.48433C14.0624 5.48433 14.3437 5.20308 14.3437 4.83745V4.24683H15.7499C16.0312 4.24683 16.2562 4.47183 16.2562 4.75308V6.94683H1.77178V4.75308C1.77178 4.47183 1.96865 4.24683 2.2499 4.24683ZM1.77178 14.5125V12.2343H4.1624V14.9906H2.2499C1.96865 15.0187 1.77178 14.7937 1.77178 14.5125ZM15.7499 15.0187H13.8374V12.2625H16.228V14.5406C16.2562 14.7937 16.0312 15.0187 15.7499 15.0187Z"
                      fill=""
                    />
                  </EnvelopeIcon>
                </div>
                <p className="text-xl font-semibold text-black dark:text-white">
                  메일 함이 비었습니다
                </p>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default MainTableOne;
