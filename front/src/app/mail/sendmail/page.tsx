'use client'

import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import React, { useState } from 'react';
import TinymceEditor from "@/components/TinymceEditor/TinymceEditor";
import dotenv from 'dotenv';
import { useRouter } from "next/router";
import { useQueryClient } from "@tanstack/react-query";
dotenv.config();

const SendMail = () => {
    const queryClient = useQueryClient();
    const Memberdata: any = queryClient.getQueryData(["member"]);
    const [sendMail, setSendMail] = useState({
        senderEmail: "admin@email.com",
        receiverEmail: "",
        referenceEmail: "",
        title: "",
        content: "",
        sendDate: "",
        receiveDate: ""
    });

    const handleSubmit = async (e) => {
        e.preventDefault();

        // 유효성 검사: 수신자 이메일 주소가 유효한지 확인
        if (!isValidEmail(sendMail.receiverEmail)) {
            alert("유효한 이메일 주소를 입력하세요.");
            return;
        }

        try {
            const response = await fetch("http://localhost:8090/api/v1/mails", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(sendMail)
            });

            if (response.ok) {
                alert("메일 발송 완료");
            } else {
                alert("메일 발송 실패");
            }
        } catch (error) {
            console.error("메일 발송 중 에러:", error);
            alert("메일 발송 중 에러가 발생했습니다.");
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSendMail({ ...sendMail, [name]: value });
    }

    const handleEditorChange = (content) => {
        setSendMail({ ...sendMail, content: content });
    };

    // 이메일 주소의 유효성을 검사하는 함수
    const isValidEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

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
                                    받는 사람 <span className="text-meta-1">*</span>
                                </label>
                                <input
                                    onChange={handleChange}
                                    type="email"
                                    name="receiverEmail"
                                    value={sendMail.receiverEmail}
                                    placeholder="받는 사람을 입력하세요"
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
                                    name="referenceEmail"
                                    value={sendMail.referenceEmail}
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
                                    value={sendMail.title}
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
                                    value={sendMail.content}
                                    placeholder="내용을 입력하세요"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                             {/* <!-- ===== Tinymce Start ===== --> */}
                             <TinymceEditor />
                            {/* <!-- ===== Tinymce End ===== --> */}
                            <div className="flex flex-col gap-5.5 p-6.5">
                                <div>
                                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                        첨부 파일
                                    </label>
                                    <input
                                        type="file"
                                        className="w-full cursor-pointer rounded-lg border-[1.5px] border-stroke bg-transparent outline-none transition file:mr-5 file:border-collapse file:cursor-pointer file:border-0 file:border-r file:border-solid file:border-stroke file:bg-whiter file:px-5 file:py-3 file:hover:bg-primary file:hover:bg-opacity-10 focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:file:border-form-strokedark dark:file:bg-white/30 dark:file:text-white dark:focus:border-primary"
                                    />
                                </div>
                            </div>

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