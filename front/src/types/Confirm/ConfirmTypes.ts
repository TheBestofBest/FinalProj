import { MemberType } from "../Member/MemberTypes";
import { ConfirmFormVactionType } from "./ConfirmFormTypes";

export type ConfirmType = {
  id: number;
  subject: string;
  description: string;
  formData: string;
  formTypeDTO: ConfirmFormType;
  confirmStatusDTO: ConfirmStatusType;
  confirmRequestMember: MemberType;
  confirmMembers: MemberType[];
  createDate: Date;
  confirmStepCounter: number;
};

export type ConfirmFormType = {
  formName: string;
  formDescription: string;
};

export type ConfirmStatusType = {
  statusName: string;
  statusDescription: string;
};
