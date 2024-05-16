'use client'

import api from "@/util/api"
import { useParams } from 'next/navigation'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation';
import DefaultLayout from "@/components/Layouts/DefaultLayout";

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
            const response = await api.get(`/api/v1/answers/${params.id}`)
            setAnswer(response.data.data.answer)
            setIsLoading(true)
        } catch (error) {
            console.log(error)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await api.patch(`/api/v1/answers/${params.id}`, answer)
            router.push(`/api/v1/answer/${params.id}`);
        } catch (error) {
            console.log(error)
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target
        setAnswer({ ...answer, [name]: value })
    }

    return (
        <DefaultLayout>
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
        </DefaultLayout>
    )
}