'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import MemberTable from "./MemberTable";
import Link from "next/link";
import { useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import RebateLoader from "./RebateLoader";

interface memberInfo {
    id: BigInteger;
}


interface Rebate {
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
}

export default function Rebates() {
    const queryClient = useQueryClient();
    const member = queryClient.getQueryData<memberInfo>(["member"]);
    const [rebates, setRebates] = useState<Rebate[]>([]);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        fetchRebates();
    }, []);

    const fetchRebates = async () => {
        try {
            const response = await fetch('http://localhost:8090/api/v1/rebates');
            if (!response.ok) {
                throw new Error('Failed to fetch rebates');
            }
            const data = await response.json();
            setRebates(data.data.rebates);
        } catch (error) {
            console.error('Error fetching data:', error);
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <DefaultLayout>
            {isLoading ? (
                <RebateLoader/>
            ) : (
                <>
                    <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
                        급여정산 페이지
                    </h4>
                    <Link href="/rebate/detail" className="inline-flex items-center justify-center rounded-md bg-primary px-6 py-2 text-center font-medium text-white hover:bg-opacity-90">
                        테스트페이지
                    </Link>
                    <MemberTable rebates={rebates}/>
                </>
            )}
        </DefaultLayout>
    );
}