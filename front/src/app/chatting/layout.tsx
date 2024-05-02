"use client";
import "jsvectormap/dist/css/jsvectormap.css";
import "flatpickr/dist/flatpickr.min.css";
import "@/css/satoshi.css";
import "@/css/style.css";
import React, { useEffect, useState } from "react";
import Loader from "@/components/common/Loader";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import Link from "next/link";
import { ChattingRoom } from "./type";
import api from "@/util/api";
import Image from "next/image";
import CreateBtn from "../../../public/images/chatiing/createButton.png"
import DropdownChattingRoom from "./DropdownChattingRoom";
import InviteModal from "./inviteModal";

export default function ChattingLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    const [loading, setLoading] = useState<boolean>(true);
    const [chattingRooms, setChattingRooms] = useState<ChattingRoom[]>([]);
    const [isEmpty, setIsEmpty] = useState<boolean>();

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
                            <Image className="w-5" src={CreateBtn} alt=""></Image>
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
                                        {isHovered == chattingRoom.id ?
                                            <DropdownChattingRoom
                                                onSelected={() => onSelected(chattingRoom.id, chattingRoom.name)}
                                                isSelected={isSelected}
                                                offSelected={() => offSelected()}></DropdownChattingRoom> : <></>}
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
