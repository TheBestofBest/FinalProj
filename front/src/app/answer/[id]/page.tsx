'use client'

import { useState } from 'react';
import { useParams } from 'next/navigation';
import { useQuery } from '@tanstack/react-query';
import api from "@/util/api";
import Link from 'next/link'
import DefaultLayout from "@/components/Layouts/DefaultLayout";

export default function AnswerDetail() {
    const params = useParams();

    const getAnswer = async () => {
        return await api.get(`/api/v1/answers/${params.id}`)
            .then((response) => response.data.data.answer);
    }

    const { isLoading: answerLoading, error: answerError, data: answerData } = useQuery({
        queryKey: ['answer'],
        queryFn: getAnswer
    });

    if (answerError) {
        console.log(answerError);
    }

    if (answerLoading) {
        return <>Loading...</>;
    }

    if (answerData) {
        return (
            <DefaultLayout>
                <h1>댓글 상세 {params.id}번</h1>
               
                <div>{answerData.content}</div>
                <Link href={`/answer/${params.id}/edit`}>수정하기</Link>
                
            </DefaultLayout>
        );
    }
}