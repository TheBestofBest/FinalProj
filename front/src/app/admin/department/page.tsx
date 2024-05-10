import DefaultLayout from "@/components/Layouts/DefaultLayout";

const AdminDepartment = () => {
  return (
    <DefaultLayout>
      <div className="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
        <h2 className="text-title-md2 font-bold text-black dark:text-white">
          부서 관리
        </h2>

        <nav>
          <ol className="flex items-center gap-2">
            <li>
              <a className="font-medium" href="index.html">
                관리 /
              </a>
            </li>
            <li className="font-medium text-primary">부서</li>
          </ol>
        </nav>
      </div>
    </DefaultLayout>
  );
};

export default AdminDepartment;
