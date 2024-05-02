"use client";
import ConfirmTable from "@/components/Confirm/ConfirmTable";
import DefaultLayout from "@/components/Layouts/DefaultLayout";
import HorizontalLinearAlternativeLabelStepper from "@/components/Stepper/Stepper";

export default function ConfirmPage() {
  return (
    <DefaultLayout>
      <div className="h-full w-full overflow-scroll">
        <ConfirmTable></ConfirmTable>
      </div>
    </DefaultLayout>
  );
}
