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
        } catch (error) {
            console.error('Error fetching data:', error);
            alert(error.message);
            router.push("/");
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
                    <MemberTable rebates={rebates}/>
                </>
            )}
        </DefaultLayout>
    );
}