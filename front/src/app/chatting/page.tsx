"use client";

import { Metadata } from "next";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import { useEffect, useRef, useState } from "react";
import Id from "./[id]/page";

const Chatting = () => {
  return (
    <main>
      <main className=" h-115 flex-1 overflow-y-auto p-6 ">
        <div className="flex items-center justify-center">
          <div className="mt-12">
            <div className="mb-6">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                className="ml-26 h-10 w-10"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="m9.75 9.75 4.5 4.5m0-4.5-4.5 4.5M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
                />
              </svg>
            </div>
            <span className="mt-20 text-xl">선택된 채팅방이 없습니다.</span>
          </div>
        </div>
      </main>
      <footer className="bg-gray-300 px-6 py-4 "></footer>
    </main>
  );
};

export default Chatting;
