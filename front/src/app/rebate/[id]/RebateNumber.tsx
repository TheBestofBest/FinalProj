'use client'

const RebateNumber = ({rebate}) => {
    return (
      <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
        <div className="px-4 py-6 md:px-6 xl:px-7.5">
          <h4 className="text-xl font-semibold text-black dark:text-white">
            {rebate?.year}년 {rebate?.month}월 급여명세서
          </h4>
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
  