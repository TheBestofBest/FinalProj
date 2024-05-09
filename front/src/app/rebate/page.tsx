'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import MemberTable from "./MemberTable";
import Link from "next/link";
import { useQueryClient } from "@tanstack/react-query";

interface memberInfo {
    id: BigInteger;
  }


export default function Rebate() {

  const queryClient = useQueryClient();

  const member = queryClient.getQueryData<memberInfo>(["member"]);

    return (
        <>
        <DefaultLayout>
            <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
                급여정산 페이지
            </h4>
            <Link
              href={`/rebate/${member.id}`}
              className="inline-flex items-center justify-center rounded-md bg-primary px-6 py-2 text-center font-medium text-white hover:bg-opacity-90">
              테스트페이지
            </Link>
            
            <MemberTable></MemberTable>
        </DefaultLayout>
        </>
    )
}