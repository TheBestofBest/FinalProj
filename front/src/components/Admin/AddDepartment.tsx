import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { useState } from "react";
import api from "@/util/api";
import { replaceEqualDeep } from "@tanstack/react-query";

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

export default function AddDepartment({ refetch }) {
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const [departmentForm, setDepartmentform] = useState({
    code: Number,
    name: String,
  });

  const handleChange = (e: any) => {
    const { name, value } = e.target;
    setDepartmentform({ ...departmentForm, [name]: value });
    console.log({ ...departmentForm, [name]: value });
  };

  const handleClick = () => {
    api.post("/api/v1/departments", departmentForm).then((res) => {
      if (res.data.isSuccess) {
        handleClose();
        alert(res.data.msg);
        refetch;
      } else {
        alert(res.data.msg);
      }
    });
  };

  return (
    <div>
      <Button onClick={handleOpen}>부서 등록 +</Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <div className="flex justify-center text-black">
            <Typography id="modal-modal-title" variant="h6" component="h2">
              부서 등록
            </Typography>
          </div>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            <div className="mb-3">
              <div className="mb-1">부서 코드</div>
              <input
                onChange={handleChange}
                type="text"
                name="code"
                className="h-8 w-full rounded-md border px-2"
                placeholder="부서 코드(숫자)를 입력해주세요."
              />
            </div>
            <div className="mb-5">
              <div className="mb-1">부서명</div>
              <input
                onChange={handleChange}
                type="text"
                name="name"
                className="h-8 w-full rounded-md border px-2"
                placeholder="부서명을 입력해주세요."
              />
            </div>
            <div className="mb-5 flex justify-end">
              <button
                onClick={handleClick}
                className="mr-3 rounded-md border bg-primary px-2 py-1 text-white"
              >
                등록
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
