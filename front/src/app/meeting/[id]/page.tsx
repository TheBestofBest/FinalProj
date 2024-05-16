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
    const myKey = useRef(Math.random().toString(36).substring(2, 11)); // 식별을 위한 key
    const stompClientRef = useRef<Client | null>(null);
    const [isConnected, setIsConnected] = useState(false); // 연결 상태 관리
    const localVideoRef = useRef<HTMLVideoElement | null>(null);
    const localStreamRef = useRef<MediaStream | null>(null);
    const pcListRef = useRef<Map<string, RTCPeerConnection>>(new Map()); // useRef로 관리
    // const pendingCandidates = useRef<Map<string, RTCIceCandidate[]>>(new Map());
    const otherKeyListRef = useRef<string[]>([]);
    const [otherKeyList, setOtherKeyList] = useState<string[]>([]);
    const remoteStreamRef = useRef<HTMLDivElement>(null);
    const isOfferer = useRef(false); // 역할 결정

    useEffect(() => {
        if (memberData?.meetingState.includes("받음")) {
            alert("초대받은 회의가 있습니다.");
            router.back();
        }
        startCam();
        const stomp = new Client({
            brokerURL: 'ws://localhost:8090/meeting',
            reconnectDelay: 10000000,
        });
        stomp.onConnect = () => {
            console.log("myKey:", myKey)
            // 구독 활성화
            stomp.subscribe(
                `/topic/peer/iceCandidate/${myKey.current}/${params.id}`,
                (iceCandidate) => handleIceCandidate(JSON.parse(iceCandidate.body))
            );
            stomp.subscribe(
                `/topic/peer/offer/${myKey.current}/${params.id}`,
                (offer) => {
                    console.log("offer를 받아줘...", offer.body)
                    handleOffer(JSON.parse(offer.body))
                }
            );
            stomp.subscribe(
                `/topic/peer/answer/${myKey.current}/${params.id}`,
                (answer) => handleAnswer(JSON.parse(answer.body))
            );
            stomp.subscribe(`/topic/call/key`,
                () => {
                    stomp.publish({
                        destination: `/app/send/key`,
                        body: JSON.stringify(myKey.current)
                    })
                });
            stomp.subscribe(`/topic/send/key`,
                (msg) => {
                    console.log("otherKey받음 : ", msg.body);
                    const otherKey = JSON.parse(msg.body);
                    if (myKey.current !== otherKey && !otherKeyListRef.current.includes(otherKey)) {
                        otherKeyListRef.current = [...otherKeyListRef.current, otherKey];
                        setOtherKeyList([...otherKeyListRef.current]); // 상태 동기화
                        console.log("keyList : ", otherKeyListRef.current);
                    }
                });
            // 연결된 상태에서 메시지 전송
            console.log("화상회의 연결됨");
            setIsConnected(true);
        };
        stomp.activate();
        stompClientRef.current = stomp;
        console.log("stompClient : ",  stompClientRef.current);
        return () => {
            stomp.deactivate();
        };
    }, [memberData, params.id, router]);

    useEffect(() => {
        if (isConnected &&  stompClientRef.current) {
            handleStartStream();
        }
    }, [isConnected]);

    useEffect(() => {
        if (isConnected) {
            if (isOfferer.current) {
                otherKeyListRef.current.forEach((key) => {
                    if (!pcListRef.current.has(key)) {
                        const pc = createPeerConnection(key);
                        sendOffer(pc, key);
                    }
                });
            }
        }
    }, [otherKeyList, isConnected]);

    useEffect(() => {
        setOtherKeyList(otherKeyListRef.current);
    }, [otherKeyListRef.current]);

    const handleStartStream = () => {
        if ( stompClientRef.current?.connected) {
            console.log("Sending key to start stream");
            stompClientRef.current.publish({
                destination: `/app/call/key`,
                body: "key 발송",
            });
            if (isOfferer.current) {
                otherKeyList.forEach((key) => {
                    if (!pcListRef.current.has(key)) {
                        console.log("offerer 첫 수행")
                        const pc = createPeerConnection(key);
                        sendOffer(pc, key);
                    }
                });
            }
        } else {
            console.error("STOMP client is not connected");
        }
    };

    const handleIceCandidate = ({ key, body }: IceCandidateMessage) => {
        if (body) {
            console.log("ICE Candidate received : [pcListMap]", pcListRef.current);
            const pc = pcListRef.current.get(key);
            if (pc) {
                pc.addIceCandidate(new RTCIceCandidate({ candidate: body.candidate, sdpMLineIndex: body.sdpMLineIndex, sdpMid: body.sdpMid }))
                    .then(() => console.log("Added ICE Candidate"))
                    .catch((error) => console.error("Error adding ICE Candidate:", error));
            } else {
                console.error("PeerConnection not found for key:", key);
                // pendingCandidates.current.set(key, [
                //     ...(pendingCandidates.current.get(key) || []),
                //     body
                // ]);
            }
        }
    };

    const handleOffer = ({ key, body }: OfferAnswerMessage) => {
        console.log("Offer received from key:", key);
        const pc = createPeerConnection(key);
        pc.setRemoteDescription(new RTCSessionDescription({ type: body.type, sdp: body.sdp }))
            .then(() => {
                console.log("Offer set as remote description", body.sdp);
                sendAnswer(pc, key);
            })
            .catch((error) => console.error("Error handling offer:", error));
    };

    const handleAnswer = ({ key, body }: OfferAnswerMessage) => {
        console.log("handleAnswer [pcMap] :", pcListRef.current)
        const pc = pcListRef.current.get(key);
        if (pc) {
            pc.setRemoteDescription(new RTCSessionDescription(body))
                .then(() => console.log("Answer received and set as remote description", body.sdp))
                .catch((error) => console.error("Error handling answer:", error));
        } else {
            console.error("PeerConnection not found for key:", key);
        }
    };

    //peer connection 생성
    const createPeerConnection = (key: string): RTCPeerConnection => {
        console.log("Creating PeerConnection for key:", key);
        // STUN 서버
        // const configuration = {
        //     iceServers: [
        //         { urls: 'stun:stun.l.google.com:19302' },
        //         // 필요한 경우 TURN 서버 추가
        //     ]
        // };
        const peerConnection = new RTCPeerConnection();
        console.log("PeerConnection created:", peerConnection);

        peerConnection.addEventListener("icecandidate", (event) => onIceCandidate(event, key));
        console.log("Added ICE candidate event listener");

        peerConnection.addEventListener("track", (event) => onTrack(event, key));
        console.log("Added track event listener");

        peerConnection.addEventListener("iceconnectionstatechange", () => {
            console.log(`ICE connection state for ${key}:`, peerConnection.iceConnectionState);
        });
        console.log("Added ICE connection state change event listener");

        if (localStreamRef.current) {
            localStreamRef.current.getTracks().forEach((track: MediaStreamTrack) => {
                console.log(`Adding track: ${track.kind}`);
                peerConnection.addTrack(track, localStreamRef.current!);
            });
        }
        console.log("Added local tracks to PeerConnection");

        pcListRef.current.set(key, peerConnection);
        console.log("Added PeerConnection to pcListRef");

        // // Pending candidate 처리
        // if (pendingCandidates.current.has(key)) {
        //     const candidates = pendingCandidates.current.get(key) || [];
        //     candidates.forEach((candidate) => {
        //         peerConnection.addIceCandidate(new RTCIceCandidate(candidate))
        //             .then(() => console.log("Added ICE Candidate from pending"))
        //             .catch((error) => console.error("Error adding pending ICE Candidate:", error));
        //     });
        //     pendingCandidates.current.delete(key);
        // }
        // console.log("Processed pending ICE candidates");

        console.log("pcListRef size:", pcListRef.current.size); // 디버깅 로그 추가
        return peerConnection;
    };

    //IceCandidate Event
    const onIceCandidate = (e: RTCPeerConnectionIceEvent, otherKey: string) => {
        if (e.candidate) {
            console.log("ICE Candidate event for key:", otherKey);
            stompClientRef.current?.publish({
                destination: `/app/peer/iceCandidate/${otherKey}/${params.id}`,
                body: JSON.stringify({ key: myKey.current, body: e.candidate })
            });
        }
    };
    //Track Event
    const onTrack = (e: RTCTrackEvent, key: string) => {
        console.log("Track event for key:", key);
        console.log("Track event for event:", e.streams[0]);
        let videoElement = document.getElementById(key) as HTMLVideoElement | null;
        if (!videoElement) {
            const video = document.createElement('video');
            video.autoplay = true;
            video.controls = true;
            video.id = key;
            video.srcObject = e.streams[0];
            remoteStreamRef.current?.appendChild(video);
        } else {
            videoElement.srcObject = e.streams[0];
        }
    };

    const sendOffer = (peerConnection: RTCPeerConnection, otherKey: string) => {
        peerConnection.createOffer()
            .then((offer) => {
                return peerConnection.setLocalDescription(offer)
                    .then(() => {
                        console.log('Sending offer to key:', otherKey, 'with offer:', offer);
                        stompClientRef.current?.publish({
                            destination: `/app/peer/offer/${otherKey}/${params.id}`,
                            body: JSON.stringify({ key: myKey.current, body: offer })
                        });
                        console.log(`/topic/peer/answer/${myKey.current}/${params.id}`);
                        console.log('Offer sent to key:', otherKey);
                    });
            })
            .catch((error) => console.error('Error creating offer:', error));
    };

    const sendAnswer = (peerConnection: RTCPeerConnection, otherKey: string) => {
        peerConnection.createAnswer()
            .then((answer) => {
                return peerConnection.setLocalDescription(answer)
                    .then(() => {
                        stompClientRef.current?.publish({
                            destination: `/app/peer/answer/${otherKey}/${params.id}`,
                            body: JSON.stringify({ key: myKey.current, body: answer })
                        });
                        console.log(`/app/peer/answer/${otherKey}/${params.id}`);
                        console.log('Answer sent to key:', otherKey);
                    })
                    .catch((error) => console.error('Error send answer:', error));
            })
            .catch((error) => console.error('Error creating answer:', error));
    };


    //캠 연결 및 내 화면 출력
    const startCam = async () => {
        try {
            const stream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
            localStreamRef.current = stream;
            if (localVideoRef.current) {
                localVideoRef.current.srcObject = stream;
            }
            console.log("캠 연결 성공 localStream:", stream);
            // 역할 결정 로직
            if (otherKeyListRef.current.length === 0) {
                // 첫 번째 피어는 offer를 보냅니다.
                isOfferer.current = true;
                console.log("This peer will send the offer.");
            } else {
                // 나머지 피어는 answer를 기다립니다.
                isOfferer.current = false;
                console.log("This peer will wait for the offer.");
            }
        } catch (err) {
            console.error("캠 연결 에러:", err);
        }
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
                    fetchMeetingRoom();
                    if (meetingRoom?.members == null) {
                        deleteMeetingRoom();
                    }
                    api.get(`/api/v1/members/me`)
                        .then(res => {
                            if (!res.data.isSuccess) {
                                return alert(res.data.msg);
                            }
                            queryClient.setQueryData(["member"], res.data.data.memberDTO);
                        });
                    alert("회의에서 퇴장하였습니다.");
                    router.push("/chatting");
                }).catch(err => {
                    console.error("퇴장 error:", err)
                })
        }
    }

    const fetchMeetingRoom = () => {
        api.get(`/api/v1/meetings/${params.id}`)
            .then(response => {
                setMeetingRoom(response.data.data.meetingRoomDto);
            })
    }

    const deleteMeetingRoom = async () => {
        const response = await api.delete(`/api/v1/meetings/${params.id}`);
        if (response.status == 200) {
        } else {
            alert('나가기에 실패했습니다.');
        }
    }

    return (
        <DefaultLayout>
            {modalIsOpen ? <InviteModal closeModal={closeModal} /> : null}
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
                <div className="flex justify-between">
                    <div>
                        <span className="text-xl">내 화면</span>
                        <video id="localStream" ref={localVideoRef} autoPlay playsInline className="display: none w-1/4 border"></video>
                        <button className="border" onClick={handleStartStream}>mute</button>
                    </div>
                    {/*controls 옵션이 있으면 f-screen/pause 가능*/}
                    상대방
                    <div ref={remoteStreamRef} className="flex justify-between" />
                </div>
            </main>

            {/*크로스 브라우징을 위한 코드*/}
            <script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
        </DefaultLayout>
    )
}

export default Meeting;