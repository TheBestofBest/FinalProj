'use client'

import api from "@/util/api"
import { useParams } from 'next/navigation'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation';

export default function AnswerEdit() {
    const params = useParams()
    const router = useRouter();

    const [isLoading, setIsLoading] = useState(false)
    const [answer, setAnswer] = useState({ content: '' })

    useEffect(() => {
        fetchAnswer()
    }, [])

    const fetchAnswer = async () => {
        try {
            const response = await api.get(`/answers/${params.id}`)
            setAnswer(response.data.data.answer)
            setIsLoading(true)
        } catch (error) {
            console.log(error)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await api.patch(`/answers/${params.id}`, answer)
            router.push('/answer');
        } catch (error) {
            console.log(error)
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target
        setAnswer({ ...answer, [name]: value })
    }

    return (
        <>
            {isLoading ? (
                <>
                    <h1>수정페이지</h1>
                    <form onSubmit={handleSubmit}>
                        
                        <input
                            type="text"
                            name="content"
                            value={answer.content}
                            onChange={handleChange}
                        />
                        <button type="submit">수정</button>
                    </form>
                </>
            ) : (
                <></>
            )}
        </>
    )
}
