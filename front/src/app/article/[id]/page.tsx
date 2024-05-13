'use client'

import { useParams } from 'next/navigation';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import api from "@/util/api";
import React, { useState } from 'react';

export default function ArticleDetail() {
    const params = useParams();

    // 게시글 정보를 가져오는 함수
    const getArticle = async () => {
        return await api.get(`/articles/${params.id}`)
            .then((response) => response.data.data.article);
    }

    // 답변 목록을 가져오는 함수
    const getAnswers = async () => {
        return await api.get(`/answers/${params.id}/articles`)
            .then((response) => response.data.data.answers);
    }

    // 게시글 정보를 가져오는 쿼리 훅
    const { isLoading: articleLoading, error: articleError, data: articleData } = useQuery({
        queryKey: ['article'],
        queryFn: getArticle
    });

    // 답변 목록을 가져오는 쿼리 훅
    const { isLoading: answerLoading, error: answerError, data: answerData } = useQuery({
        queryKey: ['answers'],
        queryFn: getAnswers
    });

    // 답변을 등록하는 뮤테이션
    const mutation = useMutation({
        mutationFn: (newAnswer) => api.post('/answers', newAnswer),
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ['answers']})
        }
    });

    // 답변을 제출하는 함수
    const handleSubmit = async (e) => {
        e.preventDefault();
        mutation.mutate({ content: answer });
        setAnswer('');
    };

    const [answer, setAnswer] = useState('');

    // 게시글 로딩 중인 경우
    if (articleLoading) {
        return <>Loading...</>;
    }

    // 게시글 로딩 에러 발생한 경우
    if (articleError) {
        console.log(articleError);
        return <>Error: {articleError.message}</>;
    }

    // 게시글 정보가 로드된 후, 게시글 상세 내용과 답변 목록을 표시합니다.
    return (
        <>
            <h1>게시글 상세 페이지 - {params.id}번</h1>
            <div>
                <h2>{articleData.subject}</h2>
                <p>{articleData.content}</p>
                <p>작성일: {articleData.createdDate}</p>
                <p>수정일: {articleData.modifiedDate}</p>
            </div>

            <h2>답변 작성</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>내용:</label>
                    <textarea value={answer} onChange={(e) => setAnswer(e.target.value)} />
                </div>
                <button type="submit">등록</button>
            </form>

            <h2>답변 목록</h2>
            {answerLoading ? (
                <>Loading...</>
            ) : (
                <ul>
                    {answerData.map((answer) => (
                        <li key={answer.id}>{answer.content}</li>
                    ))}
                </ul>
            )}
        </>
    );
}
