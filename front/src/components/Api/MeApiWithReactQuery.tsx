import api from "@/util/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import Loader from "../common/Loader";
import { useRouter } from "next/navigation";

export default function MeApiWithReactQuery() {
  const queryClient = useQueryClient();
  const router = useRouter();

  const getMember = async () => {
    await api.get("/api/v1/members/me").then((res) => {
      queryClient.setQueryData(["member"], res.data.data.memberDTO);
    });
  };

  const { data, isLoading } = useQuery({
    queryKey: ["member"],
    queryFn: getMember,
  });

  // 데이터 패칭 중
  if (isLoading) {
    return (
      <>
        <Loader />
      </>
    );
  }

  if (data) {
    return;
  }

  // 비 로그인 시 강제 이동
  if (queryClient.getQueryData(["member"]) === undefined) {
    router.replace("/auth/signin");
  }
}
