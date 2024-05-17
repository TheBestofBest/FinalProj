"use client";
import "@fullcalendar/common/main.css";
import "@/css/calendar.css";
import "react-datepicker/dist/react-datepicker.css";
import { useQuery } from "@tanstack/react-query";
import { Cog6ToothIcon } from "@heroicons/react/24/solid";
import { Menu, Transition } from "@headlessui/react";
import api from "@/util/api";
import DatePicker from "react-datepicker";
import { useState } from "react";
interface DetailModalProps {
  setDetailModal: (value: boolean) => void;
  handleCheckBox: () => void;
  detailData: {};
}

function classNames(...classes) {
  return classes.filter(Boolean).join(" ");
}

const DetailModal: React.FC<DetailModalProps> = ({
  setDetailModal,
  handleCheckBox,
  detailData,
}) => {
  const [updateForm, setUpdateForm] = useState(false);
  const [startDate, setStartDate] = useState(
    new Date(detailData.originalStart),
  );
  const [endDate, setEndDate] = useState(new Date(detailData.originalEnd));
  console.log(detailData);
  const { isLoading, data } = useQuery({
    queryKey: ["member"],
  });

  const vaild = (categoryId: string, category: string) => {
    if (category == "all" && data && data.department.id === 1) {
      return true;
    }

    if (category == "dept" && data && data.department.id === categoryId) {
      return true;
    }

    if (category == "member" && data && data.id === categoryId) {
      return true;
    }
    return false;
  };

  const handlerUpdateClose = () => {
    setUpdateForm(false);
  };

  const handlerEdit = () => {
    if (vaild(detailData.categoryId, detailData.category) == false) {
      alert("권한이 없습니다.");
      return;
    }
    setUpdateForm(true);
  };

  const handlerScheduleUpdate = () => {
    const sendName = document.querySelector("#scheduleUpdateName").value;

    if (sendName == "") {
      alert("스케줄명을 입력해 주세요");
      return;
    }

    if (startDate > endDate) {
      alert("시작시간보다 종료시간이 늦을 수 없습니다.");
      return;
    }

    if (!confirm("스케줄을 변경하시겠습니까?")) {
      return;
    }

    const sendData = {
      id: detailData.originalId,
      name: sendName,
      startDate:
        startDate.getFullYear() +
        "-" +
        (startDate.getMonth() + 1 <= 9
          ? "0" + (startDate.getMonth() + 1)
          : startDate.getMonth() + 1) +
        "-" +
        (startDate.getDate() <= 9
          ? "0" + startDate.getDate()
          : startDate.getDate()),
      endDate:
        endDate.getFullYear() +
        "-" +
        (endDate.getMonth() + 1 <= 9
          ? "0" + (endDate.getMonth() + 1)
          : endDate.getMonth() + 1) +
        "-" +
        (endDate.getDate() <= 9 ? "0" + endDate.getDate() : endDate.getDate()),
    };

    api.patch("api/v1/schedules", sendData).then((res) => {
      console.log(res);
      alert(res.data.msg);
      if (res.data.isSuccess) {
        handleCheckBox();
        setDetailModal(false);
      }
    });
  };

  const handlerDelete = () => {
    if (vaild(detailData.categoryId, detailData.category) == false) {
      alert("권한이 없습니다.");
      return;
    }

    if (!confirm("삭제된 일정은 되돌릴 수 없습니다.")) {
      return;
    }

    const deleteData = {
      id: detailData.originalId,
      relationName: detailData.category,
      relationId: detailData.categoryId,
    };

    api
      .delete("/api/v1/schedules", {
        data: deleteData,
      })
      .then((res) => {
        alert(res.data.msg);
        handleCheckBox();
        setDetailModal(false);
      });
  };

  var color = "black";
  var category = "";
  console.log(detailData);
  switch (detailData.category) {
    case "all":
      color = "text-red";
      category = "전체";
      break;
    case "dept":
      color = "text-green-700";
      category = "부서";
      break;
    case "member":
      color = "text-blue-700";
      category = "개인";
      break;
    default:
      color = "text-gray-700";
      break;
  }
  return (
    <>
      <div className="  fixed inset-0 z-50 flex items-center justify-center overflow-y-auto overflow-x-hidden outline-none focus:outline-none">
        <div className="relative mx-auto my-6 w-180">
          {/*content*/}
          <div className="relative flex w-full flex-col rounded-lg border-0 bg-white shadow-lg outline-none focus:outline-none">
            {/*header*/}
            <div className="border-blueGray-200 flex items-start justify-between rounded-t border-b border-solid p-5">
              <h3 className={`${color} text-3xl font-semibold `}>
                {category} 일정
              </h3>

              <Menu as="div" className="relative inline-block text-left">
                <div>
                  <Menu.Button className="text-gray-900 ring-gray-300 hover:bg-gray-50 inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold shadow-sm ring-inset">
                    <Cog6ToothIcon
                      className="text-gray-400 h-5 w-5"
                      aria-hidden="true"
                    />
                  </Menu.Button>
                </div>

                <Transition
                  // as={Fragment}
                  enter="transition ease-out duration-100"
                  enterFrom="transform opacity-0 scale-95"
                  enterTo="transform opacity-100 scale-100"
                  leave="transition ease-in duration-75"
                  leaveFrom="transform opacity-100 scale-100"
                  leaveTo="transform opacity-0 scale-95"
                >
                  <Menu.Items className="absolute right-0 z-10 mt-2 w-20 origin-top-right rounded-md bg-white shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                    <div className="py-1">
                      <Menu.Item>
                        {({ active }) => (
                          <button
                            className={classNames(
                              active
                                ? "bg-gray-100 text-gray-900"
                                : "text-gray-700",
                              "block px-4 py-2 text-sm",
                            )}
                            onClick={handlerEdit}
                          >
                            수정
                          </button>
                        )}
                      </Menu.Item>
                      <Menu.Item>
                        {({ active }) => (
                          <button
                            className={classNames(
                              active
                                ? "bg-gray-100 text-gray-900"
                                : "text-gray-700",
                              "block px-4 py-2 text-sm",
                            )}
                            onClick={handlerDelete}
                          >
                            삭제
                          </button>
                        )}
                      </Menu.Item>
                    </div>
                  </Menu.Items>
                </Transition>
              </Menu>
            </div>
            {/*body*/}
            {updateForm ? (
              <div className="relative p-6">
                <div className="flex gap-3 ">
                  <div className="w-1/2">
                    <label className="block text-xl">category</label>
                    <div className="mt-1">
                      <div className="ring-gray-300 flex rounded-md shadow-sm ring-1 ring-inset focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                        <input
                          type="text"
                          className="text-gray-900 placeholder:text-gray-400 block flex-1 border-0 bg-transparent py-1.5 pl-1 focus:ring-0 sm:text-sm sm:leading-6"
                          defaultValue={category}
                          disabled
                        />
                      </div>
                    </div>
                  </div>
                  <div className="w-1/2">
                    <label htmlFor="name" className="block text-xl">
                      name
                    </label>
                    <div className="mt-1">
                      <div className="ring-gray-300 flex rounded-md shadow-sm ring-1 ring-inset focus-within:ring-2 focus-within:ring-inset focus-within:ring-indigo-600 sm:max-w-md">
                        <input
                          type="text"
                          name="scheduleUpdateName"
                          id="scheduleUpdateName"
                          className="text-gray-900 placeholder:text-gray-400 block flex-1 border-0 bg-transparent py-1.5 pl-1 focus:ring-0 sm:text-sm sm:leading-6"
                          defaultValue={detailData.title}
                        />
                      </div>
                    </div>
                  </div>
                </div>
                <div className="mt-5 flex gap-3 ">
                  <div className="w-1/2">
                    <div>시작일</div>
                    <DatePicker
                      className="mt-1 inline-block rounded-md border border-blue-300 px-19 py-1"
                      dateFormat={"yyyy년 MM월 dd일"}
                      selected={startDate}
                      onChange={(date) => setStartDate(date)}
                      closeOnScroll={true}
                    />
                  </div>
                  <div className="w-1/2">
                    <div>종료일</div>
                    <DatePicker
                      className="mt-1 inline-block rounded-md border border-blue-300 px-20 py-1"
                      dateFormat={"yyyy년 MM월 dd일"}
                      selected={endDate}
                      onChange={(date) => setEndDate(date)}
                      closeOnScroll={true}
                    />
                  </div>
                </div>
                <div className="mt-5 flex justify-end">
                  <button
                    onClick={handlerUpdateClose}
                    className="hover:bg-gray-100 text-gray-800 border-gray-400 me-3 rounded border bg-white px-4 py-2 font-semibold shadow"
                  >
                    취소
                  </button>
                  <button
                    onClick={handlerScheduleUpdate}
                    className="hover:bg-gray-100 text-gray-800 border-gray-400 rounded border border-green-400 bg-white px-4 py-2 font-semibold shadow"
                  >
                    변경
                  </button>
                </div>
              </div>
            ) : (
              <div className="relative p-6">
                <div className="grid h-50 content-between gap-3 overflow-auto">
                  {detailData && detailData.title}
                </div>
                <div className="text-end">
                  {detailData && detailData.author}
                </div>
                <div className="flex justify-end">
                  {detailData && detailData.originalStart} ~{" "}
                  {detailData && detailData.originalEnd}
                </div>
              </div>
            )}

            {/*  */}

            {/*footer*/}
            <div className="border-blueGray-200 flex items-center justify-end rounded-b border-t border-solid p-3">
              <button
                className="text-red-500 background-transparent mb-1 mr-1 px-6 py-2 text-sm font-bold uppercase outline-none transition-all duration-150 ease-linear focus:outline-none"
                type="button"
                onClick={() => setDetailModal(false)}
              >
                Close
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="fixed inset-0 z-40 bg-black opacity-25"></div>
    </>
  );
};

export default DetailModal;
