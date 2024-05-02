"use client";
import { Editor } from '@tinymce/tinymce-react';
function TinymceEditor() {
  
  const handleEditorChange = (content, editor) => {
    console.log('Content was updated:', content);
  };

  return (
    <Editor
      initialValue="<p>API 키 아직 안넣음 수정 예정</p>"
      init={{
        height: 500,
        menubar: false,
        plugins: [
          'advlist autolink lists link image charmap print preview anchor',
          'searchreplace visualblocks code fullscreen',
          'insertdatetime media table paste code help wordcount'
        ],
        toolbar:
          'undo redo | formatselect | bold italic backcolor | \
          alignleft aligncenter alignright alignjustify | \
          bullist numlist outdent indent | removeformat | help'
      }}
      onEditorChange={handleEditorChange}
    />
  );
}

export default TinymceEditor;
