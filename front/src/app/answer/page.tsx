'use client'

import Link from 'next/link'
import { useEffect, useState } from 'react'
import api from "@/util/api"
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import DefaultLayout from "@/components/Layouts/DefaultLayout";

export default function Answer() {

    const getAnswers = async () => {
        return await api.get('/api/v1/answers')
            .then((response) => response.data.data.answers)
    }

    const {isLoading, error, data} = useQuery({
        queryKey: ['answers'],
        queryFn: getAnswers
    });


    const deleteAnswer = async (id) => {
        await api.delete(`/api/v1/answers/${id}`)
    }

    const queryClient = useQueryClient()
    const mutation = useMutation({
        mutationFn: deleteAnswer,
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['answers']})
        }
    })

    if (error) {
        console.log(error)
    }

    if (isLoading) <>Loading...</>

    if (data) {
        return (
            <DefaultLayout>

                <ul>

                    {data.map((row) => (
                        <li key={row.id}>
                            {row.id} /{' '}
                            <Link href={`/answer/${row.id}`}>{row.content}</Link> /{' '}
                            {row.author} / {row.createdDate}
                            <button onClick={() => mutation.mutate(row.id)}>
                                삭제
                            </button>
                        </li>
                    ))}
                </ul>
                <Link href="/answerCreate">
                Answer Create 페이지로 이동
                </Link>
            </DefaultLayout>
        )
    }
}