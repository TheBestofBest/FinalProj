"use client";
import React from "react";

import MainTableOne from "./MainTableOne";
import MainTableTwo from "./MainTableTwo";
import MainTableThree from "./MainTableThree";
import MainTableFour from "./MainTableFour";
import Message from "./Message";

const Main: React.FC = () => {
  return (
    <>
        메인페이지임다
      <div className="grid grid-cols-1 gap-4 md:grid-cols-2 md:gap-6 xl:grid-cols-4 2xl:gap-7.5">
        
      </div>

      <div className="mt-4 grid grid-cols-12 gap-4 md:mt-6 md:gap-6 2xl:mt-7.5 2xl:gap-7.5">
        <div className="col-span-12 xl:col-span-6">
          <MainTableOne/>
        </div>
        <div className="col-span-12 xl:col-span-6">
            <MainTableThree/>
        </div>
        <div className="col-span-12 xl:col-span-4">
            <MainTableTwo/>
        </div>
        <div className="col-span-12 xl:col-span-4">
          <MainTableFour/>
        </div>
        <Message/>
      </div>
      
    </>
  );
};

export default Main;
