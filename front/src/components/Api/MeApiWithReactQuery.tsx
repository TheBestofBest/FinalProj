import api from "@/util/api";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import Loader from "../common/Loader";

export default function MeApiWithReactQuery() {
  const queryClient = useQueryClient();
  const getMember = async () => {
    console.log("!!1141234313");
    await api.get("/api/v1/members/me").then((res) => {
      queryClient.setQueryData(["member"], res.data.data.memberDTO);
    });
  };
  const { data, isLoading } = useQuery({
    queryKey: ["member"],
    queryFn: getMember,
  });

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
}
