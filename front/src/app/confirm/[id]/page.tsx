"use client";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import HorizontalLinearAlternativeLabelStepper from "@/components/Stepper/Stepper";
import { useState } from "react";
export default function ConfirmDetailPage() {
  // 모달 버튼 클릭 유무를 저장할 state
  const [showModal, setShowModal] = useState(false);

  // 버튼 클릭시 모달 버튼 클릭 유무를 설정하는 state 함수
  const clickModal = () => setShowModal(!showModal);
  return (
    <DefaultLayout>
      <div className="h-full w-full overflow-y-auto">
        <div className="rounded-sm border border-stroke bg-white px-2 pb-2.5 pt-4.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
          <div className="flex max-w-full flex-col items-center justify-center overflow-x-auto text-neutral-950 dark:text-neutral-100">
            <div className="max-w-1000 mb-3">
              {/* a Subject */}
              <div className="w-full items-center">
                <div className="items-center text-3xl font-bold">결재 제목</div>
                <div className="text-lg font-bold text-slate-400">
                  결재 양식
                </div>
              </div>
              {/* b table: 결재 신청자, 기안일, 부서, 직급 ,, 우측에는 승인 진행도  */}
              <div className="relative mb-3 mt-2 flex w-full justify-between overflow-x-auto sm:rounded-lg">
                <table className="text-gray-500 dark:text-gray-400 min-w-400 border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-medium dark:text-white"
                      >
                        결재 기안자
                      </th>
                      <td className="px-6 py-4">김갑환</td>
                    </tr>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-medium dark:text-white"
                      >
                        기안일
                      </th>
                      <td className="px-6 py-4">2024-05-09</td>
                    </tr>
                  </tbody>
                </table>
                <div>
                  <HorizontalLinearAlternativeLabelStepper />
                </div>
              </div>
              {/* c table: 결재 간략설명, 상세 내용포함 테이블 */}
              <div className="relative mt-2 flex w-full justify-between overflow-x-auto shadow-md sm:rounded-lg">
                <table className="text-gray-500 dark:text-gray-400 w-full border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <td className="px-6 py-4">결재 설명</td>
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-medium dark:text-white"
                      >
                        결재 설명
                      </th>
                    </tr>
                  </tbody>
                </table>
                <div></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
}
