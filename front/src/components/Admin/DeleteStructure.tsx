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

export default function DeleteStructure({ row, category, categoryKo }: Props) {
  const queryClient = useQueryClient();

  const deleteStructureForm = {
    id: row.id,
  };

  const refetching = async () => {
    await api.get(`/api/v1/${category}`).then((res) => {
      queryClient.setQueryData([category], res.data.data.dtoList);
    });
  };

  const handleClick = () => {
    if (confirm(`해당 ${categoryKo} 항목을 삭제하시겠습니까?`)) {
      api
        .delete(`/api/v1/${category}`, {
          data: deleteStructureForm,
        })
        .then((res) => {
          if (res.data.isSuccess) {
            alert(res.data.msg);
            refetching();
          } else {
            alert(res.data.msg);
          }
        });
    } else {
      alert("삭제가 취소 되었습니다.");
    }
  };

  return (
    <button onClick={handleClick}>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        height="24px"
        viewBox="0 -960 960 960"
        width="24px"
        fill="#5f6368"
      >
        <path d="M280-120q-33 0-56.5-23.5T200-200v-520h-40v-80h200v-40h240v40h200v80h-40v520q0 33-23.5 56.5T680-120H280Zm400-600H280v520h400v-520ZM360-280h80v-360h-80v360Zm160 0h80v-360h-80v360ZM280-720v520-520Z" />
      </svg>
    </button>
  );
}
