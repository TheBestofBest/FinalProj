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

    return (
        <DefaultLayout>
            <div className="w-full flex justify-between">
                <div className="flex flex-col w-1/4 mr-2">
                    <div className="flex w-full justify-between">
                        <span>Chatting</span>
                        <button>+</button>
                    </div>
                    <div>
                        <button className="w-full border rounded mt-1 p-1">찾기 및 초대</button>
                    </div>
                    <div className="mt-1">
                        {isEmpty ? <></> :
                            chattingRooms.map((chattingRoom: ChattingRoom) =>
                                <Link className="block w-full border rounded p-1 mt-1" href={"/chatting/" + chattingRoom.id}>{chattingRoom.name}</Link>
                            )
                        }

                    </div>
                </div>
                <div className="dark:bg-boxdark-2 dark:text-bodydark w-3/4">
                    {loading ? <Loader /> : children}
                </div>
            </div>
        </DefaultLayout>
    );
}
