'use client'

import DefaultLayout from "@/components/Layouts/DefaultLayout"
import { useEffect, useRef } from "react"


const Meeting = () => {

    const localVideoRef = useRef(null);
    let localStream;

    useEffect(() => {
        const mediaStreamConstraints = { video: true };

        const gotLocalMediaStream = (mediaStream) => {
            localStream = mediaStream;
            localVideoRef.current.srcObject = mediaStream;
        };

        const handleLocalMediaStreamError = (error) => {
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
                localStream.getTracks().forEach(track => {
                    track.stop();
                });
            }
        };
    }, []);

    return (
        <DefaultLayout>
            <main>
                <title>Realtime communication with WebRTC</title>
                <div className="flex justify-between">
                    <video id="localVideo" ref={localVideoRef} autoPlay playsInline></video>
                    <video id="remoteVideo" ref={localVideoRef} autoPlay playsInline></video>
                </div>
            </main>
            <div>
                <button className="border m-1 p-1" id="startButton">Start</button>
                <button className="border m-1 p-1" id="callButton">Call</button>
                <button className="border m-1 p-1" id="hangupButton">Hang Up</button>
            </div>
            <script src="https://webrtc.github.io/adapter/adapter-latest.js"></script>
        </DefaultLayout>
    )
}

export default Meeting;