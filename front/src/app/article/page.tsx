'use client'

import Link from 'next/link'
import { useEffect, useState } from 'react'
import api from "@/util/api"
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query'

export default function Article() {

    const getArticles = async () => {
        return await api.get('/articles')
            .then((response) => response.data.data.articles)
    }

    const {isLoading, error, data} = useQuery({
        queryKey: ['articles'],
        queryFn: getArticles
    });


    const deleteArticle = async (id) => {
        await api.delete(`/articles/${id}`)
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
            <>
                
                <ul>
                    번호 / 제목 / 작성자 / 생성일 / 삭제
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

            </>
        )
    }


}
