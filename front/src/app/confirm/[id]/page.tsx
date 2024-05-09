"use client";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import HorizontalLinearAlternativeLabelStepper from "@/components/Stepper/Stepper";
import Link from "next/link";
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
            {/* 클릭 시 결재 메인 페이지로 이동 */}
            <div className="mb-3 mt-3 flex w-full justify-start">
              <Link
                href="/confirm"
                type="button"
                className="font-base mx-1 mb-2  me-2 rounded-lg bg-gradient-to-r from-cyan-400 via-cyan-500 to-cyan-600 px-5 py-2.5 text-center text-base text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-cyan-300 dark:focus:ring-cyan-800"
              >
                뒤로가기
              </Link>
            </div>
            <div className="mb-3 max-w-3xl">
              {/* a Subject */}
              <div className="w-full items-center">
                <div className="items-center text-3xl font-bold">결재 제목</div>
                <div className="text-lg font-bold text-slate-400">
                  결재 양식
                </div>
              </div>
              {/* b table: 결재 신청자, 기안일, 부서, 직급 ,, 우측에는 승인 진행도  */}
              <div className="relative mb-3 mt-2 flex w-full justify-between overflow-x-auto ">
                <table className="text-gray-500 dark:text-gray-400 min-w-400 border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-bold dark:text-white"
                      >
                        결재 기안자
                      </th>
                      <td className="px-6 py-4">김갑환</td>
                    </tr>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-bold dark:text-white"
                      >
                        기안일
                      </th>
                      <td className="px-6 py-4">2024-05-09</td>
                    </tr>
                  </tbody>
                </table>
                <div className="dark:text-white">
                  <HorizontalLinearAlternativeLabelStepper />
                </div>
              </div>
              {/* c table: 결재 간략설명 */}
              <div className="relative mt-2 flex max-w-full justify-between overflow-x-auto shadow-md">
                <table className="text-gray-500 dark:text-gray-400 min-w-full max-w-[600px] border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 max-w-full border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 overflow-wrap break-word   border px-6 py-4 font-medium dark:text-white"
                      >
                        결재 내용 결재 결재 내용 결재결재 내용 결재결재 내용
                        결재결재 내용 결재결재 내용 결재결재 내용 결재결재 내용
                        결재결재 내용 결재결재 내용 결재결재 내용 결재결재 내용
                        결재결재 내용 결재결재 내용 결재결재 내용 결재
                      </th>
                    </tr>
                  </tbody>
                </table>
                <div></div>
              </div>
              {/* d table:  상세 내용 테이블 => 양식별 component 투입하기 */}
              <div className="relative mt-2 flex w-full justify-between overflow-x-auto shadow-md">
                <table className="text-gray-500 dark:text-gray-400 w-full border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-medium dark:text-white"
                      >
                        양식 별 컴포넌트!
                      </th>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div className="mb-3 mt-3 flex w-full justify-center">
            {/* 수정,삭제: 기안자에게만 보이게 하기 */}
            <button
              type="button"
              className="mb-2 me-2 rounded-lg bg-gradient-to-r from-green-400 via-green-500 to-green-600 px-5 py-2.5 text-center text-lg font-bold text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-green-300 dark:focus:ring-green-800"
            >
              수정
            </button>
            <button
              type="button"
              className="mb-2 me-2 rounded-lg bg-gradient-to-r from-rose-400 via-rose-500 to-rose-600 px-5 py-2.5 text-center text-lg font-bold text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-rose-300 dark:focus:ring-rose-800"
            >
              삭제
            </button>
            {/* 승인, 반려: 결재 승인자에게만 보이게하기 */}
            <button
              type="button"
              className="text-gray-900 mb-2 me-2 rounded-lg bg-gradient-to-r from-lime-200 via-lime-400 to-lime-500 px-5 py-2.5 text-lg  font-bold text-black hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-lime-300 dark:focus:ring-lime-800"
            >
              승인
            </button>
            <button
              type="button"
              className="mb-2 me-2 rounded-lg bg-gradient-to-r from-orange-400 via-orange-500 to-orange-600 px-5 py-2.5 text-center text-lg font-bold text-white hover:bg-slate-900 hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-orange-300 dark:focus:ring-orange-800"
            >
              반려
            </button>
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
}
