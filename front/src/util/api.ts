import { useQuery, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { Loader } from "next/dynamic";

const api = axios.create({
  baseURL: "http://localhost:8090",
  withCredentials: true,
});

axios.interceptors.request.use(function () {
  const queryClient = useQueryClient();
  const getMember = async () => {
    console.log("!!1141234313");
    await api.get("/api/v1/members/me").then((res) => {
      queryClient.setQueryData(["member"], res.data.data.memberDTO);
    });
  };
  const { data, isLoading, refetch } = useQuery({
    queryKey: ["member"],
    queryFn: getMember,
  });
});

export default api;
