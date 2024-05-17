'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import MemberTable from "./MemberTable";
import Link from "next/link";
import { useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import RebateLoader from "./RebateLoader";
import { useRouter } from "next/navigation";
import RebateDropDown from "./RebateDropDown";

interface memberInfo {
    id: BigInteger;
}


interface Rebate {
    rebateId : number;
    year: string;
    month: string;
    memberName: string;
    memberId: string;
    grade: string;
    dept: string;
    workDate: number;
    workedDate: number;
    salary: number;
    bonus: number;
    tax: number;
    insurance: number;
    totalSalary: number;
    isSaved: boolean;
}

export default function Rebates() {
    const queryClient = useQueryClient();
    const member = queryClient.getQueryData<memberInfo>(["member"]);
    const [rebates, setRebates] = useState<Rebate[]>([]);
    const [totalSum, setTotalSum] = useState<number>(0);
    const [isLoading, setIsLoading] = useState(true);
    const router = useRouter();

    useEffect(() => {
        fetchRebates();
    }, []);

    const fetchRebates = async () => {
    
        try {
            const response = await fetch('http://localhost:8090/api/v1/rebates', {
                credentials: "include",
            });
    
            const data = await response.json();
    
            if (!response.ok || data.rsCode.code.startsWith("F")) {
                throw new Error(data.msg);
            }
            setRebates(data.data.rebates);
            setTotalSum(data.data.totalSum);
        } catch (error) {
            console.error('Error fetching data:', error);
            alert(error.message);
            router.push("/");
        } finally {
            setIsLoading(false);
        }
    };

    const [date, setDate] = useState({ year: "", month: "", dept:"", userInfo: ""});

    const handleChange = (e) => {
        const { name, value } = e.target;
        console.log(date);
        setDate({ ...date, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
    
        const response = await fetch("http://localhost:8090/api/v1/rebates/search", {
            method: 'POST',
            credentials: "include",
            headers: {
                'Content-Type': 'application/json' 
            },
            body: JSON.stringify(date)
        });

        const parsedResponse = await response.json();

        if (response.ok && parsedResponse.data.totalSum !== 0) {
            setRebates(parsedResponse.data.rebates);
            setTotalSum(parsedResponse.data.totalSum);
            alert(date.year + "년 " + date.month + "월 급여정산내역");
        } else if (parsedResponse.data.totalSum === 0) {
            alert("정산 내역이 존재하지 않습니다.");
        } else {
            alert("정산 생성 실패.");
        }
        
    }


    return (
        <DefaultLayout>
            {isLoading ? (
                <RebateLoader/>
            ) : (
                <>
                <form onSubmit={handleSubmit}>
                    <div className="flex justify-between py-4">
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
                            <div>
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                부서명 입력
                                </label>
                                <input
                                type="text"
                                name="dept"
                                onChange={handleChange}
                                placeholder="ex)영업"
                                className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            <div>
                                <label className="mb-3 block text-sm font-medium text-black dark:text-white">
                                직원 이름 입력
                                </label>
                                <input
                                type="text"
                                name="userInfo"
                                onChange={handleChange}
                                placeholder="ex)홍길동"
                                className="w-full rounded-lg border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                                />
                            </div>
                            <div>
                                <button
                                type="submit"
                                className="whitespace-nowrap rounded bg-primary px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
                                >검색</button>
                            </div>    
                    </div>
                    </form>
                    <MemberTable rebates={rebates} totalSum={totalSum}/>
                    </>
                )}
        </DefaultLayout>
    );
}