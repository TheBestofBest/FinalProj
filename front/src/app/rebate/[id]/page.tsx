'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import RebateNumber from "./RebateNumber";
import Loader from "@/components/common/Loader";


export default function RebateDetail() {

    const params = useParams();

    const [isLoading, setIsLoading] = useState(true); // 로딩 상태

    const router = useRouter();

    const [rebate, setRebate] = useState([]);
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
    useEffect(() => {
        fetchRebate()
    }, [])
    
    const fetchRebate = async () => {
        try {
            const response = await fetch(`http://localhost:8090/api/v1/rebates/${params.id}`,{
              method: 'GET',
              credentials: "include",
            })
            const data = await response.json();
    
            if (!response.ok || data.rsCode.code.startsWith("F")) {
                throw new Error(data.msg);
            }
            setRebate(data.data.rebateDto);
        } catch (error) {
            console.error('Error fetching data:', error);
            alert(error.message);
            router.push("/");
        } finally {
            setIsLoading(false); // 데이터 로딩이 완료되면 로딩 상태를 false로 설정
        }
    }


    return (
        <>
        <DefaultLayout>
            <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
                정산 상세보기
            </h4>   
            {isLoading ? <Loader/> : <RebateNumber rebate = {rebate}/>}
            
            
        </DefaultLayout>
        </>
    )
}