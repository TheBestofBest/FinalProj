import api from "./api"

const getArticle = async (params: { id: any; }) => {
    return await api.get(`/articles/${params.id}`)
    .then((response) => response.data.data.article)
}


export default getArticle;