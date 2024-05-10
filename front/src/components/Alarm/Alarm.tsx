"use client";
import { useQuery, useQueryClient } from "@tanstack/react-query";

import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";

export default function Alarm() {
  const [stompClient, setStompClient] = useState<Client | null>(null);

  const [titleBgColor, setTitleBgColor] = useState("");
  const [bgColor, setBgColor] = useState("");
  const [borderColor, setBorderColor] = useState("");
  const [textColor, setTextColor] = useState("");

  const [categoryToKorean, setcategoryToKorean] = useState("");

  const [alarms, setAlarms] = useState({
    category: "",
    categoryId: "",
    message: "",
  });
  const handleMessage = (message: IMessage) => {
    const receive = JSON.parse(message.body);
    console.log(receive);
    if (receive.category === "all") {
      setTitleBgColor("bg-rose-500");
      setBgColor("bg-rose-100");
      setBorderColor("border-rose-400");
      setTextColor("text-rose-700");
      setcategoryToKorean("전체");
    }
    if (receive.category === "dept") {
      setTitleBgColor("bg-green-500");
      setBgColor("bg-green-100");
      setBorderColor("border-green-400");
      setTextColor("text-green-700");
      setcategoryToKorean("부서");
    }
    if (receive.category === "member") {
      setTitleBgColor("bg-blue-500");
      setBgColor("bg-blue-100");
      setBorderColor("border-blue-400");
      setTextColor("text-blue-700");
      setcategoryToKorean("개인");
    }
    setAlarms(receive);
    setTimeout(() => {
      setAlarms({ category: "", categoryId: "", message: "" });
    }, 5000);
  };

  const { data, isLoading } = useQuery({
    queryKey: ["member"],
  });
  const queryClient = useQueryClient();

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
  //
  if (isLoading) {
    return;
  }

  return (
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
      </div>
    </div>
  );
}
