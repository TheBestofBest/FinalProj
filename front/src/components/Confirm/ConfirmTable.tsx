"use client";
import { Package } from "@/types/package";
import HorizontalLinearAlternativeLabelStepper from "../Stepper/Stepper";
import { useRouter } from "next/navigation";

// 가 데이터
const packageData: Package[] = [
  {
    name: "휴가신청서",
    price: 0.0,
    invoiceDate: `Jan 13,2023`,
    status: "Paid",
  },
  {
    name: "Standard Package",
    price: 59.0,
    invoiceDate: `Jan 13,2023`,
    status: "Paid",
  },
  {
    name: "Business Package",
    price: 99.0,
    invoiceDate: `Jan 13,2023`,
    status: "Unpaid",
  },
  {
    name: "Standard Package",
    price: 59.0,
    invoiceDate: `Jan 13,2023`,
    status: "Pending",
  },
];

// Props로 `결재 리스트` 받기
const ConfirmTable = () => {
  const router = useRouter();

  const handleRouter = () => {
    // 결재 아이디 받게 되면 바꾸기
    router.push("/confirm/1");
  };

  return (
    <div className="rounded-sm border border-stroke bg-white px-2 pb-2.5 pt-4.5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
      <div className="max-w-full overflow-x-auto">
        <table className="w-full table-auto">
          {/* 추후 head 삭제 */}
          <tbody>
            {packageData.map((packageItem, key) => (
              //[] onClick 으로 상세 페이지로 전송하는 이벤트 만들기
              <tr
                key={key}
                className="hover:cursor-pointer hover:bg-blue-200"
                onClick={() => handleRouter()}
              >
                <td className="border-b border-[#eee] px-1 py-5 pl-2 dark:border-strokedark xl:pl-2">
                  <h5 className="font-medium text-black dark:text-white">
                    {packageItem.name}
                  </h5>
                  <p className="text-sm">{packageItem.invoiceDate}</p>
                </td>
                <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                  <p className="text-black dark:text-white">
                    결재 요청자, 요청자 직급
                  </p>
                  <p className="text-sm">부서</p>
                </td>
                <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                  <p
                    className={`inline-flex rounded-full bg-opacity-10 px-3 py-1 text-sm font-medium ${
                      packageItem.status === "Paid"
                        ? "bg-success text-success"
                        : packageItem.status === "Unpaid"
                          ? "bg-danger text-danger"
                          : "bg-warning text-warning"
                    }`}
                  >
                    {packageItem.status}
                  </p>
                </td>
                <td className="border-b border-[#eee] px-4 py-5 dark:border-strokedark">
                  <div className="flex items-center space-x-3.5">
                    <HorizontalLinearAlternativeLabelStepper activeStep={2} />
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ConfirmTable;
