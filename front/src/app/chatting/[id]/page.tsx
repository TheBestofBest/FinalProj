'use client'

import { useParams, useRouter } from "next/navigation";
import React, { useEffect, useRef, useState } from "react";
import { ChatLog, ChattingRoom, Member, Message } from "../type";
import api from "@/util/api";
import { Params } from "next/dist/shared/lib/router/utils/route-matcher";
import { useQuery, useQueryClient } from "@tanstack/react-query";

const Id = () => {

    const params: Params = useParams();
    const router = useRouter();
    const queryClient = useQueryClient();
    const memberData: any = queryClient.getQueryData(["member"]);
    const ws = useRef<WebSocket | null>(null);

    const [messages, setMessages] = useState<Message[]>([]); //매세지들 (채팅창에 전부 다 쳐서 쌓인 글들)
    const [chattingRoom, setChattingRoom] = useState<ChattingRoom>();
    const [chattingLogs, setChattingLogs] = useState<ChatLog[]>([]);

    const containerRef = useRef(null);

    useEffect(() => {
        // 새로운 메시지가 추가될 때마다 스크롤을 맨 아래로 이동
        containerRef.current ?.scrollTo(0, containerRef.current.scrollHeight);
        // containerRef.current.scrollTop = containerRef.current.scrollHeight;
    }, [chattingLogs, messages]);

    useEffect(() => {
        fetchChattingRoom();
        fetchChatLogs();
        wsHandler();
    }, [])

    const wsHandler = async () => {
        ws.current = await new WebSocket(`ws://localhost:8090/ws/chats/${params.id}`)
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
    }


    const fetchChattingRoom = () => {
        api.get(`/api/v1/chats/${params.id}`)
            .then(response => {
                setChattingRoom(response.data.data.chattingRoomDto);
            }).catch(err => {
            })
    }

    const fetchChatLogs = () => {
        api.get(`/api/v1/logs/${params.id}`)
            .then(response => {
                setChattingLogs(response.data.data.chatLogDtoList);
            }).catch(err => {
            })
    }

    const [message, setMessage] = useState<Message>({
        roomId: parseInt(params.id),
        content: '',
        username: memberData.username,
        name: memberData.name,
        isCheck: chattingRoom?.members.length
    });

    const sendMessage = async () => {
        ws.current?.send(JSON.stringify(message));
        setMessage({
            roomId: parseInt(params.id),
            content: '',
            username: memberData.username,
            name: memberData.name,
            isCheck: message.isCheck ? -1 : undefined
        });
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setMessage({ ...message, [name]: value });
        console.log({ ...message, [name]: value });
    };



    return (
        <>
            <header className="bg-blue-700 text-white py-4 px-6 rounded">
                <h1 className="text-2xl font-bold">{chattingRoom?.name}</h1>
                <span className="text-sm">{chattingRoom?.members.map((name, index) =>
                    <React.Fragment key={index}>
                        {index == 0 ? name : `, ${name}`}
                    </React.Fragment>)}
                </span>
            </header>
            <main className="flex-1 h-115 p-6 overflow-y-auto" ref={containerRef}>
                <div className="flex flex-col gap-2">
                    {chattingLogs?.map((chatLog: ChatLog) => <>
                        {chatLog.username != memberData.username ?
                            <div className="bg-gray-100 p-4 rounded-lg max-w-xs self-start border">
                                <p className="text-sm">{chatLog.content}</p>
                                <p className="text-sm">{chatLog.name}</p>
                            </div> :
                            <div className="bg-blue-200 p-4 rounded-lg max-w-xs self-end">
                                <p className="text-sm">{chatLog.content}</p>
                                <p className="text-sm">{chatLog.name}</p>
                            </div>
                        }
                    </>)}
                    {messages?.map((message: Message) => <>
                        {message.username != memberData.username ?
                            <div className="bg-gray-100 p-4 rounded-lg max-w-xs self-start border">
                                <p className="text-sm">{message.content}</p>
                                <p className="text-sm">{message.name}</p>
                            </div> :
                            <div className="bg-blue-200 p-4 rounded-lg max-w-xs self-end">
                                <p className="text-sm">{message.content}</p>
                                <p className="text-sm">{message.name}</p>
                            </div>
                        }
                    </>)}
                </div>
            </main>
            <footer className="bg-gray-300 py-4 px-6 ">
                <div className="flex">
                    <input type="text" placeholder="Type your message..." className="flex-1 rounded-l-lg p-2 focus:outline-none"
                        name="content" value={message.content} onChange={handleChange} />
                    <button className="bg-blue-700 text-white px-4 rounded-r-lg" type="button" onClick={sendMessage}>Send</button>
                </div>
            </footer>
        </>
    )
}

export default Id;