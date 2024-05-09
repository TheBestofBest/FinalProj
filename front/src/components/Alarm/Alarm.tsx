"use client";
import { useQuery, useQueryClient } from "@tanstack/react-query";

import { useEffect, useState } from "react";
import { Client, IMessage } from "@stomp/stompjs";

export default function Alarm() {
  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [color, setColor] = useState("");
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
      setColor("rose");
      setcategoryToKorean("전체");
    }
    if (receive.category === "dept") {
      console.log("돌아요");
      setColor("green");
      setcategoryToKorean("부서");
    }
    if (receive.category === "member") {
      setColor("blue");
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
  //
  // ${color} ${alarms.message ? "block" : "hidden"}
  return (
    <div role="alert" className={` absolute bottom-5 left-5 z-999999 w-fit`}>
      <div
        className={`bg-${color}-500 rounded-t px-4 py-2 font-bold text-white`}
      >
        {categoryToKorean}
      </div>
      <div
        className={`border-${color}-400 bg-${color}-100 text-${color}-700 rounded-b border border-t-0 px-4 py-3`}
      >
        <p>{alarms.message}</p>
      </div>
    </div>
  );
}
