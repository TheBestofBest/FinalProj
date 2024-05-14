"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { useParams, useRouter } from "next/navigation";
import ReactPlayer from "react-player";
import { useQueryClient } from "@tanstack/react-query";

const EducationDetailPage = () => {
  const router = useRouter();
  const params = useParams();
  const queryClient = useQueryClient();
  const data: any = queryClient.getQueryData(["member"]);
  const [video, setVideo] = useState({});

  useEffect(() => {
    api.get("/api/v1/educations/" + params.id).then((res) => {
      setVideo(res.data.data.video);
    });
  }, []);

  const handlerDelete = () => {
    if (!confirm("해당 동영상을 삭제 하시겠습니까?")) {
      return;
    }
    api.delete("/api/v1/educations/" + params.id).then((res) => {
      alert(res.data.msg);
      router.push("/education");
    });
  };

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Education" />

      <div className="h-171.5 gap-9 bg-white p-3 shadow-3">
        {/* <!-- Contact Form --> */}
        <div className="relative h-115">
          <ReactPlayer
            url={video.filePath}
            controls={true}
            width="w-full"
            height="h-full"
            className="h-full w-full"
          />
          {data.username == "admin" ? (
            <div className="absolute right-0 top-0">
              <a
                className="text-gray-800 border-gray-400 me-2 rounded border bg-white px-2 py-1 shadow"
                href={"/education/" + params.id + "/edit"}
              >
                수정
              </a>
              <a
                className="text-gray-800 border-gray-400 rounded border bg-white px-2 py-1 shadow hover:cursor-pointer"
                // href="/education/create"
                onClick={handlerDelete}
              >
                삭제
              </a>
            </div>
          ) : (
            ""
          )}
        </div>
        <div className="video-discription mt-5 overflow-auto p-3">
          <div className="text-xl font-bold">{video.title}</div>
          <div className="text-end text-sm">
            <p>{video.authorName}</p>
            <p>조회수 {video.hit}</p>
          </div>

          <p className="mt-3 text-base">{video.content}</p>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default EducationDetailPage;
