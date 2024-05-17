import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { useState } from "react";

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

interface Bonus {
  bonus : string,
  rebateId : string
}


export default function RebateModify({rebateId}) {

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setBonus({ bonus: '', rebateId: '' });
  }

  const [bonus, setBonus] = useState<Bonus>({bonus: '', rebateId: '' });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setBonus((prevBonus) => ({
      ...prevBonus,
      [name]: value
    }));
    
    let inputBonus = value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 모두 제거
    inputBonus = inputBonus.replace(/\B(?=(\d{3})+(?!\d))/g, ','); // 세 자리마다 콤마 추가
    setBonus((prevBonus) => ({
      ...prevBonus,
      bonus: inputBonus,
      rebateId: rebateId
    }));
    console.log(bonus);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const response = await fetch(`http://localhost:8090/api/v1/rebates/${rebateId}`, {
        method: 'PATCH',
        credentials: "include",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(bonus)
    });

    if (response.ok) {
        // setRebate(parsedResponse.data.rebateDto);
        alert("상여금 " + bonus.bonus + "원 추가 성공.");
        setOpen(false);
        window.location.replace(`/rebate/${rebateId}`);
    } else {
        alert("정산 생성 실패.");
    }
    
}

  

  return (
    <div>
      <button 
      className="whitespace-nowrap rounded-md bg-meta-3 px-6 pb-2 pt-2.5 text-xs font-medium uppercase leading-normal text-white shadow-[0_4px_9px_-4px_#3b71ca] transition duration-150 ease-in-out hover:bg-primary-600 hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:bg-primary-600 focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] focus:outline-none focus:ring-0 active:bg-primary-700 active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.3),0_4px_18px_0_rgba(59,113,202,0.2)] motion-reduce:transition-none dark:shadow-[0_4px_9px_-4px_rgba(59,113,202,0.5)] dark:hover:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:focus:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)] dark:active:shadow-[0_8px_9px_-4px_rgba(59,113,202,0.2),0_4px_18px_0_rgba(59,113,202,0.1)]"
      onClick={handleOpen}>상여금 추가</button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <div className="flex justify-center text-black">
            <Typography id="modal-modal-title" variant="h6" component="h2">
              등록
            </Typography>
          </div>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
            
              <div className="mb-1">상여금</div>
              
                <input
                  onChange={handleChange}
                  type="text"
                  value={bonus.bonus}
                  name="bonus"
                  className="h-8 w-full rounded-md border px-2"
                  placeholder="금액 입력"
                />
              
            </div>
            <div className="mb-5 flex justify-end">
              <button
                type="submit"
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
            </form>
          </Typography>
        </Box>
      </Modal>
    </div>
  );
}
