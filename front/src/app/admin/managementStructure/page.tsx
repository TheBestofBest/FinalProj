"use client ";

import ManageMentStructure from "@/components/Admin/StructureManagement/ManagementStructure";
import DefaultLayout from "@/components/Layouts/DefaultLayout";

const AdminDepartment = () => {
  return (
    <DefaultLayout>
      <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
        <h2 className="text-title-md2 font-bold text-black dark:text-white">
          조직 구조 관리
        </h2>

        <nav>
          <ol className="flex items-center gap-2">
            <li>
              <a className="font-medium" href="index.html">
                관리자 메뉴 /
              </a>
            </li>
            <li className="font-medium text-primary">조직 구조 관리</li>
          </ol>
        </nav>
      </div>
      <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between"></div>
      <div className="overflow-hidden">
        <ManageMentStructure />
      </div>
    </DefaultLayout>
  );
};

export default AdminDepartment;
