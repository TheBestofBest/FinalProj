export type MemberType = {
  id: number; // id
  department: DepartmentType; // 부서
  grade: GradeType; // 직급
  username: string; // 로그인 아이디
  password: string; // 비밀번호
  email: string; // 이메일
  memberNumber: number; // 사원번호
  name: string; // 사원명
  assignedTask: string; // 담당 업무
  workStatus: string; // 근무 상태 ( 온라인, 오프라인, 부재중 )
  extensionNumber: string; // 내선 전화 번호
  phoneNumber: string; // 개인 연락처
  statusMessage: string; // 상태메세지
};

export type DepartmentType = {
  code: number;
  name: string;
};
export type GradeType = {
  code: number;
  name: string;
};
