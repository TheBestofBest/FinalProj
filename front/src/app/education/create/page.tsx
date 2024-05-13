"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { useQueryClient } from "@tanstack/react-query";
import { useRouter } from "next/navigation";

const EducationCreatePage = () => {
  const router = useRouter();
  const queryClient = useQueryClient();
  const data: any = queryClient.getQueryData(["member"]);
  const [sendData, setSendData] = useState({
    category: "",
    title: "",
    content: "",
    video: "",
  });

  useEffect(() => {
    if (data.username !== "admin") {
      router.back();
    }
  }, []);

  function inputFocus(inputName: string) {
    const inputElement = document.getElementsByName(inputName);
    inputElement[0]?.focus();
  }

  function inputValid() {
    if (sendData.category == "") {
      alert("카테고리를 입력해 주세요");
      inputFocus("category");
      return false;
    }
    if (sendData.title == "") {
      alert("제목을 입력해 주세요");
      inputFocus("title");
      return false;
    }
    if (sendData.content == "") {
      alert("내용을 입력해 주세요");
      inputFocus("content");
      return false;
    }

    if (sendData.video == "") {
      alert("비디오를 첨부해주세요");
      inputFocus("video");
      return false;
    }

    return true;
  }

  const handlerVideo = (e) => {
    var file = e.target.files[0];

    var maxSize = 50 * 1024 * 1024;

    if (file.size > maxSize) {
      alert("최대 동영상 크기는 50MB 입니다.");
      e.target = null;
      document.getElementsByName("video")[0].value = "";
      return;
    }

    setSendData({ ...sendData, video: file });

    console.log(sendData);
  };

  const handlerSubmit = () => {
    if (inputValid() == false) {
      return;
    }

    const formData = new FormData();
    formData.append("category", sendData.category);
    formData.append("title", sendData.title);
    formData.append("content", sendData.content);
    formData.append("video", sendData.video);

    api.post("/api/v1/educations", formData).then((res) => {
      alert(res.data.msg);

      if (res.data.isSuccess) {
        router.push("/education");
      }
    });
  };

  const handlerChange = (e) => {
    const { name, value } = e.target;
    setSendData({ ...sendData, [name]: value });
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Education" />
      <div className="">
        <div className="flex flex-col gap-9">
          {/* <!-- Contact Form --> */}
          <div className="rounded-sm border border-stroke bg-white shadow-default dark:border-strokedark dark:bg-boxdark">
            <div className="border-b border-stroke px-6.5 py-4 dark:border-strokedark">
              <h3 className="font-medium text-black dark:text-white">
                영상 등록
              </h3>
            </div>
            <div className="p-6.5">
              <div className="mb-4.5">
                <label className="mb-3 block font-medium text-black dark:text-white">
                  Category
                  {/* <span className="text-meta-1">*</span> */}
                </label>
                <input
                  type="text"
                  name="category"
                  onChange={handlerChange}
                  placeholder="카테고리를 입력해주세요"
                  className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  required
                />
              </div>

              <div className="mb-4.5">
                <label className="mb-3 block font-medium text-black dark:text-white">
                  Title
                </label>
                <input
                  type="text"
                  name="title"
                  onChange={handlerChange}
                  placeholder="Select subject"
                  className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  required
                />
              </div>

              <div className="mb-4.5">
                <label className="mb-3 block font-medium text-black dark:text-white">
                  Content
                </label>
                <textarea
                  rows={6}
                  placeholder="Type your message"
                  name="content"
                  onChange={handlerChange}
                  className="w-full rounded border-[1.5px] border-stroke bg-transparent px-5 py-3 text-black outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:text-white dark:focus:border-primary"
                  required
                ></textarea>
              </div>
              <div className="mb-4.5">
                <label className="mb-3 block font-medium text-black dark:text-white">
                  Attach file{" "}
                  <span className="text-xs text-red">* max 50MB</span>
                </label>
                <input
                  type="file"
                  name="video"
                  onChange={handlerVideo}
                  className="w-full cursor-pointer rounded-lg border-[1.5px] border-stroke bg-transparent outline-none transition file:mr-5 file:border-collapse file:cursor-pointer file:border-0 file:border-r file:border-solid file:border-stroke file:bg-whiter file:px-5 file:py-3 file:hover:bg-primary file:hover:bg-opacity-10 focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:border-form-strokedark dark:bg-form-input dark:file:border-form-strokedark dark:file:bg-white/30 dark:file:text-white dark:focus:border-primary"
                  required
                />
              </div>
              <button
                onClick={handlerSubmit}
                className="flex w-full justify-center rounded bg-primary p-3 font-medium text-gray hover:bg-opacity-90"
              >
                등록
              </button>
            </div>
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default EducationCreatePage;
