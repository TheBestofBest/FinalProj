"use client";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import React, { useState } from 'react';
import Link from "next/link";
import MailboxSidebar from "@/components/MailboxSidebar";
import { DocumentIcon,
    DocumentTextIcon,
    EnvelopeIcon,
    EnvelopeOpenIcon,
    TrashIcon,
    PaperAirplaneIcon
    
} from '@heroicons/react/24/solid'


const mailbox = () => {
    const [mailboxsidebarOpen, setMailboxSidebarOpen] = useState(false);
    return (
        <DefaultLayout>
            <Breadcrumb pageName="MailBox" />
            <div className="grid grid-cols-1 gap-9 sm:grid-cols-3">
                <div className="flex flex-col gap-9 col-span-1">
                    {/* <!-- Menu Fields --> */}
                    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                        <div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
                            <h3 className="font-medium text-black dark:text-white">
                                <Link
                                    href="#"
                                    className="rounded-sm inline-flex items-center justify-center gap-2.5 bg-primary px-10 py-4 text-center font-medium text-white hover:bg-opacity-90 lg:px-0.5 xl:px-1"
                                >
                                    <span>
                                        <EnvelopeIcon
                                            className="fill-current mb-1.5"
                                            width="20"
                                            height="20"
                                            viewBox="0 0 20 20"
                                            fill="none"
                                            xmlns="http://www.w3.org/2000/svg"
                                            style={{ overflow: 'visible' }}
                                        >
                                        </EnvelopeIcon>
                                    </span>
                                    메일 보내기 
                                </Link>
                                <Link
                                    href="#"
                                    className="inline-flex items-center justify-center gap-2.5 bg-primary px-10 py-4 ml-2 text-center font-medium text-white hover:bg-opacity-90 lg:px-0.5 xl:px-1"
                                >
                                    <span>
                                        <PaperAirplaneIcon
                                            className="fill-current mb-1.5"
                                            width="20"
                                            height="20"
                                            viewBox="0 0 20 20"
                                            fill="none"
                                            xmlns="http://www.w3.org/2000/svg"
                                            style={{ overflow: 'visible' }}
                                        >
                                        </PaperAirplaneIcon>
                                    </span>
                                    내게 쓰기
                                </Link>
                            </h3>
                        </div>
                        <div className="flex flex-col gap-5.5 p-6.5">
                            {/* <!-- ===== MailboxSidebar Start ===== --> */}
                            <MailboxSidebar sidebarOpen={mailboxsidebarOpen} setSidebarOpen={setMailboxSidebarOpen} />
                            {/* <!-- ===== MailboxSidebar End ===== --> */}
                        </div>
                    </div>
                </div>

                <div className="flex flex-col gap-9 col-span-3 sm:col-span-2">
                    {/* <!-- List Fields --> */}
                    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                        <div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
                            <h3 className="font-medium text-black dark:text-white">
                                전체 메일함
                            </h3>
                        </div>
                        <div className="flex flex-col gap-5.5 p-6.5">
                            <div>
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    리스트 예정
                                </label>
                                {/* 내용 */}
                            </div>

                            <div>
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                    목록
                                </label>
                                {/* 내용 */}
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </DefaultLayout>
    );
};

export default mailbox;
