'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Stats from "./stats";


export default function StatsMain() {

    return (
        <>
        <DefaultLayout>
            <h1 className="mb-6 text-xl font-semibold text-black dark:text-white">
                통계
            </h1>
            <Stats/>
        </DefaultLayout>
        </>
    )
}