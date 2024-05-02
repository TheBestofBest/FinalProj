"use client";
import ConfirmTable from "@/components/Confirm/ConfirmTable";
import HorizontalLinearAlternativeLabelStepper from "@/components/Stepper/Stepper";

export default function ConfirmPage() {
  return (
    <>
      <div className="h-full w-full overflow-scroll">
        <ConfirmTable></ConfirmTable>
      </div>
    </>
  );
}
