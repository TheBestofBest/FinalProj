'use client'

import React, { ChangeEvent, useEffect, useRef, useState } from "react";
import styles from "../../components/Chat/Background.module.css"
import { ChattingRoom } from "./type";
import api from "@/util/api";
import { useRouter } from "next/navigation";
import { MemberType } from "@/types/Member/MemberTypes";


const InviteModal = ({ closeModal }: any) => {

    const router = useRouter();

    const [chattingRooms, setChattingRooms] = useState<ChattingRoom[]>([]);
    const [chattingRoomId, setChattingRoomId] = useState<number | null>(null);
    const [username, setUsername] = useState<string>();
    const [keyword, setKeyword] = useState("");
    const [members, setMembers] = useState<MemberType[]>([]);



    const fetchChattingRooms = () => {
        api.get('/api/v1/chats')
            .then(response => {
                setChattingRooms(response.data.data.chattingRoomDtoList);
            }).catch(err => {
            })
    }

    const handleCheck = (e: any, id: number) => {
        e.target.checked ? setChattingRoomId(id) : setChattingRoomId(null);
        console.log(chattingRoomId)
    };


    const invite = async (selectedName: string | null) => {
        if (chattingRoomId != null) {
            const response = await api.patch(`/api/v1/chats/${chattingRoomId}/invite`, { username: selectedName });
            if (response.status == 200) {
                alert(`[${selectedName}] 님을 초대하였습니다.`);
                setUsername("");
                closeModal();
                router.push(`/chatting/${chattingRoomId}`);
            } else {
                alert('초대에 실패했습니다.');
            }
        }
    }

    
    const changeSearchWordHandler = (event: ChangeEvent<HTMLInputElement>) => {
        event.preventDefault();

        setKeyword(event.target.value);
        console.log(event.target.value);
    };
    
    useEffect(() => {
        fetchChattingRooms();
    }, [])

    const searchMembers = async () => {
        if (keyword === "") {
            return;
        }
        const response = await api.get(`api/v1/members/search?keyword=${keyword}`);
        setMembers(response.data.data.memberDTOs);
        console.log(members);
    };
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
                    <div className="h-48 px-6 overflow-y-auto">
                        {chattingRooms.map((chattingRoom: ChattingRoom) =>
                            <div className="p-2 border rounded m-2 flex justify-between items-center" key={chattingRoom.id}>
                                <div className="flex justify-between items-center">
                                    <input className="w-5 h-5" type="checkbox" onChange={(e) => handleCheck(e, chattingRoom.id)} />
                                    <span className="ml-1 p-1">{chattingRoom.name}</span>
                                </div>
                                <div className="flex justify-end text-xs w-28 ">
                                    <span className="truncate">{chattingRoom?.members.map((name, index) =>
                                        <React.Fragment key={index}>
                                            {index == 0 ? name : `, ${name}`}
                                        </React.Fragment>)}
                                    </span>
                                </div>
                            </div>
                        )}
                    </div>
                    {/* <form className=" m-2 p-2 flex justify-between" onSubmit={(e) => invite(e, chattingRoomId)}>
                        <input className="px-1 border rounded" name="username" type="text" onChange={handleChange} />
                        <button type="submit" className="border rounded px-2 py-1">초대</button>
                    </form> */}
                    <div className="px-6 ">
                        <div className="relative">
                            <div className="pointer-events-none absolute inset-y-0 start-0 flex items-center ps-3">
                                <svg
                                    className="text-gray-500 dark:text-gray-400 h-4 w-4"
                                    aria-hidden="true"
                                    xmlns="http://www.w3.org/2000/svg"
                                    fill="none"
                                    viewBox="0 0 20 20"
                                >
                                    <path
                                        stroke="currentColor"
                                        stroke-linecap="round"
                                        stroke-linejoin="round"
                                        stroke-width="2"
                                        d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"
                                    />
                                </svg>
                            </div>
                            <input
                                type="search"
                                id="default-search"
                                className="text-gray-900 border-gray-300 bg-gray-50 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 block w-full rounded-lg border p-4 ps-10 text-sm focus:border-blue-500 focus:ring-blue-500 dark:text-white dark:focus:border-blue-500 dark:focus:ring-blue-500"
                                placeholder="Type the name..."
                                onChange={changeSearchWordHandler}
                            />
                            <button
                                className="absolute bottom-2.5 end-2.5 rounded-lg bg-blue-700 px-4 py-2 text-sm font-medium text-white hover:bg-blue-800 focus:outline-none focus:ring-4 focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
                                onClick={(event) => {
                                    event.preventDefault(); // 기본 동작 방지
                                    searchMembers(); // searchMembers 함수 호출
                                }}
                            >
                                검색
                            </button>
                        </div>
                    </div>
                    <div className="max-h-48 px-6 pb-2 overflow-y-auto ">
                        <div className="relative mt-2 overflow-x-auto border border-slate-300 px-1">
                            <table className="text-gray-500 dark:text-gray-400 w-full text-left text-sm rtl:text-right">
                                <thead className="text-gray-700 bg-gray-50 dark:bg-gray-700 dark:text-gray-400 border-b border-slate-300 text-xs uppercase">
                                    <tr>
                                        <th scope="col" className="px-6 py-3">
                                            이름
                                        </th>
                                        <th scope="col" className="px-6 py-3">
                                            직급
                                        </th>
                                        <th scope="col" className="px-6 py-3">
                                            부서
                                        </th>
                                        <th scope="col" className="px-6 py-3">
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {members?.map((member) => (
                                        <tr
                                            key={member.id}
                                            className="dark:bg-gray-800 dark:border-gray-200 border-b bg-white"
                                        >
                                            <th
                                                scope="row"
                                                className="text-gray-900 whitespace-nowrap px-6 py-3 font-medium dark:text-white"
                                            >
                                                {member.name}
                                            </th>
                                            <td className="px-6 py-3">{member.grade.name}</td>
                                            <td className="px-6 py-3">{member.department.name}</td>
                                            <td className="py-3">
                                                <button className=" border rounded px-2 py-1 hover:bg-zinc-200" onClick={() => invite(member.username)}>초대</button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div >
    )
}

export default InviteModal;