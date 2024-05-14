'use client'

import { useState } from 'react';
import { useParams } from 'next/navigation';
import { useQuery } from '@tanstack/react-query';
import api from "@/util/api";

export default function ArticleDetail() {
    const params = useParams();

    const getArticle = async () => {
        return await api.get(`/articles/${params.id}`)
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
            <>
                <h1>게시판 상세 {params.id}번</h1>
                <div>{articleData.subject}</div>
                <div>{articleData.content}</div>
                <div>{articleData.createdDate}</div>
                <div>{articleData.modifiedDate}</div>
                <AnswerCreate />
            </>
        );
    }
}

function AnswerCreate() {
    const params = useParams();
    const [answer, setAnswer] = useState({ content: '' });

    const getAnswers = async () => {
        return await api.get(`/answers`)
            .then((response) => response.data.data.answers);
    }

    const { isLoading: answerLoading, error: answerError, data: answerData } = useQuery({
        queryKey: ['answers'],
        queryFn: getAnswers
    });

    const handleSubmit = async (e) => {
        e.preventDefault();
        await api
            .post('/answers', answer)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            })
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAnswer({ ...answer, [name]: value });
    };

    if (answerError) {
        console.log(answerError);
    }

    return (
        <>
            <h3>글 등록</h3>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>내용:</label>
                    <textarea name="content" value={answer.content} onChange={handleChange} />
                </div>
                <button type="submit">등록</button>
            </form>
            {answerLoading ? (
                <div>Loading...</div>
            ) : (
                <>
                    {answerData && answerData.map((answer, index) => (
                        <div key={index}>
                            <div>{answer.content}</div>
                            {/* 답변의 내용 등 다른 정보도 필요한 경우 추가 */}
                        </div>
                    ))}
                </>
            )}
        </>
    );
}

