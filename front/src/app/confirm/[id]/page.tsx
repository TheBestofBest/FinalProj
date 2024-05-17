"use client";
import VacationForm from "@/components/ConfirmForm/VacationForm";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import HorizontalLinearAlternativeLabelStepper from "@/components/Stepper/Stepper";
import { ConfirmFormVactionType } from "@/types/Confirm/ConfirmFormTypes";
import { ConfirmFormType, ConfirmType } from "@/types/Confirm/ConfirmTypes";
import { MemberType } from "@/types/Member/MemberTypes";
import api from "@/util/api";
import { useQueryClient } from "@tanstack/react-query";
import Link from "next/link";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
export default function ConfirmDetailPage() {
  // 모달 버튼 클릭 유무를 저장할 state
  const [showModal, setShowModal] = useState(false);
  const params = useParams();
  const [formData, setFormData] = useState({});
  const [confirm, setConfirm] = useState<ConfirmType>({
    id: 0,
    subject: "",
    description: "",
    formData: "",
    formTypeDTO: {
      formName: "",
      formDescription: "",
    },
    confirmStatusDTO: {
      statusName: "",
      statusDescription: "",
    },
    confirmRequestMember: {
      id: 0, // id
      department: {
        code: 0,
        name: "",
      }, // 부서
      grade: {
        code: 0,
        name: "",
      }, // 직급
      username: "", // 로그인 아이디
      password: "", // 비밀번호
      email: "", // 이메일
      memberNumber: 0, // 사원번호
      name: "", // 사원명
      assignedTask: "", // 담당 업무
      workStatus: "", // 근무 상태 ( 온라인, 오프라인, 부재중 )
      extensionNumber: "", // 내선 전화 번호
      phoneNumber: "", // 개인 연락처
      statusMessage: "",
    },
    confirmMembers: [], // 빈 배열로 초기화
    createDate: new Date(),
    confirmStepCounter: 0,
  });
  const queryClient = useQueryClient();
  const member = queryClient.getQueryData<MemberType>(["member"]);
  const router = useRouter();
  const [jsonObject, setJsonObject] = useState<ConfirmFormVactionType>({
    content: "",
    startDate: new Date(),
    endDate: new Date(),
  });

  useEffect(() => {
    try {
      // JSON 문자열이 올바른지 확인하고 객체로 변환
      if (confirm.formData && confirm.formData.trim()) {
        const parsedObject = JSON.parse(confirm.formData);
        setJsonObject(parsedObject);
        console.log(jsonObject);
      } else {
        console.error("JSON 문자열이 비어 있습니다.");
      }
    } catch (error) {
      console.error("JSON 파싱 오류:", error);
    }
  }, []);

  const deleteConfirm = async () => {
    await api.delete(`/api/v1/confirms/${params.id}`);
    router.push(`/confirm`);
  };
  const handleDelete = () => {
    const isConfirmed = window.confirm("정말로 삭제하시겠습니까?");

    if (isConfirmed) {
      deleteConfirm();
    }
  };

  const getConfirm = async () => {
    const response = await api.get(`/api/v1/confirms/${params.id}`);
    setConfirm(response.data.data.confirmDTO);
    console.log(confirm);
  };
  useEffect(() => {
    getConfirm();
  }, []);

  const confirmConfirm = async () => {
    await api.patch(`/api/v1/confirms/${params.id}/change-counter`);
    router.push(`/confirm`);
  };
  const handleConfirm = () => {
    const isConfirmed = window.confirm("승인 하시겠습니까?");

    if (isConfirmed) {
      confirmConfirm();
    }
  };

  const rejectConfirm = async () => {
    await api.patch(`/api/v1/confirms/${params.id}/reject`);
    router.push(`/confirm`);
  };
  const handleReject = () => {
    const isConfirmed = window.confirm("반려 하시겠습니까?");

    if (isConfirmed) {
      rejectConfirm();
    }
  };

  const clickModal = () => setShowModal(!showModal);
  return (
    <DefaultLayout>
      <div className="h-full w-full overflow-y-auto">
        <div className="rounded-sm border border-stroke bg-white px-2 pb-2.5 pt-4.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
          <div className="flex max-w-full flex-col items-center justify-center overflow-x-auto text-neutral-950 dark:text-neutral-100">
            {/* 클릭 시 결재 메인 페이지로 이동 */}
            <div className="mb-3 mt-3 flex w-full justify-start">
              <Link
                href="/confirm"
                type="button"
                className="font-base mx-1 mb-2  me-2 rounded-lg bg-gradient-to-r from-cyan-400 via-cyan-500 to-cyan-600 px-5 py-2.5 text-center text-base text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-cyan-300 dark:focus:ring-cyan-800"
              >
                뒤로가기
              </Link>
            </div>
            <div className="mb-3 min-w-125 max-w-3xl">
              {/* a Subject */}
              <div className="w-full items-center">
                <div className="items-center text-3xl font-bold">
                  {confirm?.subject}
                </div>
                <div className="text-lg font-bold text-slate-400">
                  양식: {confirm?.formTypeDTO?.formName}
                </div>
              </div>
              {/* b table: 결재 신청자, 기안일, 부서, 직급 ,, 우측에는 승인 진행도  */}
              <div className="relative mb-3 mt-2 flex w-full justify-between overflow-x-auto ">
                <table className="text-gray-500 dark:text-gray-400 min-w-400 border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-bold dark:text-white"
                      >
                        결재 기안자
                      </th>
                      <td className="px-6 py-4">
                        {confirm?.confirmRequestMember.name}
                      </td>
                    </tr>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-bold dark:text-white"
                      >
                        기안일
                      </th>
                      <td className="px-6 py-4">
                        {" "}
                        {new Date(confirm.createDate).toLocaleDateString(
                          "ko-KR",
                          {
                            year: "numeric",
                            month: "2-digit",
                            day: "2-digit",
                          },
                        )}
                      </td>
                    </tr>
                  </tbody>
                </table>
                <div className="dark:text-white">
                  <HorizontalLinearAlternativeLabelStepper
                    activeStep={confirm?.confirmStepCounter}
                    confirmMembers={confirm?.confirmMembers}
                  />
                </div>
              </div>
              {/* c table: 결재 간략설명 */}
              <div className="relative mt-2 flex max-w-full justify-between overflow-x-auto shadow-md">
                <table className="text-gray-500 dark:text-gray-400 min-w-full max-w-[600px] border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 max-w-full border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 overflow-wrap break-word   border px-6 py-4 font-medium dark:text-white"
                      >
                        {confirm?.description}
                      </th>
                    </tr>
                  </tbody>
                </table>
                <div></div>
              </div>
              {/* d table:  상세 내용 테이블 => 양식별 component 투입하기 */}
              <div className="relative mt-2 flex w-full justify-between overflow-x-auto shadow-md">
                <table className="text-gray-500 dark:text-gray-400 w-full border text-left text-sm rtl:text-right">
                  <tbody>
                    <tr className="border-gray-200 dark:border-gray-700 border-b">
                      <th
                        scope="row"
                        className="text-gray-900 bg-gray-50 dark:bg-gray-800 whitespace-nowrap border px-6 py-4 font-medium dark:text-white"
                      >
                        <span>상세내용: {jsonObject?.content}</span>
                        <br />
                        <br />
                        <span>
                          휴가 시작일:{" "}
                          {new Date(jsonObject.startDate).toLocaleDateString(
                            "ko-KR",
                            {
                              year: "numeric",
                              month: "2-digit",
                              day: "2-digit",
                            },
                          )}
                        </span>
                        <br />
                        <br />
                        <span>
                          휴가 종료일:{" "}
                          {new Date(jsonObject.endDate).toLocaleDateString(
                            "ko-KR",
                            {
                              year: "numeric",
                              month: "2-digit",
                              day: "2-digit",
                            },
                          )}
                        </span>
                      </th>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>

          <div className="mb-3 mt-3 flex w-full justify-center">
            {/* 수정,삭제: 기안자에게만 보이게 하기 */}
            {confirm?.confirmRequestMember?.name === member?.name &&
              confirm.confirmStatusDTO?.statusName === "결재 처리중" && (
                <div>
                  <button
                    type="button"
                    className="mb-2 me-2 rounded-lg bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 px-5 py-2.5 text-center text-lg font-bold  text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-800"
                  >
                    수정
                  </button>
                  <button
                    type="button"
                    className="focus:ring-black-300 mb-2 me-2 rounded-lg bg-gradient-to-r from-slate-900 via-slate-500 to-slate-600 px-5 py-2.5 text-center text-lg font-bold text-white hover:bg-slate-900 hover:bg-gradient-to-br focus:outline-none focus:ring-4 dark:focus:ring-slate-800"
                    onClick={handleDelete}
                  >
                    삭제
                  </button>
                </div>
              )}
            {/* 승인, 반려: 결재 승인자에게만 보이게하기 */}
            {confirm?.confirmMembers[confirm?.confirmStepCounter]?.name ===
              member?.name &&
              confirm?.confirmStatusDTO?.statusName === "결재 처리중" && (
                <div>
                  <button
                    type="button"
                    className="mb-2 me-2 rounded-lg bg-gradient-to-r from-blue-500 via-blue-600 to-blue-700 px-5 py-2.5 text-center text-lg font-bold  text-white hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-blue-300 dark:focus:ring-blue-800"
                    onClick={handleConfirm}
                  >
                    승인
                  </button>
                  <button
                    type="button"
                    className="focus:ring-black-300 mb-2 me-2 rounded-lg bg-gradient-to-r from-slate-900 via-slate-500 to-slate-600 px-5 py-2.5 text-center text-lg font-bold text-white hover:bg-slate-900 hover:bg-gradient-to-br focus:outline-none focus:ring-4 dark:focus:ring-slate-800"
                    onClick={handleReject}
                  >
                    반려
                  </button>
                </div>
              )}
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
}
