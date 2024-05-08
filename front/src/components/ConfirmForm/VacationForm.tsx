const VacationForm = () => {
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
        <div date-rangepicker className="flex items-center">
          <div className="relative">
            <div className="pointer-events-none absolute inset-y-0 start-0 flex items-center ps-3">
              <svg
                className="text-gray-500 dark:text-gray-400 h-4 w-4"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
              </svg>
            </div>
            <input
              name="start"
              type="text"
              className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 ps-10  text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
              placeholder="Select date start"
            />
          </div>
          <span className="text-gray-500 mx-4">to</span>
          <div className="relative">
            <div className="pointer-events-none absolute inset-y-0 start-0 flex items-center ps-3">
              <svg
                className="text-gray-500 dark:text-gray-400 h-4 w-4"
                aria-hidden="true"
                xmlns="http://www.w3.org/2000/svg"
                fill="currentColor"
                viewBox="0 0 20 20"
              >
                <path d="M20 4a2 2 0 0 0-2-2h-2V1a1 1 0 0 0-2 0v1h-3V1a1 1 0 0 0-2 0v1H6V1a1 1 0 0 0-2 0v1H2a2 2 0 0 0-2 2v2h20V4ZM0 18a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V8H0v10Zm5-8h10a1 1 0 0 1 0 2H5a1 1 0 0 1 0-2Z" />
              </svg>
            </div>
            <input
              name="end"
              type="text"
              className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 ps-10  text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
              placeholder="Select date end"
            />
          </div>
        </div>
      </div>
    </div>
  );
};

export default VacationForm;
