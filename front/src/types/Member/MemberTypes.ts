export type MemberType = {
  id: number; // id
  department: DepartmentType; // 부서
  grade: GradeType; // 직급
  username: String; // 로그인 아이디
  password: String; // 비밀번호
  email: String; // 이메일
  memberNumber: number; // 사원번호
  name: String; // 사원명
  assignedTask: String; // 담당 업무
  workStatus: String; // 근무 상태 ( 온라인, 오프라인, 부재중 )
  extensionNumber: String; // 내선 전화 번호
  phoneNumber: String; // 개인 연락처
  statusMessage: String; // 상태메세지
};

export type DepartmentType = {
  code: number;
  name: String;
};
export type GradeType = {
  code: number;
  name: String;
};
