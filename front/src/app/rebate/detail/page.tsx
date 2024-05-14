'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import RebateTable from "./RebateTable";
import { useState } from "react";


export default function RebateDetailTest() {

    const [date, setDate] = useState({ year: "", month: ""});

    const [rebate, setRebate] = useState();
    // "rebateDto": {
    //     "year": "2024",
    //     "month": "6",
    //     "memberName": "고길동",
    //     "memberId": "20006",
    //     "grade": "부장",
    //     "dept": "영업",
    //     "salary": 6666666,
    //     "bonus": 0,
    //     "tax": 399999,
    //     "insurance": 50000,
    //     "totalSalary": 6216667
    // }

    const handleChange = (e) => {
        const { name, value } = e.target;
        console.log(date);
        setDate({ ...date, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        const response = await fetch("http://localhost:8090/api/v1/rebates", {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(date)
        });

        const parsedResponse = await response.json();

        if (response.ok) {
            setRebate(parsedResponse.data.rebateDto);
            alert(date.year + "년 " + date.month + "월 급여정산내역");
        } else {
            alert("정산 생성 실패.");
        }
        
    }

    return (
        <>
        <DefaultLayout>
            <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
                정산 테스트 페이지 /reabte/detail
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
                
            <RebateTable rebate = {rebate}/>
            
        </DefaultLayout>
        </>
    )
}