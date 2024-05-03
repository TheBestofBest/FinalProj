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
import { useRouter } from "next/navigation";
import { QueryClient, useQueryClient } from "@tanstack/react-query";

export default function ChattingLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    const router = useRouter();


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
            offSelected();
            router.push("/chatting");
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
            router.push("/chatting");
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

    return (
        <DefaultLayout>
            <div className="w-full flex justify-between font-bold">
                {modalIsOpen ? <InviteModal closeModal={closeModal} /> : <></>}
                <div className="flex flex-col w-1/4 mr-2">
                    <div className="flex w-full justify-between">
                        <span>Chatting</span>
                        <button className="flex items-end" onClick={createChattingRoom}>
                            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" className="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M12 9v6m3-3H9m12 0a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z" />
                            </svg>

                        </button>
                    </div>
                    <div>
                        <button className="w-full border rounded mt-1 p-1 hover:bg-gray-200" onClick={openModal}>찾기 및 초대</button>
                    </div>
                    <div className="mt-1">
                        {isEmpty ? <></> :
                            chattingRooms.map((chattingRoom: ChattingRoom) =>
                                <>
                                    <Link className={`flex w-full border rounded p-1.5 mt-1
                                     justify-between items-center`} href={"/chatting/" + chattingRoom.id}
                                        onMouseEnter={() => onMouseEnter(chattingRoom.id)} onMouseLeave={onMouseLeave}>
                                        <span className="p-1">{chattingRoom.name}</span>
                                        <span>{chattingRoom.members}</span>
                                        {chattingRoom.members == null?<>asdasd</>:<></>}
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
