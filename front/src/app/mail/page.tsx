"use client";

import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import React, { useState } from 'react';
import dotenv from 'dotenv'
import TinymceRead from "@/components/TinymceEditor/TinymceRead";
import { useRouter } from "next/router";
import { useQueryClient } from "@tanstack/react-query";
dotenv.config();

const SendMail = () => {
    const router = useRouter();
    const queryClient = useQueryClient();
    const Memberdata: any = queryClient.getQueryData(["member"]);
    const [sendMail, setSendMail] = useState({
        senderEmail: "",
        receiverEmail: "",
        referenceEmail: "",
        title: "",
        content: "",
        sendDate: "",
        receiveDate: ""
    });
    const handleSubmit = async (e) => {
        e.preventDefault();

        const response = await fetch("http://localhost:8090/api/v1/mails", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(sendMail)
        })

        if (response.ok) {
            alert("메일 발송 완료")
            router.push("/project")

        } else {
            alert("메일 발송 실패")
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSendMail({ ...sendMail, [name]: value })
    }
    return (
        <DefaultLayout>
            <Breadcrumb pageName="SendMail" />
            <div className="flex flex-col gap-9">
                {/* <!-- Contact Form --> */}
                <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                    <div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
                        <h3 className="font-medium text-black dark:text-white">
                            메일 쓰기
                        </h3>
                    </div>
                    <form action="#">
                        <div className="p-6.5">
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    받는 사람 <span className="text-meta-1">*</span>
                                </label>
                                <input
                                    onChange={handleChange}
                                    type="email"
                                    name="receiverEmail"
                                    placeholder="Enter your email address"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            <div className="mb-4.5">
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    참조
                                </label>
                                <input
                                    onChange={handleChange}
                                    type="text"
                                    name="referenceEmail"
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
                                    id="title"
                                    placeholder="Select subject"
                                    className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            {/* <!-- ===== Tinymce읽기전용 Start ===== --> */}
                            <TinymceRead />
                            {/* <!-- ===== Tinymce읽기전용 End ===== --> */}
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
                                메일 보내기
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        </DefaultLayout>
    );
};

export default SendMail;
