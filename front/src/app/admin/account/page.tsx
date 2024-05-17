"use client";

import AddAccountModal from "@/components/Admin/AccountManagement/AddAcountModal";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@mui/material";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import Image from "next/image";

const AdminAccount = () => {
  const queryClient = useQueryClient();

  const getAllMember = async () => {
    const response = await api.get("/api/v1/members");
    console.log(response);
    return response.data.data;
  };

  const searchMembers = async (e: any) => {
    e.preventDefault();
    await api
      .get(`api/v1/members/search?keyword=${e.target.keyword.value}`)
      .then((res) => {
        queryClient.setQueryData(["allMember"], res.data.data);
      });
  };

  const searchReset = async () => {
    await api.get(`api/v1/members/search?keyword=`).then((res) => {
      queryClient.setQueryData(["allMember"], res.data.data);
    });
  };

  const { data } = useQuery({
    queryKey: ["allMember"],
    queryFn: getAllMember,
  });

  return (
    <DefaultLayout>
      <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
        <h2 className="text-title-md2 font-bold text-black dark:text-white">
          계정 관리
        </h2>
        <nav>
          <ol className="flex items-center gap-2">
            <li>
              <a className="font-medium" href="index.html">
                관리자 메뉴 /
              </a>
            </li>
            <li className="font-medium text-primary">계정 관리</li>
          </ol>
        </nav>
      </div>
      <div className="flex justify-between">
        <AddAccountModal />
        <form onSubmit={searchMembers} className="flex">
          <button
            type="button"
            onClick={searchReset}
            className="mr-2 h-11 w-11"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 -960 960 960"
              fill="#5f6368"
            >
              <path d="M440-122q-121-15-200.5-105.5T160-440q0-66 26-126.5T260-672l57 57q-38 34-57.5 79T240-440q0 88 56 155.5T440-202v80Zm80 0v-80q87-16 143.5-83T720-440q0-100-70-170t-170-70h-3l44 44-56 56-140-140 140-140 56 56-44 44h3q134 0 227 93t93 227q0 121-79.5 211.5T520-122Z" />
            </svg>
          </button>
          <input
            type="text"
            name="keyword"
            placeholder="사원명 검색"
            className="rounded-lg border border-slate-300 bg-white px-5 py-3 text-black shadow-default transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border dark:border-form-strokedark dark:border-white dark:bg-boxdark dark:bg-form-input dark:text-white dark:focus:border-primary"
          />
          <button
            className="hover:bg-primary-600 focus:bg-primary-600 active:bg-primary-700 ml-2 h-full whitespace-nowrap rounded-lg bg-primary px-5 font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
            type="submit"
          >
            검색
          </button>
        </form>
      </div>
      <div className="mt-4 grid grid-cols-4 gap-6">
        {data?.memberDTOs.map((row: any, index: number) => (
          <div className="grid h-36 content-between rounded-lg border border-slate-300 bg-white shadow-default dark:border dark:border-white dark:bg-boxdark">
            <div className="flex justify-between">
              <div className="p-5">
                <div className="flex">
                  <div className="text-lg font-bold text-black dark:text-white">
                    {row.name}
                  </div>
                  <div className="ml-2 rounded-lg p-0.5 text-end text-sm underline underline-offset-4">
                    {row.grade.name}
                  </div>
                </div>
                <div className="mt-2 text-sm">
                  {row?.division.code === 0 ? null : `${row?.division.name}`}
                  {row?.department.code === 0
                    ? null
                    : row?.division.code !== 0
                      ? ` / ${row?.department.name}`
                      : `${row?.department.name}`}
                </div>
              </div>
              <div className="p-4">
                <Image
                  width={50}
                  height={50}
                  src={"/images/user/user-01.png"}
                  style={{
                    width: "auto",
                    height: "auto",
                  }}
                  alt="User"
                />
              </div>
            </div>
            <div className="grid h-12 grid-cols-2 border-t border-slate-300">
              <div className="place-content-center border-r border-slate-300">
                <div className="flex justify-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    height="24px"
                    viewBox="0 -960 960 960"
                    width="24px"
                    fill="#5f6368"
                  >
                    <path d="M160-160q-33 0-56.5-23.5T80-240v-480q0-33 23.5-56.5T160-800h640q33 0 56.5 23.5T880-720v480q0 33-23.5 56.5T800-160H160Zm320-280L160-640v400h640v-400L480-440Zm0-80 320-200H160l320 200ZM160-640v-80 480-400Z" />
                  </svg>
                  <div className="ml-1">Email</div>
                </div>
              </div>
              <div className="place-content-center">
                <div className="flex justify-center">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    height="24px"
                    viewBox="0 -960 960 960"
                    width="24px"
                    fill="#5f6368"
                  >
                    <path d="M798-120q-125 0-247-54.5T329-329Q229-429 174.5-551T120-798q0-18 12-30t30-12h162q14 0 25 9.5t13 22.5l26 140q2 16-1 27t-11 19l-97 98q20 37 47.5 71.5T387-386q31 31 65 57.5t72 48.5l94-94q9-9 23.5-13.5T670-390l138 28q14 4 23 14.5t9 23.5v162q0 18-12 30t-30 12ZM241-600l66-66-17-94h-89q5 41 14 81t26 79Zm358 358q39 17 79.5 27t81.5 13v-88l-94-19-67 67ZM241-600Zm358 358Z" />
                  </svg>
                  <div className="ml-1">Call</div>
                </div>
              </div>
            </div>
          </div>
        ))}
      </div>
    </DefaultLayout>
  );
};

export default AdminAccount;
