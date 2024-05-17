'use client'

import Link from "next/link";
import Image from "next/image";
import { Chat } from "@/types/chat";
import { useRouter } from "next/navigation";
import { useQueryClient } from "@tanstack/react-query";
import React, { useEffect, useState } from "react";
import { ChattingRoom } from "./chatting/type";
import api from "@/util/api";


const Message = () => {

  const router = useRouter();
  const queryClient = useQueryClient();
  const memberData: any = queryClient.getQueryData(["member"]);
  const [loading, setLoading] = useState<boolean>(true);
  const [chattingRooms, setChattingRooms] = useState<ChattingRoom[]>([]);
  const [isEmpty, setIsEmpty] = useState<boolean>();

  useEffect(() => {
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
    <div className="col-span-12 rounded-sm border border-stroke bg-white py-6 shadow-default dark:border-strokedark dark:bg-boxdark xl:col-span-4">
      <h4 className="px-7.5 text-xl font-semibold border-[#eee] text-black dark:text-white border-b pb-4">
        채팅방
      </h4>
      <div className="p-2 h-125 overflow-y-auto">
        {isEmpty ? <></> :
          <>
            {chattingRooms.map((chattingRoom: ChattingRoom) => (
              <Link
                href={"/chatting/" + chattingRoom.id}
                className="border-b border-[#eee] flex items-center gap-5 px-7.5 py-3 hover:bg-gray-3 dark:hover:bg-meta-4"
                key={chattingRoom.id}
              >
                <div className="px-1 flex flex-1 items-center justify-between">
                  <div>
                    <h5 className="font-medium text-black dark:text-white">
                      {chattingRoom.name}
                    </h5>
                    <p>
                      <span className="text-sm">{chattingRoom?.members.map((name, index) =>
                        <React.Fragment key={index}>
                          {index == 0 ? name : `, ${name}`}
                        </React.Fragment>)}
                      </span>
                      {/* <span className="text-xs"> . {chat.time} min</span> */}
                    </p>
                  </div>
                  {/* {chat.textCount !== 0 && (
              <div className="flex h-6 w-6 items-center justify-center rounded-full bg-primary">
                <span className="text-sm font-medium text-white">
                  {" "}
                  {chat.textCount}
                </span>
              </div>
            )} */}
                </div>
              </Link>
            ))}
          </>
        }

      </div>
    </div>
  );
};

export default Message;
