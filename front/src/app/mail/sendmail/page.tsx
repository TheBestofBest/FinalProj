'use client'

import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import React, { ChangeEvent, useRef, useState } from 'react';
import TinymceEditor from "@/components/TinymceEditor/TinymceEditor";
import dotenv from 'dotenv';
import { useParams, useRouter } from "next/navigation";
import api from "@/util/api";
import { MemberType } from "@/types/Member/MemberTypes";
import { MailTypes } from "@/types/Mail/MailTypes";
dotenv.config();

const SendMail = () => {
    const router = useRouter();
    const [sendMail, setSendMail] = useState<MailTypes>({
        id: 0,
        title: "",
        content: "",
        sendDate: new Date(),
        receiveDate: new Date(),
        sender: {
            id: 0, // id
            department: {
                code: 0,
                name: "",
            }, // 부서
            grade: {
                code: 0,
                name: "",
            }, // 직급
            username: "", // 로그인 아이디
            password: "", // 비밀번호
            email: "", // 이메일
            memberNumber: 0, // 사원번호
            name: "", // 사원명
            assignedTask: "", // 담당 업무
            workStatus: "", // 근무 상태 ( 온라인, 오프라인, 부재중 )
            extensionNumber: "", // 내선 전화 번호
            phoneNumber: "", // 개인 연락처
            statusMessage: "",
        },
        receiver: {
            id: 0, // id
            department: {
                code: 0,
                name: "",
            }, // 부서
            grade: {
                code: 0,
                name: "",
            }, // 직급
            username: "", // 로그인 아이디
            password: "", // 비밀번호
            email: "", // 이메일
            memberNumber: 0, // 사원번호
            name: "", // 사원명
            assignedTask: "", // 담당 업무
            workStatus: "", // 근무 상태 ( 온라인, 오프라인, 부재중 )
            extensionNumber: "", // 내선 전화 번호
            phoneNumber: "", // 개인 연락처
            statusMessage: "",
        },
        reference: {
            id: 0, // id
            department: {
                code: 0,
                name: "",
            }, // 부서
            grade: {
                code: 0,
                name: "",
            }, // 직급
            username: "", // 로그인 아이디
            password: "", // 비밀번호
            email: "", // 이메일
            memberNumber: 0, // 사원번호
            name: "", // 사원명
            assignedTask: "", // 담당 업무
            workStatus: "", // 근무 상태 ( 온라인, 오프라인, 부재중 )
            extensionNumber: "", // 내선 전화 번호
            phoneNumber: "", // 개인 연락처
            statusMessage: "",
        },
        receiver: [], // 빈 배열로 초기화
        reference: null, // 초기값 null로 설정
    });
    const params = useParams();
    const [keyword, setKeyword] = useState("");
    const [members, setMembers] = useState<MemberType[]>([]);
    const changeSearchWordHandler = (event: ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();

        setKeyword(event.target.value);
        console.log(event.target.value);
    };
    const searchMembers = async () => {
        if (keyword === "") {
            return;
        }
        const response = await api.get(`api/v1/members/search?keyword=${keyword}`);
        setMembers(response.data.data.memberDTOs);
        console.log(members);
    };
    const handleChange = (
        e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
      ) => {
        const { name, value } = e.target;
        setSendMail({ ...sendMail, [name]: value });
        console.log({ ...sendMail, [name]: value });
      };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        // 보낼 이메일 데이터 구성
        const sendData = {
            title: sendMail.title,
            content: sendMail.content,
            sendDate: new Date(),
            receiveDate: new Date(),
            sender: {
                email: sendMail.sender.email,
            },
            receiver: {
                email: sendMail.receiver.email,
            },
            reference: sendMail.reference ? {
                email: sendMail.reference.email,
            } : null
        };

        try {

            const response = await api.post("/api/v1/mails", sendData);
            console.log("메일 발송 성공", response.data);
            alert("메일 발송 완료")
            router.push(`/mail/mailbox`);
        } catch (error) {
            console.error("메일 발송 실패", error);
            alert("메일 발송 실패")
        }
    };


    return (
        <DefaultLayout>
            <Breadcrumb pageName="SendMail" />
            <div className="flex flex-col gap-9">
                <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                    <div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
                        <h3 className="font-medium text-black dark:text-white">
                            메일 쓰기
                        </h3>
                    </div>
                    <form onSubmit={handleSubmit} action="#" method="POST">
                        <div className="p-6.5">
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    보내는 사람 <span className="text-meta-1">*</span>
                                </label>
                                <input
                                    defaultValue="admin@email.com"
                                    type="email"
                                    name="sender"
                                    placeholder="받는 사람을 입력하세요"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            <div className="px-6 ">
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
                                        placeholder="Type the name..."
                                        onChange={changeSearchWordHandler}
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
                            <div className="max-h-48 px-6 pb-2 overflow-y-auto ">
                                <div className="relative mt-2 overflow-x-auto border border-slate-300 px-1">
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
                                                <th scope="col" className="px-6 py-3">
                                                    Email
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {members?.map((member) => (
                                                <tr
                                                    key={member.id}
                                                    className="dark:bg-gray-800 dark:border-gray-200 border-b bg-white"
                                                >
                                                    <th
                                                        scope="row"
                                                        className="text-gray-900 whitespace-nowrap px-6 py-3 font-medium dark:text-white"
                                                    >
                                                        {member.name}
                                                    </th>
                                                    <td className="px-6 py-3">{member.grade.name}</td>
                                                    <td className="px-6 py-3">{member.department.name}</td>
                                                    <td className="px-6 py-3">{member.email}</td>
                                                </tr>
                                            ))}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    받는사람
                                </label>
                                <input
                                    onChange={handleChange}
                                    type="email"
                                    name="receiver"
                                    placeholder="받는 사람 메일을 입력하세요"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>    
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    참조
                                </label>
                                <input
                                    onChange={handleChange}
                                    type="email"
                                    name="reference"
                                    placeholder="Carbon Copy"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    제목
                                </label>
                                <input
                                    onChange={handleChange}
                                    type="text"
                                    name="title"
                                    placeholder="제목을 입력하세요"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    내용
                                </label>
                                <textarea
                                    onChange={handleChange}
                                    name="content"
                                    placeholder="내용을 입력하세요"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            {/* <!-- ===== Tinymce Start ===== --> */}
                            {/* <TinymceEditor /> */}
                            {/* <!-- ===== Tinymce End ===== --> */}
                            <button className="flex w-full justify-center rounded bg-primary p-3 font-medium text-gray hover:bg-opacity-90">
                                메일 발송
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </DefaultLayout>
    );
};

export default SendMail;
