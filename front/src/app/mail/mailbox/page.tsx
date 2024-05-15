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
import MailboxAll from "@/components/Mailbox/MailboxAll";


const mailbox = () => {
    const [mailboxsidebarOpen, setMailboxSidebarOpen] = useState(false);
    return (
        <DefaultLayout>
            <Breadcrumb pageName="MailBox" />
            <div className="grid grid-cols-3 gap-9 sm:grid-cols-4">
                <div className="flex flex-col gap-9 col-span-1">
                    {/* <!-- Left Side Start--> */}
                    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                        <div className="grid grid-cols-2 border-b border-stroke px-6.5 py-4 dark:border-strokedark">     
                                <Link
                                    href="#"
                                    className="col-span-1 rounded-sm inline-flex items-center justify-center gap-2.5 bg-primary px-10 py-4 text-center font-medium text-white hover:bg-opacity-90 lg:px-0.5 xl:px-1"
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
                                    className="col-span-1 ml-2 inline-flex items-center justify-center gap-2.5 bg-primary px-10 py-4 text-center font-medium text-white hover:bg-opacity-90 lg:px-0.5 xl:px-1"
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
                        </div>
                        <div className="flex flex-col gap-5.5 p-6.5">
                            {/* <!-- ===== MailboxSidebar Start ===== --> */}
                            <MailboxSidebar sidebarOpen={mailboxsidebarOpen} setSidebarOpen={setMailboxSidebarOpen} />
                            {/* <!-- ===== MailboxSidebar End ===== --> */}
                        </div>
                    </div>
                    {/* <!-- Left Side End--> */}
                </div>

                <div className="flex flex-col gap-9 col-span-3 sm:col-span-3">
                    {/* <!-- Right Side Start--> */}
                    <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
                        <div className="flex flex-col gap-5.5 p-6.5">
                            <div>
                                <MailboxAll />
                            </div>
                        </div>
                    </div>
                    {/* <!-- Right Side End--> */}
                </div>
            </div>

        </DefaultLayout>
    );
};

export default mailbox;
