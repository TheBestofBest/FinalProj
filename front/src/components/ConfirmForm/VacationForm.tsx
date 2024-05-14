"use client";
import { ConfirmFormType } from "@/types/Confirm/ConfirmTypes";
import { MemberType } from "@/types/Member/MemberTypes";
import api from "@/util/api";
import React, { ChangeEvent, useEffect, useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { useQuery, useQueryClient } from "@tanstack/react-query";
const VacationForm = () => {
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  const [confirm, setConfirm] = useState<ConfirmFormType>();
  const [members, setMembers] = useState<MemberType[]>([]);
  const [keyword, setKeyword] = useState("");
  const [confirmMembers, setConfirmMembers] = useState<MemberType[]>([]);

  // 오늘 날짜
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, "0");
  const day = String(currentDate.getDate()).padStart(2, "0");
  const formattedDate = `${month}/${day}/${year}`;

  // 승인자 검색 창 입력 시 키워드 변경
  const changeSearchWordHandler = (event: ChangeEvent<HTMLInputElement>) => {
    event.preventDefault();

    setKeyword(event.target.value);
    console.log(event.target.value);
  };
  // 검색 누르면 해당 키워드로 검색 후 리스트 출력
  const searchMembers = async () => {
    const response = await api.get(`api/v1/members/search?keyword=${keyword}`);
    setMembers(response.data.data.memberDTOs);
    console.log(members);
  };

  // 검색한 멤버 누르면 승인자 리스트에 추가
  const addConfirmMembers = (member: MemberType) => {
    setConfirmMembers((prevConfirmMembers) => [...prevConfirmMembers, member]);
  };
  // 승인자 상태 확인용
  useEffect(() => {
    console.log(confirmMembers);
  }, [confirmMembers]);

  const queryClient = useQueryClient();
  const member = queryClient.getQueryData<MemberType>(["member"]);

  // 결재 신청 메서드 컴포넌트에서 해결
  //   const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
  //     e.preventDefault();

  //     try {
  //         const formData = new FormData(); // FormData 객체 생성

  //         // 폼 데이터에 필드 추가

  //         await api.post("/confirms", {
  //             subject: ,
  //             description: description ,
  //             formData: formData,
  //             formType: confirmFormType,
  //             confirmRequestMember: member,
  //             confirmMembers: : confirmMembers,
  //         });
  //         formData.append("subject", article.subject);
  //         formData.append("content", article.content);
  //         if (image) {
  //             formData.append("image", image);
  //         }
  //         console.log("Gongcha Article created successfully!");
  //         await fetch("http://localhost:8090/api/v1/image-data/articles", {
  //             method: "POST",
  //             body: formData,
  //         });
  //         router.push("/gongcha/articles");

  //         // 추가적인 로직이 필요한 경우 여기에 작성
  //     } catch (error) {
  //         console.error("An error occurred while creating the Gongcha article:", error);
  //         // 에러 처리 로직을 추가할 수 있습니다. 예를 들어, 사용자에게 오류 메시지를 표시하거나 다시 시도할 수 있도록 유도할 수 있습니다.
  //     }
  // };

  // const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
  //     const { name, value } = e.target;
  //     setArticle({ ...article, [name]: value });
  //     console.log({ ...article, [name]: value });
  // };

  return (
    // onSubmit={handleSubmit} form에 넣기
    <form>
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
          defaultValue={member?.name.toString()}
          disabled
        />
        <label className="text-gray-900 mb-2 block text-base font-bold dark:text-white">
          결재 신청일
        </label>
        <input
          type="text"
          className="text-gray-900 border-gray-300 bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 text-s mb-2 block w-full cursor-not-allowed rounded-lg border p-2 focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          defaultValue={formattedDate}
          disabled
        />
        <label className="text-gray-900 mb-2 block text-base font-bold dark:text-white">
          결재 설명
        </label>
        <input
          type="text"
          className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 text-s block w-full rounded-lg border p-2.5 focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          placeholder={"결재에 대한 간략한 설명을 적어주세요"}
        />
        <label className="text-gray-900 mb-2 mt-2 block text-base font-bold dark:text-white">
          휴가 날짜 선택
        </label>
        <div className="flex items-center">
          <DatePicker
            selected={startDate}
            onChange={(date) => setStartDate(date)}
            selectsStart
            startDate={startDate}
            endDate={endDate}
            className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
            placeholderText="Select date start"
          />
          <span className="text-gray-500 mx-4">to</span>
          <DatePicker
            selected={endDate}
            onChange={(date) => setEndDate(date)}
            selectsEnd
            startDate={startDate}
            endDate={endDate}
            minDate={startDate}
            className="bg-gray-50 border-gray-300 text-gray-900 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
            placeholderText="Select date end"
          />
        </div>
        <label
          htmlFor="message"
          className="text-gray-900 mb-2 block text-base font-bold dark:text-white"
        >
          상세내용
        </label>
        <textarea
          id="message"
          rows={4}
          className="text-gray-900 bg-gray-50 border-gray-300 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-2.5 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
          placeholder="상세 내용을 작성해주세요"
          defaultValue={""}
        />
        {/* 멤버 선택 넣기 */}
        <label
          htmlFor="search"
          className="text-gray-900 mb-2 mb-2 mt-3 block text-base font-bold dark:text-white"
        >
          승인자 검색
        </label>
        <div className="mx-auto  max-w-md">
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
              onChange={changeSearchWordHandler}
              required
            />
            <button
              className="absolute bottom-2.5 end-2.5 rounded-lg bg-blue-700 px-4 py-2 text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
              onClick={(event) => {
                event.preventDefault(); // 기본 동작 방지
                searchMembers(); // searchMembers 함수 호출
              }}
            >
              검색
            </button>
          </div>
        </div>
        <div className="relative mt-2 overflow-x-auto border border-slate-300">
          <table className="text-gray-500 dark:text-gray-400 w-full text-left text-sm rtl:text-right">
            <thead className="text-gray-700 bg-gray-50 dark:bg-gray-700 dark:text-gray-400 border-b border-slate-300 text-xs uppercase">
              <tr>
                <th scope="col" className="px-6 py-3">
                  이름
                </th>
                <th scope="col" className="px-6 py-3">
                  직급
                </th>
                <th scope="col" className="px-6 py-3">
                  부서
                </th>
              </tr>
            </thead>
            <tbody>
              {members?.map((member) => (
                <tr
                  key={member.id}
                  className="dark:bg-gray-800 dark:border-gray-200 border-b bg-white hover:cursor-pointer hover:bg-blue-200"
                  onClick={() => addConfirmMembers(member)}
                >
                  <th
                    scope="row"
                    className="text-gray-900 whitespace-nowrap px-6 py-4 font-medium dark:text-white"
                  >
                    {member.name}
                  </th>
                  <td className="px-6 py-4">{member.grade.name}</td>
                  <td className="px-6 py-4">{member.department.name}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        <div className="mt-3 flex justify-end">
          <button
            // onClick={() => handleForm("")}
            type="button"
            className="mb-2 me-2  rounded-lg bg-gradient-to-r from-teal-400 via-teal-500 to-teal-600 px-5 py-2.5 text-center text-sm font-medium text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-teal-300 dark:focus:ring-teal-800"
          >
            등록하기
          </button>
        </div>
      </div>
    </form>
  );
};

export default VacationForm;
