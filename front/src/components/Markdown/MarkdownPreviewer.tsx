"use client";
import React, { ChangeEvent, useState } from "react";
import ReactMarkdown from "react-markdown";

const MarkdownPreviewer = () => {
  const [markdown, setMarkdown] = useState("");

  const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setMarkdown(e.target.value);
  };

  return (
    <div style={{ display: "flex", justifyContent: "space-between" }}>
      <div style={{ width: "45%", padding: "10px" }}>
        <textarea
          style={{ width: "100%", height: "300px" }}
          value={markdown}
          onChange={handleChange}
          placeholder="마크다운을 작성하세요..."
        />
      </div>
      <div style={{ width: "45%", padding: "10px" }}>
        <ReactMarkdown>{markdown}</ReactMarkdown>
      </div>
    </div>
  );
};

export default MarkdownPreviewer;
