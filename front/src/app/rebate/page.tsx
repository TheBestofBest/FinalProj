'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import TableFour from "@/components/Tables/TableFour";
import TableOne from "@/components/Tables/TableOne";
import TableThree from "@/components/Tables/TableThree";
import TableTwo from "@/components/Tables/TableTwo";
import MemberTable from "./MemberTable";


export default function Rebate() {

    return (
        <>
        <DefaultLayout>
            정산 페이지
            <MemberTable></MemberTable>
        </DefaultLayout>
        </>
    )
}