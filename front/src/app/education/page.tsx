"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { useQueryClient } from "@tanstack/react-query";
import { useSearchParams } from "next/navigation";
import ReactPlayer from "react-player";

const EducationListPage = () => {
  const queryClient = useQueryClient();
  const data: any = queryClient.getQueryData(["member"]);
  const searchParams = useSearchParams();
  const [totalPages, setTotalPages] = useState(0);
  const [videos, setVideos] = useState([]);
  const [hover, setHover] = useState(false);

  const page: number =
    searchParams.get("page") === null ? 0 : Number(searchParams.get("page"));

  const getVideos = async () => {
    const data = await api
      .get(`/api/v1/educations?page=${page}`)
      .then((res) => {
        console.log(res);
        if (res.data.isSuccess) {
          if (!res.data.data.empty) {
            console.log(
              "============================================================",
            );
            console.log(res.data.data.videos.content);
            return res.data.data.videos;
          }
        }
      });
    setTotalPages(data.totalPages);
    setVideos(data.content);
  };

  useEffect(() => {
    getVideos();
    console.log(videos);
  }, []);

  return (
    <DefaultLayout>
      <Breadcrumb pageName="Education" />
      <div className="relative">
        {data.username === "admin" ? (
          <div className="absolute right-3 top-3">
            <a
              className="hover:bg-gray-100 text-gray-800 border-gray-400 z-99999 mt-3 rounded border bg-white px-3 py-1 font-semibold shadow"
              href="/education/create"
            >
              등록
            </a>{" "}
          </div>
        ) : (
          ""
        )}
        {/* <!-- Contact Form --> */}

        <div className="h-171.5 rounded-sm border-stroke bg-white shadow-3 dark:border-strokedark dark:bg-boxdark">
          {videos.length > 0 ? (
            <div className="grid h-full w-full grid-cols-4 grid-rows-2 gap-10 p-5 ">
              {videos.map((video) => {
                return (
                  <div
                    className="max-w-sm overflow-hidden rounded shadow-lg"
                    onMouseOver={() => setHover(true)}
                    onMouseOut={() => setHover(false)}
                  >
                    {hover ? (
                      <div className="h-full w-full bg-slate-300">
                        <ReactPlayer
                          url={video.filePath}
                          playing={true}
                          muted={true}
                          width="w-full"
                        />
                      </div>
                    ) : (
                      <figure className="relative h-50 bg-slate-300">
                        <img
                          src={video.thumbnailPath}
                          className="h-50 w-full object-contain"
                        />
                        <span className="absolute">{video.videoLength}</span>
                      </figure>
                    )}
                    <div className="px-6 py-4">
                      <div className="mb-2 truncate text-xl font-bold">
                        {video.title}
                      </div>
                      <p className="text-gray-700 truncate text-base">
                        {video.content}
                      </p>
                    </div>
                  </div>
                );
              })}
            </div>
          ) : (
            <div className="flex h-full items-center justify-center">
              <div>아직 게시된 동영상이 없습니다.</div>
            </div>
          )}
        </div>
      </div>
    </DefaultLayout>
  );
};

export default EducationListPage;
