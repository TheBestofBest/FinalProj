'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout"
import React, { useEffect, useRef, useState } from "react"
import { MeetingRoom } from "./type";
import api from "@/util/api";
import { Client } from "@stomp/stompjs";
import InviteModal from "./inviteModal";
import { useQueryClient } from "@tanstack/react-query";


const Meeting = () => {

    const queryClient = useQueryClient();
    const memberData: any = queryClient.getQueryData(["member"]);

    useEffect(() => {
        if (memberData.meetingState.includes("없음")) {
            createChattingRoom();
        }
        startCam();
        wsHandler();
    }, []);


    const [meetingRoom, setMeetingRoom] = useState<MeetingRoom>();
    const createChattingRoom = async () => {
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

    //캠 연결
    const localVideoRef: any = useRef(null);
    let localStream: any;

    const startCam = () => {
        const mediaStreamConstraints = {
            video: true,
            audio: true
        };
        const gotLocalMediaStream = (mediaStream: any) => {
            localStream = mediaStream;
            localVideoRef.current.srcObject = mediaStream;
        };
        const handleLocalMediaStreamError = (error: any) => {
            console.log('navigator.getUserMedia error: ', error);
        };
        const initializeMediaStream = async () => {
            try {
                const mediaStream = await navigator.mediaDevices.getUserMedia(mediaStreamConstraints);
                gotLocalMediaStream(mediaStream);
            } catch (error) {
                handleLocalMediaStreamError(error);
            }
        };
        initializeMediaStream();
        return () => {
            if (localStream) {
                localStream.getTracks().forEach((track: any) => {
                    track.stop();
                });
            }
        };
    }

    //ws연결
    const [stomp, setStomp] = useState<Client>();
    const myKey = Math.random().toString(36).substring(2, 11); //식별을 위한 key
    let pcListMap = new Map();
    let keyList: any[] = [];

    const wsHandler = () => {
        const meetingClient = new Client({
            brokerURL: 'ws://localhost:8090/meeting',
            reconnectDelay: 5000,
        });
        meetingClient.onConnect = () => {
            console.log('웹 소켓 연결이 열렸습니다.');
            meetingClient.subscribe(`/topic/peer/iceCandidate/${myKey}/${meetingRoom?.id}`,
                (iceCandidate) => {
                    const key = JSON.parse(iceCandidate.body).key;
                    const msg = JSON.parse(iceCandidate.body).body;
                    pcListMap.get(key).addIceCandidate(new RTCIceCandidate({
                        candidate: msg.candidate,
                        sdpMLineIndex: msg.sdpMLineIndex,
                        sdpMid: msg.sdpMid
                    }))
                });
            meetingClient.subscribe(`/topic/peer/offer/${myKey}/${meetingRoom?.id}`,
                (offer) => {
                    const key = JSON.parse(offer.body).key;
                    const msg = JSON.parse(offer.body).body;
                    pcListMap.set(key, createPeerConnection(key));
                    pcListMap.get(key).setRemoteDescription(new RTCSessionDescription({
                        type: msg.type,
                        sdp: msg.sdp
                    }))
                    sendAnswer(pcListMap.get(key), key);
                });
            meetingClient.subscribe(`/topic/peer/answer/${myKey}/${meetingRoom?.id}`,
                (answer) => {
                    const key = JSON.parse(answer.body).key;
                    const msg = JSON.parse(answer.body).body;
                    pcListMap.get(key).setRemoteDescription(new RTCSessionDescription(msg));
                });
            meetingClient.subscribe(`/topic/call/key/`,
                (msg) => {
                    meetingClient.publish({
                        destination: `/app/send/key`,
                        body: JSON.stringify(myKey)
                    })
                });
            meetingClient.subscribe(`/topic/send/key/`,
                (msg) => {
                    const key = JSON.parse(msg.body);
                    if (myKey != key && keyList.find((key) => key == myKey) == undefined) {
                        keyList.push(key);
                    }
                });
        };
        meetingClient.activate();
        setStomp(meetingClient);
        meetingClient.onStompError = (frame) => {
            console.error('STOMP 오류:', frame);
        };
    }

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
                localStream.getTrack().forEach((track: any) => {
                    peerConnection.addTrack(track, localStream);
                });
            }
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
            stomp?.publish({
                destination: `app/peer/iceCandidate/${key}/${meetingRoom?.id}`,
                body: JSON.stringify({ key: myKey, body: e.candidate })
            });
        }
    };
    //Track Event
    const remoteStreamRef = useRef<HTMLDivElement>(null);
    let onTrack = (e: any, key: any) => {
        if (document.getElementById(`${key}`) === null) {
            const video = document.createElement('video');
            video.autoplay = true;
            video.controls = true;
            video.id = key;
            video.srcObject = e.streams[0];
            (video: any) => {
                if (remoteStreamRef.current) {
                    remoteStreamRef.current.appendChild(video);
                }
            }
        }
    };

    let sendOffer = (peerConnection: any, key: any) => {
        peerConnection.createOffer().then((offer: any) => {
            setLocalAndSendMessage(peerConnection, offer);
            stomp?.publish({
                destination: `app/peer/offer/${key}/${meetingRoom?.id}`,
                body: JSON.stringify({ key: myKey, body: offer })
            });
            console.log('Send offer');
        });
    };

    let sendAnswer = (peerConnection: any, key: any) => {
        peerConnection.createAnswer().then((answer: any) => {
            setLocalAndSendMessage(peerConnection, answer);
            stomp?.publish({
                destination: `app/peer/answer/${key}/${meetingRoom?.id}`,
                body: JSON.stringify({ key: myKey, body: answer })
            });
            console.log('Send answer');
        });
    };

    const setLocalAndSendMessage = (peerConnection: any, sessionDescription: any) => {
        peerConnection.setLocalDescription(sessionDescription);
    }





    const [modalIsOpen, setModalIsOpen] = useState(false);
    const openModal = () => {
        setModalIsOpen(true);
    }
    const closeModal = () => {
        setModalIsOpen(false);
    }
    return (
        <DefaultLayout>
            {modalIsOpen ? <InviteModal closeModal={closeModal} /> : <></>}
            <main>
                <div>
                    <button className="w-full border rounded mt-1 p-1 bg-white hover:bg-gray" onClick={openModal}>찾기 및 초대</button>
                </div>
                <header className="bg-blue-700 text-white py-4 px-6 rounded">
                    <h1 className="text-2xl font-bold">{meetingRoom?.name}</h1>
                    <span className="text-sm">{meetingRoom?.members.map((name, index) =>
                        <React.Fragment key={index}>
                            {index == 0 ? name : `, ${name}`}
                        </React.Fragment>)}
                    </span>
                </header>
                <div className="flex justify-between">
                    {/*controls 옵션이 있으면 f-screen/pause 가능*/}
                    <video id="localStream" ref={localVideoRef} autoPlay playsInline controls className="display: none;"></video>
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