'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import RebateTable from "./RebateTable";
import { useState } from "react";
import { useRouter } from "next/router";
import { useParams } from "next/navigation";


export default function RebateDetailTest() {

    const params = useParams();

    const [rebate, setRebate] = useState({ year: "", month: ""});

    const handleChange = (e) => {
        const { name, value } = e.target;
        console.log(rebate);
        setRebate({ ...rebate, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        const response = await fetch("http://localhost:8090/api/v1/rebates", {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(rebate)
        })
    
        if (response.ok) {
            alert("정산 생성 성공.")
        } else {
            alert("정산 생성 실패.")
        }
    }

    return (
        <>
        <DefaultLayout>
            <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
                정산 테스트 페이지 /reabte/member_id
            </h4>

            <div className="flex">
                <form onSubmit={handleSubmit}>
                <div>
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                    연도 입력
                    </label>
                    <input
                    type="text"
                    name="year"
                    placeholder="ex)2024"
                    onChange={handleChange}
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                    />
                </div>
                <div>
                    <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                    월 입력
                    </label>
                    <input
                    type="text"
                    name="month"
                    onChange={handleChange}
                    placeholder="ex)5"
                    className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                    />
                </div>
                <button
                type="submit"
                className="inline-flex items-center justify-center rounded-md bg-primary px-6 py-2 text-center font-medium text-white hover:bg-opacity-90"
                >정산 테스트</button>
                </form>
            </div>
                
            <RebateTable/>
            
        </DefaultLayout>
        </>
    )
}