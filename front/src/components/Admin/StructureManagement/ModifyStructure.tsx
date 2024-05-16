import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { useState } from "react";
import api from "@/util/api";
import { replaceEqualDeep, useQueryClient } from "@tanstack/react-query";

const style = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  p: 4,
  border: "1px solid #000",
};

interface Props {
  row: {
    id: number;
    code: number;
    name: string;
  };
  category: string;
  categoryKo: string;
}

export default function ModifyStructure({ row, category, categoryKo }: Props) {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const queryClient = useQueryClient();

  const [modifyStructureForm, setModifyStructureForm] = useState({
    id: row.id,
    code: row.code,
    name: row.name,
  });

  const handleChange = (e: any) => {
    const { name, value } = e.target;
    setModifyStructureForm({ ...modifyStructureForm, [name]: value });
    console.log({ ...modifyStructureForm, [name]: value });
  };

  const refetching = async () => {
    await api.get(`/api/v1/${category}`).then((res) => {
      queryClient.setQueryData([category], res.data.data.dtoList);
    });
  };

  const handleClick = () => {
    api.patch(`/api/v1/${category}`, modifyStructureForm).then((res) => {
      if (res.data.isSuccess) {
        handleClose();
        alert(res.data.msg);
        refetching();
      } else {
        alert(res.data.msg);
      }
    });
  };

  return (
    <div>
      <button onClick={handleOpen}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          height="24px"
          viewBox="0 -960 960 960"
          width="24px"
          fill="#5f6368"
        >
          <path d="M200-200h57l391-391-57-57-391 391v57Zm-80 80v-170l528-527q12-11 26.5-17t30.5-6q16 0 31 6t26 18l55 56q12 11 17.5 26t5.5 30q0 16-5.5 30.5T817-647L290-120H120Zm640-584-56-56 56 56Zm-141 85-28-29 57 57-29-28Z" />
        </svg>
      </button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <div className="flex justify-center text-black">
            <Typography id="modal-modal-title" variant="h6" component="h2">
              {categoryKo} 수정
            </Typography>
          </div>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            <div className="mb-3">
              <input type="hidden" name="id" defaultValue={row.id} />
              <div className="mb-1">{categoryKo} 코드</div>
              <input
                onChange={handleChange}
                type="text"
                name="code"
                className="h-8 w-full rounded-md border px-2"
                defaultValue={row.code}
                placeholder={`${categoryKo} 코드(숫자)를 입력해주세요.`}
              />
            </div>
            <div className="mb-5">
              <div className="mb-1">{categoryKo}명</div>
              <input
                onChange={handleChange}
                type="text"
                name="name"
                className="h-8 w-full rounded-md border px-2"
                defaultValue={row.name}
                placeholder={`${categoryKo}명을 입력해주세요..`}
              />
            </div>
            <div className="mb-5 flex justify-end">
              <button
                onClick={handleClick}
                className="mr-3 rounded-md border bg-primary px-2 py-1 text-white"
              >
                수정
              </button>
              <button
                className="rounded-md border px-2 py-1"
                onClick={handleClose}
              >
                취소
              </button>
            </div>
          </Typography>
        </Box>
      </Modal>
    </div>
  );
}
