'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout"
import React, { useEffect, useRef, useState } from "react"
import { MeetingRoom } from "./type";
import api from "@/util/api";
import { Client, IMessage } from "@stomp/stompjs";
import InviteModal from "./inviteModal";
import { useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";



const Meeting = () => {

    const router = useRouter();
    const queryClient = useQueryClient();
    const memberData: any = queryClient.getQueryData(["member"]);
    const [meetingRoom, setMeetingRoom] = useState<MeetingRoom>();
    const myKey = Math.random().toString(36).substring(2, 11); //식별을 위한 key
    const [stompClient, setStompClient] = useState<Client | null>(null);
    const localVideoRef: any = useRef(null);
    let localStream: any = undefined;
    const [pcListMap, setPcListMap] = useState<Map<string, RTCPeerConnection>>(new Map());
    const [otherKeyList, setOtherKeyList] = useState<any[]>([]);
    const remoteStreamRef: any = useRef(null);

    useEffect(() => {
        if (memberData.meetingState.includes("없음")) {
            createMeetingRoom();
        } else if (memberData.meetingState.includes("중")) {
            fetchMeetingRoom();
        } else {
            alert("초대여부를 확인하세요.");
            router.back();
        }
        startCam();

        const stomp = new Client({
            brokerURL: 'ws://localhost:8090/meeting',
            reconnectDelay: 5000,
        });
        stomp.onConnect = () => {
            // 구독 활성화
            stomp.subscribe(
                `/topic/peer/iceCandidate/${myKey}/${meetingRoom?.id}`,
                (iceCandidate) => {
                    const key = JSON.parse(iceCandidate.body).key;
                    const msg = JSON.parse(iceCandidate.body).body;
                    if (msg.type === 'candidate') {
                        const pc: any = pcListMap.get(key);
                        pc.addIceCandidate(new RTCIceCandidate({
                            candidate: msg.candidate,
                            sdpMLineIndex: msg.sdpMLineIndex,
                            sdpMid: msg.sdpMid
                        })).then(() => {
                            console.log("candidate 수신");
                        }).catch((err: any) => {
                            console.error("candidate 수신 에러:", err)
                        });
                    }
                });
            stomp.subscribe(`/topic/peer/offer/${myKey}/${meetingRoom?.id}`,
                (offer) => {
                    const key = JSON.parse(offer.body).key;
                    const msg = JSON.parse(offer.body).body;
                    pcListMap.set(key, createPeerConnection(key));
                    const pc: any = pcListMap.get(key);
                    pc.setRemoteDescription(new RTCSessionDescription({
                        type: msg.type,
                        sdp: msg.sdp
                    })).then(() => {
                        console.log("offer수신/answer전송");
                        sendAnswer(pcListMap.get(key), key);
                    }).catch((err: any) => {
                        console.error('offer 처리 에러:', err);
                    });
                });
            stomp.subscribe(`/topic/peer/answer/${myKey}/${meetingRoom?.id}`,
                (answer) => {
                    const key = JSON.parse(answer.body).key;
                    const msg = JSON.parse(answer.body).body;
                    const pc: any = pcListMap.get(key);
                    pc.setRemoteDescription(new RTCSessionDescription(msg))
                        .then(() => {
                            console.log("answer수신");
                        }).catch((err: any) => {
                            console.error('answer 수신 에러:', err);
                        });
                });
            stomp.subscribe(`/topic/call/key`,
                () => {
                    stomp.publish({
                        destination: `/app/send/key`,
                        body: JSON.stringify(myKey)
                    })
                });
            stomp.subscribe(`/topic/send/key`,
                (msg) => {
                    console.log("key받음 : ", msg);
                    const otherKey = JSON.parse(msg.body);
                    if (myKey != otherKey && otherKeyList.find((key) => key == myKey) == undefined) {
                        otherKeyList.push(otherKey);
                    }
                });
            // 연결된 상태에서 메시지 전송
            if (stomp.connected) {
                console.log("화상회의 연결됨");
            }
        };
        stomp.activate();
        setStompClient(stomp);
        return () => {
            stomp.deactivate();
        };
    }, []);


    //webRTC
    //peer connection 생성
    const createPeerConnection = (key: any) => {
        const peerConnection = new RTCPeerConnection();
        try {
            peerConnection.addEventListener("icecandidate", e => {
                onIceCandidate(e, key);
            });
            peerConnection.addEventListener("track", e => {
                onTrack(e, key);
            });
            if (localStream != undefined) {
                localStream.getTracks().forEach((track: any) => {
                    peerConnection.addTrack(track, localStream);
                    console.log("track 전송!!!")
                });
            }
            console.log('PeerConnection + track :', peerConnection);
            console.log('PeerConnection created');
        } catch (err) {
            console.error('PeerConnection failed:', err);
        }
        return peerConnection;
    }

    //IceCandidate Event
    let onIceCandidate = (e: any, key: any) => {
        if (e.candidate) {
            console.log("ICE Candidate");
            stompClient?.publish({
                destination: `app/peer/iceCandidate/${key}/${meetingRoom?.id}`,
                body: JSON.stringify({ key: myKey, body: e.candidate })
            });
        }
    };
    //Track Event
    let onTrack = (e: any, key: any) => {
        console.log("track!!!!");
        if (document.getElementById(`${key}`) === null) {
            const video = document.createElement('video');
            video.autoplay = true;
            video.controls = true;
            video.id = key;
            video.srcObject = e.streams[0];
            if (remoteStreamRef.current) {
                remoteStreamRef.current.appendChild(video);
            }
        }
    };

    let sendOffer = (peerConnection: any, key: any) => {
        peerConnection.createOffer().then((offer: any) => {
            setLocalAndSendMessage(peerConnection, offer);
            stompClient?.publish({
                destination: `app/peer/offer/${key}/${meetingRoom?.id}`,
                body: JSON.stringify({ key: myKey, body: offer })
            });
            console.log('Send offer');
        });
    };

    let sendAnswer = (peerConnection: any, key: any) => {
        peerConnection.createAnswer().then((answer: any) => {
            setLocalAndSendMessage(peerConnection, answer);
            stompClient?.publish({
                destination: `app/peer/answer/${key}/${meetingRoom?.id}`,
                body: JSON.stringify({ key: myKey, body: answer })
            });
            console.log('Send answer');
        });
    };

    const setLocalAndSendMessage = (peerConnection: any, sessionDescription: any) => {
        peerConnection.setLocalDescription(sessionDescription);
    }

    const handleStartStream = () => {
        console.log(stompClient);
        stompClient?.publish({
            destination: `/app/call/key`,
            body: "key 발송",
        });
        console.log("offer 전송");
        setTimeout(() => {
            console.log(otherKeyList);
            console.log(pcListMap);
            otherKeyList.map((key) => {
                if (!pcListMap.has(key)) {
                    pcListMap.set(key, createPeerConnection(key));
                    sendOffer(pcListMap.get(key), key);
                }
            });
        }, 1000);
    }

    const createMeetingRoom = () => {
        api.post('/api/v1/meetings', { name: "새 회의" })
            .then(response => {
                setMeetingRoom(response.data.data.meetingRoomDto);
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
                setMeetingRoom(response.data.data.meetingRoomDto);
            })
    }

    //캠 연결 및 내 화면 출력
    const startCam = async () => {
        await navigator.mediaDevices.getUserMedia({ video: true, audio: true })
            .then(async (stream) => {
                localStream = stream;
                stream.getAudioTracks()[0].enabled = true;
                localVideoRef.current.srcObject = localStream;
                console.log("캠 연결 성공 localStream:", localStream);
                console.log(stream);
            })
            .catch(err =>
                console.error("캠 연결 에러:", err));
    }
    // const onMute = () => {
    //     navigator.mediaDevices.getUserMedia({ video: true, audio: false })
    //         .then(stream => {
    //             localStream = stream;
    //             localVideoRef.current.srcObject = stream;
    //         })
    //         .catch(err =>
    //             console.error("캠 연결 에러:", err));
    // }




    //초대
    const [modalIsOpen, setModalIsOpen] = useState(false);
    const openModal = () => {
        setModalIsOpen(true);
    }
    const closeModal = () => {
        setModalIsOpen(false);
    }

    //나가기
    const exitMeeting = () => {
        const result = window.confirm("회의에서 나가시겠습니까?");
        if (result) {
            api.patch(`/api/v1/meetings/${meetingRoom?.id}/exit`)
                .then(res => {
                    api.get(`/api/v1/members/me`)
                        .then(res => {
                            if (!res.data.isSuccess) {
                                return alert(res.data.msg);
                            }
                            queryClient.setQueryData(["member"], res.data.data.memberDTO);
                        });
                    alert("회의에서 퇴장하였습니다.");
                    router.push("/chatting");
                })
        }
    }

    return (
        <DefaultLayout>
            {modalIsOpen ? <InviteModal closeModal={closeModal} /> : <></>}
            <main>
                <div className="w-full flex justify-between m-2">
                    <div className="flex flex-col w-1/4 mr-2">
                        <div className="flex h-12 w-full items-center justify-between mb-2">
                            <span className="text-2xl">Meeting</span>
                            <div className="flex items-center">
                                <button className="flex h-10 items-center border p-2 mt-1 rounded-xl flex items-end bg-white hover:bg-gray" onClick={openModal}>찾기 및 초대</button>
                                <button className="flex h-10 items-center ml-2 w-1/8 border rounded-xl mt-1 px-2 bg-white hover:bg-gray"
                                    onClick={exitMeeting}>
                                    <svg
                                        className="fill-current"
                                        width="20"
                                        height="20"
                                        viewBox="0 0 20 22"
                                        fill="none"
                                        xmlns="http://www.w3.org/2000/svg"
                                    >
                                        <path
                                            d="M15.5375 0.618744H11.6531C10.7594 0.618744 10.0031 1.37499 10.0031 2.26874V4.64062C10.0031 5.05312 10.3469 5.39687 10.7594 5.39687C11.1719 5.39687 11.55 5.05312 11.55 4.64062V2.23437C11.55 2.16562 11.5844 2.13124 11.6531 2.13124H15.5375C16.3625 2.13124 17.0156 2.78437 17.0156 3.60937V18.3562C17.0156 19.1812 16.3625 19.8344 15.5375 19.8344H11.6531C11.5844 19.8344 11.55 19.8 11.55 19.7312V17.3594C11.55 16.9469 11.2062 16.6031 10.7594 16.6031C10.3125 16.6031 10.0031 16.9469 10.0031 17.3594V19.7312C10.0031 20.625 10.7594 21.3812 11.6531 21.3812H15.5375C17.2219 21.3812 18.5625 20.0062 18.5625 18.3562V3.64374C18.5625 1.95937 17.1875 0.618744 15.5375 0.618744Z"
                                            fill=""
                                        />
                                        <path
                                            d="M6.05001 11.7563H12.2031C12.6156 11.7563 12.9594 11.4125 12.9594 11C12.9594 10.5875 12.6156 10.2438 12.2031 10.2438H6.08439L8.21564 8.07813C8.52501 7.76875 8.52501 7.2875 8.21564 6.97812C7.90626 6.66875 7.42501 6.66875 7.11564 6.97812L3.67814 10.4844C3.36876 10.7938 3.36876 11.275 3.67814 11.5844L7.11564 15.0906C7.25314 15.2281 7.45939 15.3312 7.66564 15.3312C7.87189 15.3312 8.04376 15.2625 8.21564 15.125C8.52501 14.8156 8.52501 14.3344 8.21564 14.025L6.05001 11.7563Z"
                                            fill=""
                                        />
                                    </svg>
                                    나가기
                                </button>
                            </div>
                        </div>
                    </div>
                    <div className="w-3/4">
                        <header className="bg-blue-700 text-white py-4 px-6 rounded">
                            <h1 className="text-2xl font-bold">{meetingRoom?.name}</h1>
                            <span className="text-sm">{meetingRoom?.members.map((name, index) =>
                                <React.Fragment key={index}>
                                    {index == 0 ? name : `, ${name}`}
                                </React.Fragment>)}
                            </span>
                        </header>
                    </div>
                </div>
                <span className="text-xl">내 화면</span>
                <video id="localStream" ref={localVideoRef} autoPlay playsInline className="display: none w-1/4 border"></video>
                <button className="border" onClick={handleStartStream}>mute</button>
                <div className="flex justify-between">
                    {/*controls 옵션이 있으면 f-screen/pause 가능*/}
                    <div ref={remoteStreamRef} >
                    </div>
                </div>
            </main>
            <div>
                <button className="border m-1 p-1" id="startButton">시작</button>
                <button className="border m-1 p-1" id="callButton">전화</button>
                <button className="border m-1 p-1" id="hangupButton">종료</button>
            </div>


            {/*크로스 브라우징을 위한 코드*/}
            <script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
        </DefaultLayout>
    )
}

export default Meeting;