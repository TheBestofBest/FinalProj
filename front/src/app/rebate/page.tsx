'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import MemberTable from "./MemberTable";


export default function Rebate() {

    return (
        <>
        <DefaultLayout>
            <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
                정산 페이지
            </h4>
            
            <MemberTable></MemberTable>
        </DefaultLayout>
        </>
    )
}