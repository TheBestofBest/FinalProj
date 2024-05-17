"use client";
import { useState } from "react";
import { Props } from "react-apexcharts";
import "react-datepicker/dist/react-datepicker.css";

import ReactPlayer from "react-player";

function secToTime(seconds: number) {
  var min = parseInt((seconds % 3600) / 60);
  var sec = seconds % 60;
  if (min !== 0) {
    return min + "분 " + sec + "초";
  }
  return sec + "초";
}

const EducationCard: React.FC<Props> = (video) => {
  const [hover, setHover] = useState(false);
  const data = video.video;
  const videoTime = secToTime(Math.round(data.videoLength));

  return (
    <a
      className="max-w-sm overflow-hidden rounded shadow-lg"
      onMouseOver={() => setHover(true)}
      onMouseOut={() => setHover(false)}
      href={"education/" + data.id}
    >
      {hover ? (
        <div className="relative h-full bg-slate-300">
          <div className="relative h-full ">
            <ReactPlayer
              url={data.filePath}
              playing={true}
              muted={true}
              loop={true}
              width="w-full"
              height="h-full"
              className="absolute bottom-0 left-0 right-0 top-0"
            />
          </div>
        </div>
      ) : (
        <div className="relative h-40 bg-slate-300">
          <img
            src={data.thumbnailPath}
            className="h-40 w-full object-contain"
          />
          <div className="absolute bottom-1 right-1 rounded-md bg-black px-2 text-sm text-white ">
            {videoTime}
          </div>
        </div>
      )}
      <div className="px-6 py-4">
        <div className="mb-2 truncate text-xl font-bold">{data.title}</div>
        <p className="text-gray-700 truncate text-base">{data.content}</p>
      </div>
    </a>
  );
};

export default EducationCard;
