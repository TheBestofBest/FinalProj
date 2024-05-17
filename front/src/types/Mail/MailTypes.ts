import { MemberType } from "../Member/MemberTypes";

export type MailTypes = {
    id: number;
    title: string;
    content: string;
    is_Read: boolean;
    sender: MemberType;
    receiver: MemberType;
    reference: MemberType;
    sendDate: Date;
    receiveDate: Date;
  };