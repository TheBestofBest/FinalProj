'use client'

import React, { useEffect, useState } from "react";
import styles from "../../components/Chat/Background.module.css"
import api from "@/util/api";
import { useRouter } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";


const InviteModal = ({ closeModal }: any) => {

    const queryClient = useQueryClient();
    const memberData: any = queryClient.getQueryData(["member"]);
    const [username, setUsername] = useState<string>();
    const [roomId, setRoomId] = useState<number>();

    useEffect(() => {
        const state = memberData.meetingState.split('번');
        setRoomId(parseInt(state[0]));
    }, [])

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setUsername(e.target.value);
        console.log(e.target.value);
    };

    const invite = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (roomId != null) {
            const response = await api.patch(`/api/v1/meetings/${roomId}/invite`, { username: username });
            if (response.status == 200) {
                alert(`[${username}] 님을 초대하였습니다.`);
                setUsername("");
                closeModal();
            } else {
                alert('초대에 실패했습니다.');
            }
        }
    }

    return (
        <div className="h-screen w-full fixed left-0 top-0 flex justify-center items-center bg-black bg-opacity-70 text-center">
            <div className="bg-white w-10/12 md:w-1/3">
                <div className={`${styles.background} border-b px-4 py-2 flex justify-end items-start h-32`}>
                    <button onClick={closeModal}>
                        <svg
                            className="w-6 h-6"
                            fill="none"
                            stroke="currentColor"
                            viewBox="0 0 24 24"
                            xmlns="http://www.w3.org/2000/svg"
                        >
                            <path
                                strokeLinecap="round"
                                strokeLinejoin="round"
                                strokeWidth="2"
                                d="M6 18L18 6M6 6l12 12"
                            ></path>
                        </svg>
                    </button>
                </div>
                <div>
                    <div className="flex p-4">
                        <span className="font-bold">사람 찾기 및 초대</span>
                    </div>
                    <form className=" m-2 p-2 flex justify-between" onSubmit={(e) => invite(e)}>
                        <input className="px-1 border rounded" name="username" type="text" onChange={handleChange} />
                        <button type="submit" className="border rounded px-2 py-1">초대</button>
                    </form>
                </div>
            </div>
        </div >
    )
}

export default InviteModal;