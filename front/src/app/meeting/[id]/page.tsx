'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout"
import React, { useEffect, useRef, useState } from "react"
import { MeetingRoom } from "../type";
import api from "@/util/api";
import { Client, IMessage } from "@stomp/stompjs";
import InviteModal from "../inviteModal";
import { useQueryClient } from "@tanstack/react-query";
import { useParams, useRouter } from "next/navigation";
import { Params } from "next/dist/shared/lib/router/utils/route-matcher";

interface IceCandidateMessage {
    key: string;
    body: RTCIceCandidate;
}

interface OfferAnswerMessage {
    key: string;
    body: RTCSessionDescriptionInit;
}


const Meeting = () => {

    const router = useRouter();
    const params: Params = useParams();
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

    }, [])

    useEffect(() => {
        if (memberData.meetingState.includes("받음")) {
            alert("초대받은 회의가 있습니다.");
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
                `/topic/peer/iceCandidate/${params.id}`,
                (iceCandidate) => handleIceCandidate(JSON.parse(iceCandidate.body))
            );
            stomp.subscribe(
                `/topic/peer/offer/${params.id}`,
                (offer) => handleOffer(JSON.parse(offer.body))
            );
            stomp.subscribe(
                `/topic/peer/answer/${params.id}`,
                (answer) => handleAnswer(JSON.parse(answer.body))
            );
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
                    if (myKey !== otherKey && !otherKeyList.includes(otherKey)) {
                        setOtherKeyList((prevKeys) => [...prevKeys, otherKey]);
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
    const handleIceCandidate = ({ key, body: candidate }: IceCandidateMessage) => {
        if (candidate) {
            console.log("ICE Candidate received");
            const pc = pcListMap.get(key);
            if (pc) {
                pc.addIceCandidate(new RTCIceCandidate(candidate))
                    .then(() => console.log("Added ICE Candidate"))
                    .catch((error) => console.error("Error adding ICE Candidate:", error));
            }
        }
    };

    const handleOffer = ({ key, body: offer }: OfferAnswerMessage) => {
        const pc = createPeerConnection(key);
        pc.setRemoteDescription(new RTCSessionDescription(offer))
            .then(() => {
                console.log("Offer received, sending answer");
                sendAnswer(pc, key);
            })
            .catch((error) => console.error("Error handling offer:", error));
    };

    const handleAnswer = ({ key, body: answer }: OfferAnswerMessage) => {
        console.log(pcListMap)
        const pc = pcListMap.get(key);
        if (pc) {
            pc.setRemoteDescription(new RTCSessionDescription(answer))
                .then(() => console.log("Answer received"))
                .catch((error) => console.error("Error handling answer:", error));
        }
    };

    //peer connection 생성
    const createPeerConnection = (key: string): RTCPeerConnection => {
        console.log("create pc, [key] : ", key);
        const peerConnection = new RTCPeerConnection();
        peerConnection.addEventListener("icecandidate", (event) => onIceCandidate(event, key));
        peerConnection.addEventListener("track", (event) => onTrack(event, key));
        if (localStream) {
            localStream.getTracks().forEach((track: MediaStreamTrack) => peerConnection.addTrack(track, localStream));
        }
        setPcListMap((prevMap) => new Map(prevMap.set(key, peerConnection)));
        return peerConnection;
    }

    //IceCandidate Event
    const onIceCandidate = (e: RTCPeerConnectionIceEvent, key: string) => {
        if (e.candidate) {
            console.log("ICE Candidate event");
            stompClient?.publish({
                destination: `/app/peer/iceCandidate/${params.id}`,
                body: JSON.stringify({ key: myKey, body: e.candidate })
            });
        }
    };
    //Track Event
    const onTrack = (e: RTCTrackEvent, key: string) => {
        console.log("Track event");
        const videoElement = document.getElementById(key);
        if (!videoElement) {
            const video = document.createElement('video');
            video.autoplay = true;
            video.controls = true;
            video.id = key;
            video.srcObject = e.streams[0];
            remoteStreamRef.current?.appendChild(video);
        }
    };

    const sendOffer = (peerConnection: RTCPeerConnection, key: string) => {
        peerConnection.createOffer()
            .then((offer) => {
                return peerConnection.setLocalDescription(offer)
                    .then(() => {
                        stompClient?.publish({
                            destination: `/app/peer/offer/${params.id}`,
                            body: JSON.stringify({ key: myKey, body: offer })
                        });
                        console.log('Offer sent');
                    });
            })
            .catch((error) => console.error('Error creating offer:', error));
    };

    const sendAnswer = (peerConnection: RTCPeerConnection, key: string) => {
        peerConnection.createAnswer()
            .then((answer) => {
                return peerConnection.setLocalDescription(answer)
                    .then(() => {
                        stompClient?.publish({
                            destination: `/app/peer/answer/${params.id}`,
                            body: JSON.stringify({ key: myKey, body: answer })
                        });
                        console.log('Answer sent');
                        return;
                    })
                    .catch((error) => console.error('Error send answer:', error));
            })
            .catch((error) => console.error('Error creating answer:', error));
    };


    const handleStartStream = () => {
        stompClient?.publish({
            destination: `/app/call/key`,
            body: "key 발송",
        });
        setTimeout(() => {
            otherKeyList.forEach((key) => {
                if (!pcListMap.has(key)) {
                    const pc = createPeerConnection(key);
                    sendOffer(pc, key);
                }
            });
        }, 1000);
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
            api.patch(`/api/v1/meetings/${params.id}/exit`)
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
                    <div ref={remoteStreamRef} />
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