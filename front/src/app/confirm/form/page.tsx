`use client`;
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import MarkdownPreviewer from "@/components/Markdown/MarkdownPreviewer";

export default function ConfirmForm() {
  return (
    <DefaultLayout>
      <div className="h-full w-full">
        <MarkdownPreviewer />
      </div>
    </DefaultLayout>
  );
}
