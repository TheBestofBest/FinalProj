


export interface ChattingRoom {
    id: number;
    name: string;
    members: string[];
}

export interface ChatLog {
    content: string;
    username: string | undefined;
    name: string;
    createdDate: string;
}


export interface Message {
    roomId: number;
    content: string;
    name: string | undefined;
    username: string | undefined;
    isCheck: number | undefined;
}

export interface Member {
    id: number;
    username: string | undefined;
    name: string | undefined;
}