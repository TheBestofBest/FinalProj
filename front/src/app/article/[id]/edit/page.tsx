'use client'

import api from "@/util/api"
import { useParams } from 'next/navigation'
import { useEffect, useState } from 'react'
import { useRouter } from 'next/navigation';

export default function ArticleEdit() {
    const params = useParams()
    const router = useRouter();

    const [isLoading, setIsLoading] = useState(false)
    const [article, setArticle] = useState({ subject: '', content: '' })

    useEffect(() => {
        fetchArticle()
    }, [])

    const fetchArticle = async () => {
        try {
            const response = await api.get(`/articles/${params.id}`)
            setArticle(response.data.data.article)
            setIsLoading(true)
        } catch (error) {
            console.log(error)
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            await api.patch(`/articles/${params.id}`, article)
            router.push('/article');
        } catch (error) {
            console.log(error)
        }
    }

    const handleChange = (e) => {
        const { name, value } = e.target
        setArticle({ ...article, [name]: value })
    }

    return (
        <>
            {isLoading ? (
                <>
                    <h1>수정페이지</h1>
                    <form onSubmit={handleSubmit}>
                        <input
                            type="text"
                            name="subject"
                            value={article.subject}
                            onChange={handleChange}
                        />
                        <input
                            type="text"
                            name="content"
                            value={article.content}
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
