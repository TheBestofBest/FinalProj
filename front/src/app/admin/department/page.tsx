"use client ";

import ManageMentStructure from "@/components/Admin/ManagementStructure";
import DefaultLayout from "@/components/Layouts/DefaultLayout";

const AdminDepartment = () => {
  return (
    <DefaultLayout>
      <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between"></div>
      <div className="h-[calc(100vh-186px)] overflow-hidden sm:h-[calc(100vh-174px)]">
        <ManageMentStructure />
      </div>
    </DefaultLayout>
  );
};

export default AdminDepartment;
