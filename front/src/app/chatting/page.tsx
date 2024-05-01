'use client'

import { Metadata } from "next";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import { useEffect, useRef, useState } from "react";



let socket: any;

type Message = {
  roomId: number;
  author: string;
  content: string;
};

const Chatting = () => {

    const ws = useRef<WebSocket | null>(null);
    const [username, setUsername] = useState(""); //이름 지정
    const [chosenUsername, setChosenUsername] = useState(""); //선택된 유저 이름 지정 
    const [message, setMessage] = useState<Message>({
      roomId: 0,
      author: '',
      content: ''
    });
    const [messages, setMessages] = useState<Message[]>([]); //매세지들 (채팅창에 전부 다 쳐서 쌓인 글들)

    
  useEffect(() => {
    ws.current = new WebSocket("ws://localhost:8090/ws/chats/1")
    ws.current.onopen = () => {
      console.log('웹 소켓 연결이 열렸습니다.');
    };
    // 메시지를 수신했을 때 실행되는 콜백 함수
    ws.current.onmessage = (event) => {
      console.log('메시지를 수신했습니다:', event.data);
      const receivedMessage = JSON.parse(event.data);
      setMessages(messages => [...messages, receivedMessage]);
    };
    // 연결이 닫혔을 때 실행되는 콜백 함수
    ws.current.onclose = () => {
      console.log('웹 소켓 연결이 닫혔습니다.');
    };
  }, [])



  const sendMessage = async () => {
    ws.current?.send(JSON.stringify(message));
    setMessage({
      roomId: 0,
      content: '',
      author: ''
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setMessage({ ...message, [name]: value });
    console.log({ ...message, [name]: value });
  };
  // const getChatRooms = async () => {
  // }

  return (
    <DefaultLayout>
      <main>
      adasdas
      <div>
        {messages?.map((message : Message) => <>
        <div className="flex">
          <span>번호: {message.roomId}</span>
          <span>내용: {message.content}</span>
          <span>이름: {message.author}</span>
          </div>
        </>)}
        <label >방번호</label>
        <input type="number" name="roomId" value={message.roomId} onChange={handleChange} />
        <label >메세지</label>
        <input type="text" name="content" value={message.content} onChange={handleChange} />
        <label >작성자</label>
        <input type="text" name="author" value={message.author} onChange={handleChange} />
        <button type="button" onClick={sendMessage}>전송</button>
      </div>
    </main>
    </DefaultLayout>
  );
};

export default Chatting;
