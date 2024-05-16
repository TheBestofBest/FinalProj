import { useRef } from 'react';
import { Editor } from '@tinymce/tinymce-react';

function TinymceRead() {

  const API_KEY = process.env.TINYMCE_API_KEY;

  return (
    <>
      <Editor
        initialValue="<p>메일 내용.</p>"
        apiKey={API_KEY}
        init={{
            readonly: true,
            disable: true
        }}
      />
    </>
  );
}

export default TinymceRead;
