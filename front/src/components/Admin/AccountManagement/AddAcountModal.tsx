"use client";

import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";

import { useState } from "react";
import api from "@/util/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 1000,
  p: 4,
};

export default function AddAccountModal() {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const queryClient = useQueryClient();

  const getDivisions = async () => {
    const response = await api.get(`/api/v1/divisions`);
    return response.data.data.dtoList;
  };

  const { division }: any = useQuery({
    queryKey: ["divisions"],
    queryFn: getDivisions,
  });

  const getDepartment = async () => {
    const response = await api.get(`/api/v1/departments`);
    return response.data.data.dtoList;
  };

  const { department }: any = useQuery({
    queryKey: ["departments"],
    queryFn: getDepartment,
  });

  const getGrades = async () => {
    const response = await api.get(`/api/v1/grades`);
    return response.data.data.dtoList;
  };

  const { grade }: any = useQuery({
    queryKey: ["grades"],
    queryFn: getGrades,
  });

  const handlePost = async (e: any) => {
    e.preventDefault();

    const data = {
      divisionCode: e.target.divisions.value,
      departmentCode: e.target.departments.value,
      gradeCode: e.target.grades.value,
      username: e.target.username.value,
      password: e.target.password.value,
      memberNumber: e.target.memberNumber.value,
      name: e.target.name.value,
    };

    console.log(data);

    await api.post("/api/v1/members", data).then((res) => {
      if (res.data.isSuccess) {
        alert(res.data.msg);
        handleClose();
      } else {
        alert(res.data.msg);
      }
    });
  };

  return (
    <>
      <button
        className="inline-flex items-center justify-center rounded-md bg-meta-3 px-6 py-3 text-center font-medium text-white hover:bg-opacity-90"
        onClick={handleOpen}
      >
        계정 등록
      </button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          {/* <!-- 계정 등록 --> */}
          <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
            <div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
              <h3 className="font-medium text-black dark:text-white">
                계정 등록
              </h3>
            </div>
            <form onSubmit={handlePost}>
              <div className="flex grid-cols-2 gap-8 p-6.5">
                <div className="w-1/2">
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      소속 <span className="text-meta-1">*</span>
                    </label>

                    <div className="relative z-20 bg-transparent dark:bg-form-input">
                      <select
                        id="divisions"
                        name="divisions"
                        className={`relative z-20 w-full appearance-none rounded border border-stroke bg-transparent px-5 py-3 outline-none transition focus:border-primary active:border-primary dark:border-form-strokedark dark:bg-form-input dark:focus:border-primary `}
                      >
                        {queryClient
                          .getQueryData<any>(["divisions"])
                          ?.map((row: any, index: number) => (
                            <option
                              value={row.code}
                              className="text-body dark:text-bodydark"
                            >
                              {row.name}
                            </option>
                          ))}
                      </select>

                      <span className="absolute right-4 top-1/2 z-30 -translate-y-1/2">
                        <svg
                          className="fill-current"
                          width="24"
                          height="24"
                          viewBox="0 0 24 24"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <g opacity="0.8">
                            <path
                              fillRule="evenodd"
                              clipRule="evenodd"
                              d="M5.29289 8.29289C5.68342 7.90237 6.31658 7.90237 6.70711 8.29289L12 13.5858L17.2929 8.29289C17.6834 7.90237 18.3166 7.90237 18.7071 8.29289C19.0976 8.68342 19.0976 9.31658 18.7071 9.70711L12.7071 15.7071C12.3166 16.0976 11.6834 16.0976 11.2929 15.7071L5.29289 9.70711C4.90237 9.31658 4.90237 8.68342 5.29289 8.29289Z"
                              fill=""
                            ></path>
                          </g>
                        </svg>
                      </span>
                    </div>
                  </div>
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      부서 <span className="text-meta-1">*</span>
                    </label>

                    <div className="relative z-20 bg-transparent dark:bg-form-input">
                      <select
                        id="departments"
                        name="departments"
                        className={`relative z-20 w-full appearance-none rounded border border-stroke bg-transparent px-5 py-3 outline-none transition focus:border-primary active:border-primary dark:border-form-strokedark dark:bg-form-input dark:focus:border-primary `}
                      >
                        {queryClient
                          .getQueryData<any>(["departments"])
                          ?.map((row: any, index: number) => (
                            <option
                              value={row.code}
                              className="text-body dark:text-bodydark"
                            >
                              {row.name}
                            </option>
                          ))}
                      </select>

                      <span className="absolute right-4 top-1/2 z-30 -translate-y-1/2">
                        <svg
                          className="fill-current"
                          width="24"
                          height="24"
                          viewBox="0 0 24 24"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <g opacity="0.8">
                            <path
                              fillRule="evenodd"
                              clipRule="evenodd"
                              d="M5.29289 8.29289C5.68342 7.90237 6.31658 7.90237 6.70711 8.29289L12 13.5858L17.2929 8.29289C17.6834 7.90237 18.3166 7.90237 18.7071 8.29289C19.0976 8.68342 19.0976 9.31658 18.7071 9.70711L12.7071 15.7071C12.3166 16.0976 11.6834 16.0976 11.2929 15.7071L5.29289 9.70711C4.90237 9.31658 4.90237 8.68342 5.29289 8.29289Z"
                              fill=""
                            ></path>
                          </g>
                        </svg>
                      </span>
                    </div>
                  </div>
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      직급 <span className="text-meta-1">*</span>
                    </label>

                    <div className="relative z-20 bg-transparent dark:bg-form-input">
                      <select
                        id="grades"
                        name="grades"
                        className={`relative z-20 w-full appearance-none rounded border border-stroke bg-transparent px-5 py-3 outline-none transition focus:border-primary active:border-primary dark:border-form-strokedark dark:bg-form-input dark:focus:border-primary `}
                      >
                        {queryClient
                          .getQueryData<any>(["grades"])
                          ?.map((row: any, index: number) => (
                            <option
                              value={row.code}
                              className="text-body dark:text-bodydark"
                            >
                              {row.name}
                            </option>
                          ))}
                      </select>

                      <span className="absolute right-4 top-1/2 z-30 -translate-y-1/2">
                        <svg
                          className="fill-current"
                          width="24"
                          height="24"
                          viewBox="0 0 24 24"
                          fill="none"
                          xmlns="http://www.w3.org/2000/svg"
                        >
                          <g opacity="0.8">
                            <path
                              fillRule="evenodd"
                              clipRule="evenodd"
                              d="M5.29289 8.29289C5.68342 7.90237 6.31658 7.90237 6.70711 8.29289L12 13.5858L17.2929 8.29289C17.6834 7.90237 18.3166 7.90237 18.7071 8.29289C19.0976 8.68342 19.0976 9.31658 18.7071 9.70711L12.7071 15.7071C12.3166 16.0976 11.6834 16.0976 11.2929 15.7071L5.29289 9.70711C4.90237 9.31658 4.90237 8.68342 5.29289 8.29289Z"
                              fill=""
                            ></path>
                          </g>
                        </svg>
                      </span>
                    </div>
                  </div>
                </div>
                <div className="w-1/2">
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      아이디 <span className="text-meta-1">*</span>
                    </label>
                    <input
                      type="text"
                      id="username"
                      name="username"
                      placeholder="아이디를 입력해주세요."
                      className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                    />
                  </div>
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      비밀 번호 <span className="text-meta-1">*</span>
                    </label>
                    <input
                      type="password"
                      id="password"
                      name="password"
                      placeholder="비밀번호를 입력해주세요."
                      className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                    />
                  </div>
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      사원 번호 <span className="text-meta-1">*</span>
                    </label>
                    <input
                      type="text"
                      id="memberNumber"
                      name="memberNumber"
                      placeholder="사원 번호를 입력해주세요."
                      className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                    />
                  </div>
                  <div className="mb-4.5">
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                      사원명 <span className="text-meta-1">*</span>
                    </label>
                    <input
                      type="text"
                      id="name"
                      name="name"
                      placeholder="사원명을 입력해주세요."
                      className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                    />
                  </div>
                </div>
              </div>
              <div className="p-4">
                <button
                  type="submit"
                  className="flex w-full justify-center rounded bg-primary p-3 font-medium text-gray hover:bg-opacity-90"
                >
                  계정 등록하기
                </button>
              </div>
            </form>
          </div>
        </Box>
      </Modal>
    </>
  );
}
