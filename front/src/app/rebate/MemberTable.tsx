import { BRAND } from "@/types/brand";
import Image from "next/image";
import Link from "next/link";
import DropDownMenu from "./DropDownMenu";

const members = [
  {
    logo: "/images/brand/brand-01.svg",
    name: "김대호",
    dept: "R&D본부 솔루션사업부",
    grade: "주임연구원",
    memberNumber: "1300013",
    salary: "3,800,000",
  },
  {
    logo: "/images/brand/brand-02.svg",
    name: "류한선",
    dept: "R&D본부 솔루션사업부",
    grade: "주임연구원",
    memberNumber: "1300014",
    salary: "3,800,000",
  },
  {
    logo: "/images/brand/brand-03.svg",
    name: "전율택",
    dept: "R&D본부 솔루션사업부",
    grade: "주임연구원",
    memberNumber: "1300015",
    salary: "3,800,000",
  },
  {
    logo: "/images/brand/brand-04.svg",
    name: "양희동",
    dept: "R&D본부 솔루션사업부",
    grade: "주임연구원",
    memberNumber: "1300016",
    salary: "3,800,000",
  },
  {
    logo: "/images/brand/brand-05.svg",
    name: "전오빈",
    dept: "R&D본부 솔루션사업부",
    grade: "주임연구원",
    memberNumber: "1300017",
    salary: "3,800,000",
  },
  {
    logo: "/images/brand/brand-01.svg",
    name: "김정배",
    dept: "R&D본부 솔루션사업부",
    grade: "주임연구원",
    memberNumber: "1300018",
    salary: "3,800,000",
  },
];

const MemberTable = () => {
  return (
    <div className="rounded-sm border border-stroke bg-white px-5 pb-2.5 pt-6 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:pb-1">
      <h4 className="mb-6 text-xl font-semibold text-black dark:text-white">
        2024.05월 급여 정산 내역
      </h4>
      <div className="flex justify-between py-4">
        <DropDownMenu></DropDownMenu>
        <div>
          <Link
              href="#"
              className="inline-flex items-center justify-center rounded-md bg-primary px-6 py-2 text-center font-medium text-white hover:bg-opacity-90">
              저장하기
          </Link>

        </div>

      </div>

      <div className="flex flex-col">
        <div className="grid grid-cols-3 rounded-sm bg-gray-2 dark:bg-meta-4 sm:grid-cols-6">
          <div className="p-2.5 xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              임직원
            </h5>
          </div>
          <div className="p-2.5 text-center xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              소속부서
            </h5>
          </div>
          <div className="p-2.5 text-center xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              직급
            </h5>
          </div>
          <div className="hidden p-2.5 text-center sm:block xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              사원번호
            </h5>
          </div>
          <div className="hidden p-2.5 text-center sm:block xl:p-5">
            <h5 className="text-sm font-medium uppercase xsm:text-base">
              급여
            </h5>
          </div>
        </div>

        {members.map((member, key) => (
          <div
            className={`grid grid-cols-3 sm:grid-cols-6 ${
              key === members.length - 1
                ? ""
                : "border-b border-stroke dark:border-strokedark"
            }`}
            key={key}
          >
            <div className="flex items-center gap-3 p-2.5 xl:p-5">
              <div className="flex-shrink-0">
                <Image src={member.logo} alt="Brand" width={48} height={48} />
              </div>
              <p className="hidden text-black dark:text-white sm:block">
                {member.name}
              </p>
            </div>

            <div className="flex items-center justify-center p-2.5 xl:p-5">
              <p className="text-black dark:text-white">{member.dept}</p>
            </div>

            <div className="flex items-center justify-center p-2.5 xl:p-5">
              <p className="text-meta-3">{member.grade}</p>
            </div>

            <div className="hidden items-center justify-center p-2.5 sm:flex xl:p-5">
              <p className="text-black dark:text-white">{member.memberNumber}</p>
            </div>

            <div className="hidden items-center justify-center p-2.5 sm:flex xl:p-5">
              <p className="text-meta-5">{member.salary}원</p>
            </div>

            <div className="hidden items-center justify-center p-2.5 sm:flex xl:p-5">
            <Link
              href="#"
              className="rounded-md bg-meta-3 px-3 py-2 text-center text-white hover:bg-opacity-90"
            >
              상세보기
            </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MemberTable;
