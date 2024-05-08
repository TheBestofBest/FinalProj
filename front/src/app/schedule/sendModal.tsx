"use client";
import "@fullcalendar/common/main.css";
import "@/css/calendar.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import api from "@/util/api";
import { useState } from "react";

interface SendModalProps {
  setShowModal: (value: boolean) => void;
  handleCheckBox: () => void;
  data: {};
}

const SendModal: React.FC<SendModalProps> = ({
  setShowModal,
  handleCheckBox,
  data,
}) => {
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());

  const handlerScheduleSend = () => {
    const sendCategory = document.querySelector("#scheduleCategory").value;
    const sendName = document.querySelector("#scheduleName").value;

    if (sendName == "") {
      alert("스케줄명을 입력해 주세요");
      return;
    }

    if (startDate > endDate) {
      alert("시작시간보다 종료시간이 늦을 수 없습니다.");
      return;
    }

    if (!confirm("스케줄을 등록하시겠습니까?")) {
      return;
    }
    var relationId = null;

    if (sendCategory == "all") {
      relationId = 0;
    }
    if (sendCategory == "dept") {
      relationId = data && data?.department.id;
    }
    if (sendCategory == "member") {
      relationId = data && data?.id;
    }

    if (relationId == null) {
      alert("권한이 없습니다.");
      return;
    }

    const sendData = {
      relationName: sendCategory,
      relationId: relationId,
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

    api.post("api/v1/schedules", sendData).then((res) => {
      console.log(res);
      alert(res.data.msg);
      if (res.data.isSuccess) {
        handleCheckBox();
        setShowModal(false);
      }
    });
  };

  return (
    <>
      <div className="  fixed inset-0 z-50 flex items-center justify-center overflow-y-auto overflow-x-hidden outline-none focus:outline-none">
        <div className="relative mx-auto my-6 w-180">
          {/*content*/}
          <div className="relative flex w-full flex-col rounded-lg border-0 bg-white shadow-lg outline-none focus:outline-none">
            {/*header*/}
            <div className="border-blueGray-200 flex items-start justify-between rounded-t border-b border-solid p-5">
              <h3 className="text-3xl font-semibold">스케줄 등록</h3>
            </div>
            {/*body*/}
            <div className="relative p-6">
              <div className="flex gap-3 ">
                <div className="w-1/2">
                  <label htmlFor="scheduleCategory" className="block text-xl">
                    category
                  </label>
                  <div className="mt-1">
                    <select
                      id="scheduleCategory"
                      name="scheduleCategory"
                      className="text-gray-900 ring-gray-300 block w-full rounded-md border-0 py-2 shadow-sm ring-1 ring-inset focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
                    >
                      <option value="all">회사</option>
                      <option value="dept">부서</option>
                      <option value="member">개인</option>
                    </select>
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
                        name="scheduleName"
                        id="scheduleName"
                        className="text-gray-900 placeholder:text-gray-400 block flex-1 border-0 bg-transparent py-1.5 pl-1 focus:ring-0 sm:text-sm sm:leading-6"
                        placeholder="스케줄명을 입력해 주세요"
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
            </div>
            {/*footer*/}
            <div className="border-blueGray-200 flex items-center justify-end rounded-b border-t border-solid p-3">
              <button
                className="text-red-500 background-transparent mb-1 mr-1 px-6 py-2 text-sm font-bold uppercase outline-none transition-all duration-150 ease-linear focus:outline-none"
                type="button"
                onClick={() => setShowModal(false)}
              >
                Close
              </button>
              <button
                className="mb-1 mr-1 rounded bg-emerald-500 px-6 py-3 text-sm font-bold uppercase text-white shadow outline-none transition-all duration-150 ease-linear hover:shadow-lg focus:outline-none active:bg-emerald-600"
                type="button"
                onClick={handlerScheduleSend}
              >
                Save
              </button>
            </div>
          </div>
        </div>
      </div>
      <div className="fixed inset-0 z-40 bg-black opacity-25"></div>
    </>
  );
};

export default SendModal;
