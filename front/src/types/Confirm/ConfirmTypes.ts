import { MemberType } from "../Member/MemberTypes";

export type ConfirmType = {
  subject: string;
  description: string;
  formData: string;
  formType: ConfirmFormType;
  confirmStatus: ConfirmStatusType;
  confirmRequestMember: MemberType;
  confirmMembers: MemberType[];
  createDate: Date;
};

export type ConfirmFormType = {
  formName: string;
  formDescription: string;
};

export type ConfirmStatusType = {
  statusName: string;
  statusDescription: string;
};
