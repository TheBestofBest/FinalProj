import { MemberType } from "../Member/MemberTypes";

export type ConfirmType = {
  subject: String;
  description: String;
  formData: String;
  formType: ConfirmFormType;
  confirmStatus: ConfirmStatusType;
  confirmRequestMember: MemberType;
  confirmMembers: MemberType[];
  createDate: Date;
};

export type ConfirmFormType = {
  formName: String;
  formDescription: String;
};

export type ConfirmStatusType = {
  statusName: String;
  statusDescription: String;
};
