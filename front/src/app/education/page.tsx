"use client";
import { useEffect, useState } from "react";
import "react-datepicker/dist/react-datepicker.css";

import DefaultLayout from "@/components/Layouts/DefaultLayout";
import api from "@/util/api";
import Breadcrumb from "@/components/Breadcrumbs/Breadcrumb";
import { useQueryClient } from "@tanstack/react-query";
import { useSearchParams } from "next/navigation";
import EducationCard from "./educationCard";

const EducationListPage = () => {
  const queryClient = useQueryClient();
  const data: any = queryClient.getQueryData(["member"]);
  const searchParams = useSearchParams();
  const [totalPages, setTotalPages] = useState(0);
  const [videos, setVideos] = useState([]);

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
    console.log(data.totalPages);
    setTotalPages(data.totalPages);
    setVideos(data.content);
  };

  const rendering = () => {
    if (page === 0) {
      return pageATag(0, 2);
    } else if (Number(page + 1) === Number(totalPages)) {
      return pageATag(page - 2, page);
    } else {
      return pageATag(page - 2, Number(page + 2));
    }
  };

  const pageATag = (start: number, end: number) => {
    const result = [];
    var s = start < 0 ? 0 : start;
    var e = 0;
    if (totalPages <= 2) {
      e = totalPages - 1;
    } else {
      e = totalPages - 1 < end ? totalPages - 1 : end;
    }
    for (let i = s; i <= e; i++) {
      if (i == page) {
        result.push(
          <button
            key={i}
            disabled
            aria-current="page"
            className="relative z-10 inline-flex items-center bg-indigo-600 px-4 py-2 text-sm font-semibold text-white focus:z-20 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
          >
            {i + 1}
          </button>,
        );
      } else {
        result.push(
          <a
            key={i}
            href={"/education?page=" + i}
            className="text-gray-900 ring-gray-300 hover:bg-gray-50 relative inline-flex items-center px-4 py-2 text-sm font-semibold ring-1 ring-inset focus:z-20 focus:outline-offset-0"
          >
            {i + 1}
          </a>,
        );
      }
    }
    return result;
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
          <div className="absolute right-3 top-3 z-99999 ">
            <a
              className="hover:bg-gray-100 text-gray-800 border-gray-400 mt-3 rounded border bg-white px-3 py-1 font-semibold shadow"
              href="/education/create"
            >
              등록
            </a>{" "}
          </div>
        ) : (
          ""
        )}
        {/* <!-- Contact Form --> */}

        <div className="flex h-171.5 flex-col justify-between border-stroke bg-white align-bottom shadow-3 dark:border-strokedark dark:bg-boxdark">
          <div className="h-150">
            {videos.length > 0 ? (
              <div className="grid h-full w-full grid-cols-4 grid-rows-2 gap-10 p-5 ">
                {videos.map((data) => {
                  return <EducationCard video={data} />;
                })}
              </div>
            ) : (
              <div className="flex h-full items-center justify-center">
                <div>아직 게시된 동영상이 없습니다.</div>
              </div>
            )}
          </div>

          <div className="text-center">
            <div className="border-gray-200 flex justify-center border-t bg-white px-4 py-3 sm:px-6">
              <div>
                <nav
                  className="isolate inline-flex -space-x-px rounded-md shadow-sm"
                  aria-label="Pagination"
                >
                  {rendering()}
                </nav>
              </div>
            </div>
          </div>
        </div>
      </div>
    </DefaultLayout>
  );
};

export default EducationListPage;
