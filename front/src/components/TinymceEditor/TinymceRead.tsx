import { useRef } from 'react';
import { Editor } from '@tinymce/tinymce-react';

function TinymceRead() {

  const API_KEY = process.env.TINYMCE_API_KEY;

  return (
    <>
      <Editor
        initialValue="<p>메일 내용.</p>"
        apiKey='q03zh6g2avu55q4mioaksymo3tfr10issxzvkcu4rz6szqrx'
        init={{
            readonly: true
        }}
      />
    </>
  );
}

export default TinymceRead;
