'use client'

import { useParams, useRouter } from "next/navigation";
import React, { useEffect, useRef, useState } from "react";
import { ChatLog, ChattingRoom, Member, Message } from "../type";
import api from "@/util/api";
import { Params } from "next/dist/shared/lib/router/utils/route-matcher";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { Client } from '@stomp/stompjs';

const Id = () => {

    const params: Params = useParams();
    const router = useRouter();
    const queryClient = useQueryClient();
    const memberData: any = queryClient.getQueryData(["member"]);

    const [chattingRoom, setChattingRoom] = useState<ChattingRoom>();
    const [chattingLogs, setChattingLogs] = useState<ChatLog[]>([]);

    const containerRef = useRef<HTMLDivElement>(null);
    const textareaRef = useRef<HTMLTextAreaElement>(null);

    const memberCount = useRef<number>(0);


    useEffect(() => {
        // 새로운 메시지가 추가될 때마다 스크롤을 맨 아래로 이동
        containerRef.current?.scrollTo(0, containerRef.current.scrollHeight);
    }, [chattingLogs]);

    useEffect(() => {
        loginCheck();
        fetchChattingRoom();
        fetchChatLogs();
        wsHandler();
        joinMessage();
    }, [])

    useEffect(() => {
        fetchChattingRoom();
        fetchChatLogs();
    }, [memberCount])

    const fetchChattingRoom = () => {
        api.get(`/api/v1/chats/${params.id}`)
            .then(response => {
                setChattingRoom(response.data.data.chattingRoomDto);
            })
            .catch(err => {
                console.error("채팅방 없음");
            })
    }

    const fetchChatLogs = () => {
        api.get(`/api/v1/logs/${params.id}`)
            .then(response => {
                setChattingLogs(response.data.data.chatLogDtoList);
            })
            .catch(err => {
                console.error("로그 없음");
            })
    }

    const [message, setMessage] = useState<Message>({
        roomId: parseInt(params.id),
        content: '',
        username: memberData?.username,
        name: memberData?.name,
        isCheck: chattingRoom?.members.length
    });

    //채팅방
    const [stomp, setStomp] = useState<Client>();

    const wsHandler = () => {
        const chatClient = new Client({
            brokerURL: 'ws://localhost:8090/chat',
            reconnectDelay: 5000,
        });
        chatClient.onConnect = () => {
            console.log('웹 소켓 연결이 열렸습니다.');
            chatClient.subscribe(`/topic/chat/send/${params.id}`, (data) => {
                fetchChatLogs();
            });
            chatClient.subscribe(`/topic/chat/join/${params.id}`, (data) => {

            });
            chatClient.subscribe(`/topic/chat/exit/${params.id}`, (data) => {
                fetchChatLogs();
            });
        };
        chatClient.activate();
        setStomp(chatClient);
        chatClient.onStompError = (frame) => {
            console.error('STOMP 오류:', frame);
        };
    }

    const joinMessage = () => {
        stomp?.publish({
            destination: `/app/chat/join/${params.id}`,
            body: JSON.stringify({
                roomId: parseInt(params.id),
                content: `${memberData.name} 님이 입장했습니다.`,
                username: undefined,
                name: undefined,
                isCheck: 0
            })
        });
        console.log("입장~")
    }

    const sendMessage = async () => {
        if (message.content.trim() === '') return;
        stomp?.publish({
            destination: `/app/chat/send/${params.id}`,
            body: JSON.stringify(message)
        });
        setMessage({
            roomId: parseInt(params.id),
            content: '',
            username: memberData?.username,
            name: memberData?.name,
            isCheck: message.isCheck ? -1 : undefined
        });
        if (textareaRef.current) {
            textareaRef.current.style.height = 'auto';
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setMessage({ ...message, content: e.target.value });
        console.log({ ...message, content: e.target.value });
        e.target.style.height = 'auto';
        e.target.style.height = `${e.target.scrollHeight}px`;
    };

    const loginCheck = () => {
        if (memberData == undefined) {
            router.push("/auth/signin");
        }
    }

    //enter입력
    const handleEnterPress = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
        if (e.key == 'Enter') {
            if (e.ctrlKey) {
                insertNewLine(e.currentTarget);
            } else {
                e.preventDefault();
                sendMessage();
            }
        }
    }
    const insertNewLine = (inputElement: HTMLTextAreaElement) => {
        const start = inputElement.selectionStart;
        const end = inputElement.selectionEnd;

        if (start !== null && end !== null) {
            const value = message.content;
            const newValue = value.substring(0, start) + '\n' + value.substring(end);
            setMessage({
                ...message,
                content: newValue,
            });
            // Move the cursor to the position after the newline
            setTimeout(() => {
                inputElement.selectionStart = inputElement.selectionEnd = start + 1;
            }, 0);
        }
    };

    function calculateDate(logDate: any) {
        var createdDate: Date = new Date(logDate);
        var nowDate: Date = new Date();
        var difference: number = Math.floor((nowDate.getTime() - createdDate.getTime()) / 1000);

        if (difference == 0) {
            return "방금 전";
        }
        //초
        else if (difference < 60) {
            return `${Math.floor(difference)}초 전`;
        }
        //분
        else if (difference < 3600) {
            return `${Math.floor(difference / 60)}분 전`;
        }
        //시
        else if (difference < 86400) {
            return `${Math.floor(difference / 3600)}시간 전`;
        }
        //일
        else if (difference < 31536000) {
            return `${Math.floor(difference / 86400)}일 전`;
        }
        //년
        else {
            return `${Math.floor(difference / 31536000)}년 전`;
        }
    }

    return (
        <>
            <header className="bg-blue-700 text-white py-4 px-6 rounded shadow-lg">
                <h1 className="text-2xl font-bold">{chattingRoom?.name}</h1>
                <span className="text-sm">{chattingRoom?.members.map((name, index) =>
                    <React.Fragment key={index}>
                        {index == 0 ? name : `, ${name}`}
                    </React.Fragment>)}
                </span>
            </header>
            <main className="flex-1 h-150 p-6 overflow-y-auto" ref={containerRef}>
                <div className="flex flex-col gap-2">
                    {chattingLogs?.map((chatLog: ChatLog) => <>
                        {chatLog.username == memberData?.username ?
                            <div className="border-[#eee] bg-blue-200 p-4 rounded-lg max-w-xs self-end shadow-lg">
                                <p className="text-sm whitespace-pre-wrap">{chatLog.content}</p>
                                <div className="flex justify-between items-end mt-2">
                                    <span className="text-xs">{chatLog.name}</span>
                                    <span className="ml-20 text-xs">{calculateDate(chatLog.createdDate)}</span>
                                </div>
                            </div>
                            : chatLog.username == null ?
                                <div className="flex items-center justify-center rounded-lg p-4 my-4">
                                    <span className=" px-3 py-1 rounded text-gray-700 text-sm"> {chatLog.content}</span>
                                </div>
                                :
                                <div className="border-[#eee] bg-zinc-300 p-4 rounded-lg max-w-xs self-start shadow-lg">
                                    <p className="text-sm whitespace-pre-wrap">{chatLog.content}</p>
                                    <div className="flex justify-between items-end mt-2">
                                        <span className="text-xs">{chatLog.name}</span>
                                        <span className="ml-20 text-xs">{calculateDate(chatLog.createdDate)}</span>
                                    </div>
                                </div>
                        }
                    </>)}
                    {/* {messages?.map((message: Message) => <>
                        {message.username != memberData?.username ?
                            <div className="bg-gray-100 p-4 rounded-lg max-w-xs self-start border">
                                <p className="text-sm">{message.content}</p>
                                <p className="text-sm">{message.name}</p>
                            </div> :
                            <div className="bg-blue-200 p-4 rounded-lg max-w-xs self-end">
                                <p className="text-sm">{message.content}</p>
                                <p className="text-sm">{message.name}</p>
                            </div>
                        }
                    </>)} */}
                </div>
            </main>
            <footer className="bg-gray-300 py-4 px-6">
                <div className="flex">
                    <textarea placeholder="Type your message..."
                        className="flex items-center border-0.5 flex-1 h-11 rounded-l-lg p-2 resize-none focus:outline-none shadow-lg"
                        name="content" value={message.content} onChange={handleChange} onKeyDown={handleEnterPress}
                        rows={1} style={{ overflow: 'hidden' }} />
                    <button className="bg-blue-700 text-white px-4 rounded-r-lg shadow-lg" type="button" onClick={sendMessage}>Send</button>
                </div>
            </footer>
        </>
    )
}

export default Id;