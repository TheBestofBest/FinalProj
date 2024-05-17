"use client";
import ConfirmFormModal from "@/components/Confirm/ConfirmFormModal";
import ConfirmTable from "@/components/Confirm/ConfirmTable";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import { useState } from "react";
export default function ConfirmsPage() {
  // 모달 버튼 클릭 유무를 저장할 state
  const [showModal, setShowModal] = useState(false);

  // 버튼 클릭시 모달 버튼 클릭 유무를 설정하는 state 함수
  const clickModal = () => setShowModal(!showModal);
  return (
    <DefaultLayout>
      <div className="mb-2 flex justify-end">
        <button
          onClick={clickModal}
          data-modal-target="default-modal"
          data-modal-toggle="default-modal"
          type="button"
          className="mb-2 me-2 rounded-lg bg-gradient-to-r from-cyan-400 via-cyan-500 to-cyan-600 px-5 py-2.5 text-center text-sm font-medium text-white shadow-lg shadow-cyan-500/50 hover:bg-gradient-to-br focus:outline-none focus:ring-4 focus:ring-cyan-300 dark:shadow-lg dark:shadow-cyan-800/80 dark:focus:ring-cyan-800"
        >
          결재 등록 modal 버튼
        </button>
      </div>
      <div className="h-full w-full overflow-y-auto">
        <ConfirmTable clickModal={showModal}></ConfirmTable>
        {showModal && <ConfirmFormModal clickModal={clickModal} />}
      </div>
    </DefaultLayout>
  );
}
