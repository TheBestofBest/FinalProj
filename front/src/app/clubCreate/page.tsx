'use client'

import { useState } from 'react';
import api from '@/util/api';
import { useRouter } from 'next/navigation'; // 수정
import { useQueryClient } from '@tanstack/react-query';

export default function ClubCreate() {
    const router = useRouter();
    const [club, setClub] = useState({ clubName: ''});

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/clubs', club);
            // 등록 성공 시, 이전 페이지로 이동하거나 사용자에게 알림을 제공할 수 있습니다.
            router.push('/club');
        } catch (error) {
            console.error('등록 중 에러:', error);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setClub({ ...club, [name]: value });
    };

    return (
        <>
            <h3>글 등록</h3>
            <form onSubmit={handleSubmit}>
               
                <div>
                    <label>내용:</label>
                    <textarea name="clubName" value={club.clubName} onChange={handleChange} />
                </div>
                <button type="submit">등록</button>
            </form>
        </>
    );
}
