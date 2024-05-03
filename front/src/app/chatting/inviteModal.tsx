'use client'

import styles from "./Background.module.css"


const InviteModal = ({ closeModal }: { closeModal: () => void }) => {

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
                    <form action="">
                        <input type="text" />
                        <button>생성</button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default InviteModal;