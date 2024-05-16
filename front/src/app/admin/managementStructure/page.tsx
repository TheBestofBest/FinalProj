"use client ";

import ManageMentStructure from "@/components/Admin/ManagementStructure";
import DefaultLayout from "@/components/Layouts/DefaultLayout";

const AdminDepartment = () => {
  return (
    <DefaultLayout>
      <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between"></div>
      <div className="overflow-hidden">
        <ManageMentStructure />
      </div>
    </DefaultLayout>
  );
};

export default AdminDepartment;
