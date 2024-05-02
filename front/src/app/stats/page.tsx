'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Stats from "./stats";


export default function StatsMain() {

    return (
        <>
        <DefaultLayout>
            통계 페이지
            <Stats/>
        </DefaultLayout>
        </>
    )
}