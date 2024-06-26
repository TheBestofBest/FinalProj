// modalStyle.tsx
import styled from "@emotion/styled";

// 모달 창 뒷배경
export const ConfirmModalBox = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
`;

// 여기에 만들고 싶은 모달 스타일 구현
export const ConfirmModalContent = styled.div`
  padding: 1.5rem 3rem;
  width: 45.125rem;
  border-radius: 0.313rem;
  display: flex;
  max-height: 650px;
  flex-direction: column;
  background-color: #ffffff;
  overflow-y: auto;

  > div {
    display: flex;
    justify-content: center;
    margin-top: 1.25rem;
    color: black;
  }
  .confirmFormType:hover {
    background-color: #f3f4f7;
    cursor: pointer;
  }
`;
