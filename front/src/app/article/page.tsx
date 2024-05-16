'use client'

import Link from 'next/link'
import { useEffect, useState } from 'react'
import api from "@/util/api"
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'
import DefaultLayout from "@/components/Layouts/DefaultLayout";

export default function Article() {

    const getArticles = async () => {
        return await api.get('/api/v1/articles')
            .then((response) => response.data.data.articles)
    }

    const {isLoading, error, data} = useQuery({
        queryKey: ['articles'],
        queryFn: getArticles
    });


    const deleteArticle = async (id) => {
        await api.delete(`/api/v1/articles/${id}`)
    }

    const queryClient = useQueryClient()
    const mutation = useMutation({
        mutationFn: deleteArticle,
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['articles']})
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
                            <Link href={`/article/${row.id}`}>{row.subject}</Link> /{' '}
                            {row.author} / {row.createdDate}
                            <button onClick={() => mutation.mutate(row.id)}>
                                삭제
                            </button>
                        </li>
                    ))}
                </ul>
                <Link href="/articleCreate">
    Article Create 페이지로 이동
</Link>

            </DefaultLayout>
        )
    }


}