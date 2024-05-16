'use client'

import { useState } from 'react';
import { useParams } from 'next/navigation';
import { useQuery } from '@tanstack/react-query';
import api from "@/util/api";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Link from 'next/link'

export default function ArticleDetail() {
    const params = useParams();

    const getArticle = async () => {
        return await api.get(`/api/v1/articles/${params.id}`)
            .then((response) => response.data.data.article);
    }

    const { isLoading: articleLoading, error: articleError, data: articleData } = useQuery({
        queryKey: ['article'],
        queryFn: getArticle
    });

    if (articleError) {
        console.log(articleError);
    }

    if (articleLoading) {
        return <>Loading...</>;
    }

    if (articleData) {
        return (
            <DefaultLayout>
                <h1>게시판 상세 {params.id}번</h1>
                <div>{articleData.subject}</div>
                <div>{articleData.content}</div>

                <Link href={`/article/${params.id}/edit`}>수정하기</Link>

            </DefaultLayout>
        );
    }

}

