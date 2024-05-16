"use client";
import HorizontalLinearAlternativeLabelStepper from "../Stepper/Stepper";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import api from "@/util/api";
import { ConfirmType } from "@/types/Confirm/ConfirmTypes";

interface ConfirmTableProps {
  clickModal: boolean;
}

// Props로 `결재 리스트` 받기
const ConfirmTable: React.FC<ConfirmTableProps> = (clickModal) => {
  const [confirms, setConfirms] = useState<ConfirmType[]>([]);
  const router = useRouter();

  const handleRouter = (id: number) => {
    router.push(`/confirm/${id}`);
  };
  useEffect(() => {
    api
      .get(`/api/v1/confirms`)
      .then((response) => setConfirms(response.data.data.confirmDTOs))
      .catch((err) => {
        console.log(err);
      });
  }, [clickModal]);

  return (
    <div className="min-h-[590px] rounded-sm border border-stroke  bg-white pb-2.5 pt-4.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
      <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
        결재함
      </h4>
      <div className="max-w-full overflow-x-auto">
        <table className="w-full table-auto">
          {/* 추후 head 삭제 */}
          {!confirms || confirms.length === 0 ? (
            <tr>
              <td colSpan={4} className="py-5 text-center dark:text-white">
                결재가 없습니다.
              </td>
            </tr>
          ) : (
            <tbody>
              {confirms.map((confirm) => (
                //[] onClick 으로 상세 페이지로 전송하는 이벤트 만들기
                <tr
                  key={confirm.id}
                  className="border-t border-[#eee] hover:cursor-pointer hover:bg-blue-200"
                  onClick={() => handleRouter(confirm.id)}
                >
                  <td className="border-b border-[#eee]  py-5 dark:border-strokedark ">
                    <h5 className="font-medium text-black dark:text-white">
                      {confirm.subject}
                    </h5>
                    <p className="text-sm">
                      {new Date(confirm.createDate).toLocaleDateString(
                        "ko-KR",
                        {
                          year: "numeric",
                          month: "2-digit",
                          day: "2-digit",
                        },
                      )}
                    </p>
                  </td>
                  <td className="min-w-4 border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                    <p className="text-black dark:text-white">
                      {confirm.confirmRequestMember.name}
                    </p>
                    <p className="text-sm">
                      {confirm.confirmRequestMember.department.name}
                    </p>
                  </td>
                  <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                    <p
                      className={`inline-flex rounded-full bg-opacity-10 px-3 py-1 text-sm font-medium ${
                        confirm.confirmStatusDTO?.statusName === "승인"
                          ? "bg-success text-success"
                          : confirm.confirmStatusDTO?.statusName ===
                              "결재 처리중"
                            ? "bg-danger text-danger"
                            : "bg-warning text-warning"
                      }`}
                    >
                      {confirm.confirmStatusDTO?.statusName}
                    </p>
                  </td>
                  <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                    <div className="flex items-center space-x-3.5">
                      <HorizontalLinearAlternativeLabelStepper
                        activeStep={confirm?.confirmStepCounter}
                        confirmMembers={confirm?.confirmMembers}
                      />
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          )}
        </table>
      </div>
    </div>
  );
};

export default ConfirmTable;
