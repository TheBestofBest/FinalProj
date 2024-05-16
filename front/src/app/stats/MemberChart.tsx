import { ApexOptions } from "apexcharts";
import React, { useState } from "react";
import ReactApexChart from "react-apexcharts";

const options: ApexOptions = {
  colors: ["#3C50E0", "#80CAEE"],
  chart: {
    fontFamily: "Satoshi, sans-serif",
    type: "bar",
    height: 335,
    stacked: true,
    toolbar: {
      show: false,
    },
    zoom: {
      enabled: false,
    },
  },

  responsive: [
    {
      breakpoint: 1536,
      options: {
        plotOptions: {
          bar: {
            borderRadius: 0,
            columnWidth: "25%",
          },
        },
      },
    },
  ],
  plotOptions: {
    bar: {
      horizontal: false,
      borderRadius: 0,
      columnWidth: "25%",
      borderRadiusApplication: "end",
      borderRadiusWhenStacked: "last",
    },
  },
  dataLabels: {
    enabled: false,
  },

  xaxis: {
    categories: ["총무", "경영지원", "영업", "마케팅", "서비스", "연구소", "생산"],
  },
  legend: {
    position: "top",
    horizontalAlign: "left",
    fontFamily: "Satoshi",
    fontWeight: 500,
    fontSize: "14px",

    markers: {
      radius: 99,
    },
  },
  fill: {
    opacity: 1,
  },
};

interface ChartTwoState {
  series: {
    name: string;
    data: number[];
  }[];
}

const MemberChart: React.FC = () => {
  const [state, setState] = useState<ChartTwoState>({
    series: [
      {
        name: "부장",
        data: [44, 55, 41, 67, 22, 43, 65],
      },
      {
        name: "차장",
        data: [13, 23, 20, 8, 13, 27, 15],
      },
      {
        name: "과장",
        data: [13, 23, 20, 8, 13, 27, 15],
      },
      {
        name: "대리",
        data: [13, 23, 20, 8, 13, 27, 15],
      },
      {
        name: "사원",
        data: [13, 23, 20, 8, 13, 27, 15],
      },
    ],
  });

  const handleReset = () => {
    setState((prevState) => ({
      ...prevState,
    }));
  };
  handleReset;

  return (
    <div className="col-span-12 rounded-sm border border-stroke bg-white p-7.5 shadow-default dark:border-strokedark dark:bg-boxdark xl:col-span-4">
      <div className="mb-4 justify-between gap-4 sm:flex">
        <div>
          <h4 className="text-xl font-semibold text-black dark:text-white">
            부서별 직원 현황
          </h4>
        </div>
      </div>

      <div>
        <div id="chartTwo" className="-mb-9 -ml-5">
          <ReactApexChart
            options={options}
            series={state.series}
            type="bar"
            height={350}
            width={"100%"}
          />
        </div>
      </div>
    </div>
  );
};

export default MemberChart;
