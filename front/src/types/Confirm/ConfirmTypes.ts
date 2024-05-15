import { MemberType } from "../Member/MemberTypes";

export type ConfirmType = {
  id: number;
  subject: string;
  description: string;
  formData: string;
  formType: ConfirmFormType;
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
