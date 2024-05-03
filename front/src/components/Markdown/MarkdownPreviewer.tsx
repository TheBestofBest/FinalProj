"use client";
import React, { ChangeEvent, useEffect, useState } from "react";
import ReactMarkdown from "react-markdown";
import { remark } from "remark";
import html from "remark-html";

const MarkdownPreviewer = () => {
  const [markdown, setMarkdown] = useState("");
  const [markdownResult, setMarkdownResult] = useState("");

  const handleChange = (e: ChangeEvent<HTMLTextAreaElement>) => {
    setMarkdown(e.target.value);
  };
  async function markdownToHtml(markdown: string) {
    const result = await remark().use(html).process(markdown);
    return result.toString();
  }
  useEffect(() => {
    async function updateMarkdownResult() {
      const result = await markdownToHtml(markdown);
      setMarkdownResult(result);
    }

    updateMarkdownResult();
  }, [markdown]);

  const htmlTest = `<h1> 123</h1`;

  return (
    <>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <div style={{ width: "45%", padding: "10px" }}>
          <textarea
            style={{ width: "100%", height: "300px" }}
            value={markdown}
            onChange={handleChange}
            placeholder="마크다운을 작성하세요..."
          />
        </div>
        <div
          style={{ width: "45%", padding: "10px" }}
          dangerouslySetInnerHTML={{ __html: markdownResult }}
        ></div>
      </div>
    </>
  );
};

export default MarkdownPreviewer;
