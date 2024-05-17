import { ApexOptions } from "apexcharts";
import React, { useState } from "react";
import ReactApexChart from "react-apexcharts";

interface ChartThreeState {
  series: number[];
}

const options: ApexOptions = {
  chart: {
    fontFamily: "Satoshi, sans-serif",
    type: "donut",
  },
  colors: ["#3C50E0", "#6577F3"],
  labels: ["남성", "여성"],
  legend: {
    show: false,
    position: "bottom",
  },

  plotOptions: {
    pie: {
      donut: {
        size: "65%",
        background: "transparent",
      },
    },
  },
  dataLabels: {
    enabled: false,
  },
  responsive: [
    {
      breakpoint: 2600,
      options: {
        chart: {
          width: 380,
        },
      },
    },
    {
      breakpoint: 640,
      options: {
        chart: {
          width: 200,
        },
      },
    },
  ],
};

interface Stats {
    totalPeople : string;
    numberOfMan : number;
    numberOfWoman : number;
    two : number;
    three : number;
    four : number;
    five : number;
    salaryOne : number;
    salaryTwo : number;
    salaryThree : number;
    salaryFour : number;
    salaryFive : number;
    salarySix : number;
    salarySeven : number;
  }

const SexChart = ({stats} : Stats) => {
  

    const total = stats?.numberOfMan + stats?.numberOfWoman;
    const man = (stats?.numberOfMan / total) * 100; // 56이 전체에서 차지하는 비율
    const woman = (stats?.numberOfWoman / total) * 100; // 44가 전체에서 차지하는 비율

    const [state, setState] = useState<ChartThreeState>({
        series: [man, woman],
      });

    const handleReset = () => {
        setState((prevState) => ({
        ...prevState,
        series: [man, woman],
        }));
    };
    handleReset;

  return (
    <div className="col-span-12 rounded-sm border border-stroke bg-white px-5 pb-5 pt-7.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:col-span-5">
      <div className="mb-3 justify-between gap-4 sm:flex">
        <div>
          <h5 className="text-xl font-semibold text-black dark:text-white">
            직원 성비 현황
          </h5>
        </div>
      </div>

      <div className="mb-2">
        <div id="chartThree" className="mx-auto flex justify-center">
          <ReactApexChart
            options={options}
            series={state.series}
            type="donut"
          />
        </div>
      </div>

      <div className="-mx-8 flex flex-wrap items-center justify-center gap-y-3">
        <div className="w-full px-8 sm:w-1/2">
          <div className="flex w-full items-center">
            <span className="mr-2 block h-3 w-full max-w-3 rounded-full bg-primary"></span>
            <p className="flex w-full justify-between text-sm font-medium text-black dark:text-white">
              <span> 남성 </span>
              <span> 65% </span>
            </p>
          </div>
        </div>
        <div className="w-full px-8 sm:w-1/2">
          <div className="flex w-full items-center">
            <span className="mr-2 block h-3 w-full max-w-3 rounded-full bg-[#6577F3]"></span>
            <p className="flex w-full justify-between text-sm font-medium text-black dark:text-white">
              <span> 여성 </span>
              <span> 34% </span>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default SexChart;
