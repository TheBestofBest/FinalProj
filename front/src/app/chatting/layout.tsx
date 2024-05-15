"use client";
import "jsvectormap/dist/css/jsvectormap.css";
import "flatpickr/dist/flatpickr.min.css";
import "@/css/satoshi.css";
import "@/css/style.css";
import React, { useEffect, useState } from "react";
import Loader from "@/components/common/Loader";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Link from "next/link";
import { ChattingRoom, Member } from "./type";
import api from "@/util/api";
import Image from "next/image";
import CreateBtn from "../../../public/images/chatiing/createButton.png"
import DropdownChattingRoom from "./DropdownChattingRoom";
import InviteModal from "./inviteModal";
import { useParams, useRouter } from "next/navigation";
import { QueryClient, useQueryClient } from "@tanstack/react-query";
import { Params } from "next/dist/shared/lib/router/utils/route-matcher";

export default function ChattingLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const router = useRouter();
    const params: Params = useParams();
    const queryClient = useQueryClient();
    const memberData: any = queryClient.getQueryData(["member"]);
    const [loading, setLoading] = useState<boolean>(true);
    const [chattingRooms, setChattingRooms] = useState<ChattingRoom[]>([]);
    const [isEmpty, setIsEmpty] = useState<boolean>();

    const [chattingRoom, setChattingRoom] = useState<ChattingRoom>();



    useEffect(() => {
        setTimeout(() => setLoading(false), 1000);
        fetchChattingRooms();
    }, []);

    const fetchChattingRooms = () => {
        api.get('/api/v1/chats')
            .then(response => {
                setChattingRooms(response.data.data.chattingRoomDtoList);
                setIsEmpty(false);
            }).catch(err => {
                setIsEmpty(true);
            })
    }

    const createChattingRoom = async () => {
        const response = await api.post('/api/v1/chats', { name: "새 채팅" });
        if (response.status == 200) {
            fetchChattingRooms();
        } else {
            alert('생성에 실패했습니다.');
        }
    }

    //채팅방 이름 수정
    const [modifyName, setModifyName] = useState<string>();
    const modifyChattingRoom = async (e: React.FormEvent<HTMLFormElement>, id: number) => {
        e.preventDefault();
        const response = await api.patch(`/api/v1/chats/${id}`, { name: modifyName });
        if (response.status == 200) {
            fetchChattingRooms();
            setModifyName("");
            offSelected();
            router.push(`/chatting`);
        } else {
            alert('수정에 실패했습니다.');
        }
    }
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setModifyName(e.target.value);
        console.log(e.target.value)
    }


    //채팅방 나가기
    const exitChattingRoom = async (id: number) => {
        console.log(id);
        const response = await api.patch(`/api/v1/chats/${id}/exit`);
        if (response.status == 200) {
            fetchChattingRoom(id);
            if (chattingRoom?.members == null) {
                deleteChattingRoom(id);
            }
            fetchChattingRooms();
            offSelected();
            if (params.id == id) {
                router.push("/chatting");
            }
        } else {
            alert('나가기에 실패했습니다.');
        }
    }

    const fetchChattingRoom = (id: number) => {
        api.get(`/api/v1/chats/${id}`)
            .then(response => {
                setChattingRoom(response.data.data.chattingRoomDto);
            })
    }

    const deleteChattingRoom = async (id: number) => {
        const response = await api.delete(`/api/v1/chats/${id}`);
        if (response.status == 200) {
            offSelected();
            if (params.id == id) {
                router.push("/chatting");
            }
        } else {
            alert('나가기에 실패했습니다.');
        }
    }

    //modal
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const openModal = () => {
        setModalIsOpen(true);
    }
    const closeModal = () => {
        setModalIsOpen(false);
    }

    //dropdown
    const [isHovered, setIsHovered] = useState<number>(0);
    const onMouseEnter = (id: number) => {
        setIsHovered(id);
    }
    const onMouseLeave = () => {
        setIsHovered(0);
    }

    //selected room
    const [isSelected, setIsSelected] = useState<number>(0);
    const onSelected = (id: number, name: string) => {
        setIsSelected(id);
        setModifyName(name);
    }
    const offSelected = () => {
        setIsSelected(0);
        setModifyName("");
    }


    //confirm Meeting
    const confirmMeeting = () => {
        const result= window.confirm("모임을 시작하시겠습니까?");
        if(result) {
            if (memberData.meetingState.includes("없음")) {
                createMeetingRoom();
            } else if (memberData.meetingState.includes("중")) {
                fetchMeetingRoom();
            } else {
                alert("초대받은 회의가 있습니다.");
                router.back();
            }
        }
    }
    const createMeetingRoom = () => {
        api.post('/api/v1/meetings', { name: "새 회의" })
            .then(response => {
                router.push(`/meeting/${response.data.data.meetingRoomDto.id}`);
                api.get(`/api/v1/members/me`)
                    .then(res => {
                        if (!res.data.isSuccess) {
                            return alert(res.data.msg);
                        }
                        queryClient.setQueryData(["member"], res.data.data.memberDTO);
                    })
            });
    }
    const fetchMeetingRoom = () => {
        const roomId = memberData.meetingState.split('번')[0];
        api.get(`/api/v1/meetings/${roomId}`)
            .then(response => {
                router.push(`/meeting/${roomId}`);
            })
    }

    return (
        <DefaultLayout>
            <div className="w-full flex justify-between font-bold">
                {modalIsOpen ? <InviteModal closeModal={closeModal} /> : <></>}
                <div className="flex flex-col w-1/4 mr-2">
                    <div className="flex w-full text-2xl justify-between mb-2">
                        <span>Chatting</span>
                        <div className="flex">
                        <button className="border p-2 rounded-full flex items-end bg-white hover:bg-gray" onClick={confirmMeeting}>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-5 h-5">
                                <path stroke-linecap="round" stroke-linejoin="round" d="m15.75 10.5 4.72-4.72a.75.75 0 0 1 1.28.53v11.38a.75.75 0 0 1-1.28.53l-4.72-4.72M4.5 18.75h9a2.25 2.25 0 0 0 2.25-2.25v-9a2.25 2.25 0 0 0-2.25-2.25h-9A2.25 2.25 0 0 0 2.25 7.5v9a2.25 2.25 0 0 0 2.25 2.25Z" />
                            </svg>
                        </button>
                        <button className="ml-2 border p-2 rounded-full flex items-end bg-white hover:bg-gray" onClick={createChattingRoom}>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-5 h-5">
                                <path stroke-linecap="round" stroke-linejoin="round" d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L10.582 16.07a4.5 4.5 0 0 1-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 0 1 1.13-1.897l8.932-8.931Zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0 1 15.75 21H5.25A2.25 2.25 0 0 1 3 18.75V8.25A2.25 2.25 0 0 1 5.25 6H10" />
                            </svg>
                        </button>
                        </div>
                    </div>
                    <div>
                        <button className="w-full border rounded mt-1 p-1 bg-white hover:bg-gray" onClick={openModal}>찾기 및 초대</button>
                    </div>
                    <div className="mt-2 h-171.5">
                        {isEmpty ? <></> :
                            chattingRooms.map((chattingRoom: ChattingRoom) =>
                                <>
                                    <Link className={`flex w-full border rounded p-1.5 mt-1
                                     justify-between items-center`} href={"/chatting/" + chattingRoom.id}
                                        onMouseEnter={() => onMouseEnter(chattingRoom.id)} onMouseLeave={onMouseLeave}>
                                        <span className="p-1">{chattingRoom.name}</span>

                                        {isHovered == chattingRoom.id ?
                                            <DropdownChattingRoom
                                                onSelected={() => onSelected(chattingRoom.id, chattingRoom.name)}
                                                isSelected={isSelected}
                                                offSelected={() => offSelected()}
                                                exitChattingRoom={() => exitChattingRoom(chattingRoom.id)}></DropdownChattingRoom> : <></>}
                                    </Link>
                                    {isSelected == chattingRoom.id ?
                                        <form className="p-0.5 flex justify-between" onSubmit={(e) => modifyChattingRoom(e, chattingRoom.id)}>
                                            <input className="w-3/4 p-0.5 border rounded-sm" value={modifyName}
                                                onChange={handleChange} type="text" />
                                            <button type="submit" className="w-1/4 p-0.5 border rounded-sm">수정</button>
                                        </form> :
                                        <></>
                                    }
                                </>
                            )
                        }
                    </div>
                </div>
                <div className="ml-2 dark:bg-boxdark-2 dark:text-bodydark w-3/4">
                    {loading ? <Loader /> : children}
                </div>
            </div>
        </DefaultLayout>
    );
}
