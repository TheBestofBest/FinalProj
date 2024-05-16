'use client'

import { useState } from 'react';
import api from '@/util/api';
import { useRouter } from 'next/navigation'; // 수정
import { useQueryClient } from '@tanstack/react-query';
import DefaultLayout from "@/components/Layouts/DefaultLayout";

export default function ArticleCreate() {
    const router = useRouter();
    const [article, setArticle] = useState({ subject: '', content: '' });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/api/v1/articles', article);
            // 등록 성공 시, 이전 페이지로 이동하거나 사용자에게 알림을 제공할 수 있습니다.
            router.push('/article');
        } catch (error) {
            console.error('등록 중 에러:', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setArticle({ ...article, [name]: value });
    };

    return (
        <DefaultLayout>
            <h3>글 등록</h3>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>제목:</label>
                    <input type="text" name="subject" value={article.subject} onChange={handleChange} />
                </div>
                <div>
                    <label>내용:</label>
                    <textarea name="content" value={article.content} onChange={handleChange} />
                </div>
                <button type="submit">등록</button>
            </form>
        </DefaultLayout>
    );
}