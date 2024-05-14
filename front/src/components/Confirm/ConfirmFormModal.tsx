"use client";

import { useState } from "react";
import { ConfirmModalBox, ConfirmModalContent } from "../Modal/ConfirmModals";
import VacationForm from "../ConfirmForm/VacationForm";
import SoftwarePurchaseRequestForm from "../ConfirmForm/SoftwarePurchaseRequestForm";
import { ConfirmFormType } from "@/types/Confirm/ConfirmTypes";

interface ConfirmFormModalProps {
  clickModal: () => void;
}

const ConfirmFormModal: React.FC<ConfirmFormModalProps> = ({ clickModal }) => {
  const [selectedForm, setSelectedForm] = useState<string | null>(null);
  const [confirmFormTypes, setConfirmFormTypes] = useState<ConfirmFormType[]>(
    [],
  );
  const [confirmFormType, setConfirmFormType] = useState<ConfirmFormType>({
    formName: "",
    formDescription: "",
  });

  // 선택된 양식에 따라 세부사항 작성 창으로 전환하는 함수
  const handleFormClick = (formType: string) => {
    setSelectedForm(formType);
    console.log(formType);
  };

  // 현재 선택된 양식에 따라 세부사항 작성 창을 보여주는 부분
  const renderFormDetails = () => {
    if (selectedForm === "휴가 신청") {
      return (
        <div className="mx-auto flex w-full justify-items-center">
          {/* 휴가 신청 양식의 세부사항 작성 창 */}
          {/* 이 곳에 휴가 신청 양식에 대한 세부사항 작성 컴포넌트를 추가하세요 */}
          <VacationForm confirmFormType={} />
        </div>
      );
    } else if (selectedForm === "Software 구매 신청") {
      return (
        <div>
          {/* Software 구매 신청 양식의 세부사항 작성 창 */}
          {/* 이 곳에 Software 구매 신청 양식에 대한 세부사항 작성 컴포넌트를 추가하세요 */}
          <SoftwarePurchaseRequestForm />
        </div>
      );
    } else {
      return null;
    }
  };

  return (
    <ConfirmModalBox onClick={clickModal}>
      {/* // 모달을 닫는 state함수가 아래로 전파되는 것을 막아줌 */}
      <ConfirmModalContent onClick={(e) => e.stopPropagation()}>
        {!selectedForm && (
          <div className=" text-xl font-bold">📑결재 양식 선택</div>
        )}
        {selectedForm && (
          <div className=" text-xl font-bold">📑{selectedForm}</div>
        )}
        {/* 결재 양식 선택 시  양식 검색 숨기기 */}
        {!selectedForm && (
          <div>
            <div className="min-w-20 font-bold">양식 검색</div>
            <form className=" mx-auto min-w-90">
              <label
                htmlFor="default-search"
                className="text-gray-900 sr-only mb-2 text-sm font-medium dark:text-white"
              >
                Search
              </label>
              {/* 검색 창 */}
              <div className="relative">
                <div className="pointer-events-none absolute inset-y-0 start-0 flex items-center ps-3">
                  <svg
                    className="text-gray-500 dark:text-gray-400 h-4 w-4"
                    aria-hidden="true"
                    xmlns="http://www.w3.org/2000/svg"
                    fill="none"
                    viewBox="0 0 20 20"
                  >
                    <path
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
                    />
                  </svg>
                </div>
                <input
                  type="search"
                  id="default-search"
                  className="text-gray-900 border-gray-300 bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-4 ps-10 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
                  placeholder="Search Mockups, Logos..."
                  required
                />
                <button
                  type="submit"
                  className="absolute bottom-2.5 end-2.5 rounded-lg bg-blue-700 px-4 py-2 text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                >
                  Search
                </button>
              </div>
            </form>
          </div>
        )}
        {!selectedForm && (
          <div className="relative overflow-x-auto">
            <table className="text-gray-500 dark:text-gray-400 w-full text-left text-sm rtl:text-right">
              <thead className="text-gray-700 bg-gray-50 dark:bg-gray-700 dark:text-gray-400 text-xs uppercase">
                <tr className="hover:bg-gray-200 dark:hover:bg-gray-700">
                  <th scope="col" className="px-6 py-3">
                    Form Type
                  </th>
                  <th scope="col" className="px-6 py-3">
                    Description
                  </th>
                </tr>
              </thead>
              <tbody>
                {/* tr 반복 시키기 */}
                {confirmFormTypes?.map((confirmFormType) => (
                  <tr
                    className="confirmFormType dark:bg-gray-800 dark:border-gray-700 hover:bg-gray-200 dark:hover:bg-gray-700 border-b bg-white"
                    onClick={() => handleFormClick("휴가 신청")}
                  >
                    <th
                      scope="row"
                      className="text-gray-900 whitespace-nowrap px-6 py-4 font-medium dark:text-white"
                    >
                      휴가 신청
                    </th>
                    <td className="px-6 py-4">휴가 보내줘요</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
        {selectedForm && renderFormDetails()}
        {!selectedForm && (
          <div>
            <button
              onClick={clickModal}
              type="button"
              className="mb-2 me-2 min-w-20 rounded-lg bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-800"
            >
              뒤로가기
            </button>
          </div>
        )}
        {selectedForm && (
          <div className="flex justify-start">
            <button
              onClick={() => handleFormClick("")}
              type="button"
              className="mb-2 me-2 rounded-lg bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-800"
            >
              뒤로가기
            </button>
          </div>
        )}
      </ConfirmModalContent>
    </ConfirmModalBox>
  );
};

export default ConfirmFormModal;
