'use client'

import { Metadata } from "next";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import { useEffect, useRef, useState } from "react";
import Id from "./[id]/page";






const Chatting = () => {


  return (
    <main>
      <main className=" flex-1 h-115 p-6 overflow-y-auto ">
        <div className="flex justify-center items-center">
          <div className="mt-12">
            <div className="mb-6">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="ml-26 w-10 h-10">
                <path stroke-linecap="round" stroke-linejoin="round" d="m9.75 9.75 4.5 4.5m0-4.5-4.5 4.5M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
              </svg>
            </div>
            <span className="mt-20 text-xl">선택된 채팅방이 없습니다.</span>
          </div>
        </div>
      </main>
      <footer className="bg-gray-300 py-4 px-6 ">
      </footer>
    </main>
  );
};

export default Chatting;
