"use client";
import { useQuery, useQueryClient } from "@tanstack/react-query";

import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";
import { useRouter } from "next/navigation";
import api from "@/util/api";

export default function Alarm() {
  const [stompClient, setStompClient] = useState<Client | null>(null);

  const [titleBgColor, setTitleBgColor] = useState("");
  const [bgColor, setBgColor] = useState("");
  const [borderColor, setBorderColor] = useState("");
  const [textColor, setTextColor] = useState("");

  //회의참석
  const router = useRouter();
  const [roomId, setRoomId] = useState("");
  const [openAlarm, setOpenAlarm] = useState<boolean>(true);
  const queryClient = useQueryClient();

  const [categoryToKorean, setcategoryToKorean] = useState("");

  const [alarms, setAlarms] = useState({
    category: "",
    categoryId: "",
    message: "",
  });

  const getAlarms = async () => {
    await api.get("/api/v1/alarms/top10").then((res) => {
      queryClient.setQueryData(["alarms"], res.data.data);
    });
  };

  const handleMessage = (message: IMessage) => {
    const receive = JSON.parse(message.body);
    console.log(receive);
    if (receive.category === "all") {
      setOpenAlarm(true);
      setTitleBgColor("bg-rose-500");
      setBgColor("bg-rose-100");
      setBorderColor("border-rose-400");
      setTextColor("text-rose-700");
      setcategoryToKorean("전체");
    }
    if (receive.category === "dept") {
      setOpenAlarm(true);
      setTitleBgColor("bg-green-500");
      setBgColor("bg-green-100");
      setBorderColor("border-green-400");
      setTextColor("text-green-700");
      setcategoryToKorean("부서");
    }
    if (receive.category === "member") {
      setOpenAlarm(true);
      setTitleBgColor("bg-blue-500");
      setBgColor("bg-blue-100");
      setBorderColor("border-blue-400");
      setTextColor("text-blue-700");
      setcategoryToKorean("개인");
    }
    if (receive.category === "meeting") {
      setOpenAlarm(true);
      setTitleBgColor("bg-blue-500");
      setBgColor("bg-blue-100");
      setBorderColor("border-blue-400");
      setTextColor("text-blue-700");
      setcategoryToKorean("회의 초대");
      api.get(`/api/v1/members/me`).then((res) => {
        if (!res.data.isSuccess) {
          return alert(res.data.msg);
        }
        const memberData = queryClient.setQueryData(
          ["member"],
          res.data.data.memberDTO,
        );
        setRoomId(memberData.meetingState.split("번")[0]);
      });
    }
    setAlarms(receive);
    getAlarms();

    if (receive.category !== "meeting") {
      setTimeout(() => {
        setAlarms({ category: "", categoryId: "", message: "" });
      }, 5000);
    }
  };

  const { data, isLoading } = useQuery({
    queryKey: ["member"],
  });

  const { data: alarmsData, isLoading: isAlarmsLoading } = useQuery({
    queryKey: ["alarms"],
    queryFn: getAlarms,
  });

  useEffect(() => {
    if (!isLoading && data) {
      const client = new Client({
        brokerURL: "ws://localhost:8090/alarm", // 서버 WebSocket URL
        reconnectDelay: 10000000,
      });
      client.onConnect = function () {
        // 구독 활성화
        client.subscribe(
          `/topic/public/alarm/member/${data.id}`,
          handleMessage,
        );
        client.subscribe(
          `/topic/public/alarm/meeting/${data.id}`,
          handleMessage,
        );
        client.subscribe(
          `/topic/public/alarm/dept/${data.department.id}`,
          handleMessage,
        );
        client.subscribe(`/topic/public/alarm/all/0`, handleMessage);
        // 연결된 상태에서 메시지 전송
        if (client.connected) {
          console.log("알람 연결됨");
        }
      };
      client.activate();
      setStompClient(client);

      return () => {
        client.deactivate();
      };
    }
  }, [isLoading, data, queryClient]);

  //회의참석
  const approveMeeting = () => {
    api
      .patch(`/api/v1/meetings/${roomId}/approve`)
      .then((res) =>
        queryClient.setQueryData(["member"], res.data.data.memberDTO),
      );
    setOpenAlarm(false);
    router.push("/meeting");
  };

  const rejectMeeting = () => {
    api.patch(`/api/v1/meetings/${roomId}/exit`).then((res) => {
      api.get(`/api/v1/members/me`).then((res) => {
        if (!res.data.isSuccess) {
          return alert(res.data.msg);
        }
        queryClient.setQueryData(["member"], res.data.data.memberDTO);
      });
      alert("회의를 거절하였습니다.");
      setOpenAlarm(false);
    });
  };

  if (isLoading) {
    return;
  }

  return (
    <>
      {openAlarm && (
        <div
          role="alert"
          className={` absolute bottom-5 left-5 z-999999 w-fit ${alarms.message ? "block" : "hidden"}`}
        >
          <div
            className={`${titleBgColor} rounded-t  px-4 py-2 font-bold text-white`}
          >
            {categoryToKorean}
          </div>
          <div
            className={`${borderColor} ${bgColor} ${textColor} rounded-b border border-t-0 px-4 py-3`}
          >
            <p>{alarms.message}</p>
            {categoryToKorean.includes("회의") ? (
              <div className="mt-1 flex items-center justify-between">
                <p>수락하시겠습니까?</p>
                <div>
                  <button
                    className="mx-1 rounded border px-1 hover:bg-gray"
                    onClick={rejectMeeting}
                  >
                    거절
                  </button>
                  <button
                    className="mx-1 rounded border bg-blue-500 px-1 text-white hover:bg-blue-400"
                    onClick={approveMeeting}
                  >
                    참여
                  </button>
                </div>
              </div>
            ) : (
              <></>
            )}
          </div>
        </div>
      )}
    </>
  );
}
