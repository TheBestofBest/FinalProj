


export interface ChattingRoom {
    id: number;
    name: string;
    members: string[];
}


export interface Message {
    roomId: number;
    author: string;
    content: string;
}