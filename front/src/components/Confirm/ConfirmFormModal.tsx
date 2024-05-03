"use client";

import { ConfirmModalBox, ConfirmModalContent } from "../Modal/ConfirmModals";

interface ConfirmFormModalProps {
  clickModal: () => void;
}

const ConfirmFormModal: React.FC<ConfirmFormModalProps> = ({ clickModal }) => {
  return (
    <ConfirmModalBox onClick={clickModal}>
      {/* // 모달을 닫는 state함수가 아래로 전파되는 것을 막아줌 */}
      <ConfirmModalContent onClick={(e) => e.stopPropagation()}>
        <div className=" text-xl font-bold">📑결재 양식 선택</div>
        <div>
          <div className="min-w-20 font-bold">양식 검색</div>
          <form className=" mx-auto min-w-90">
            <label
              htmlFor="default-search"
              className="text-gray-900 sr-only mb-2 text-sm font-medium dark:text-white"
            >
              Search
            </label>
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
        <div>
          <button
            onClick={clickModal}
            type="button"
            className="mb-2 me-2 min-w-20 rounded-lg bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-800"
          >
            뒤로가기
          </button>
        </div>
      </ConfirmModalContent>
    </ConfirmModalBox>
  );
};

export default ConfirmFormModal;
