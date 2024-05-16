import * as React from "react";
import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import { MemberType } from "@/types/Member/MemberTypes";

interface StepperProps {
  activeStep: number;
  confirmMembers: MemberType[];
}

const HorizontalLinearAlternativeLabelStepper: React.FC<StepperProps> = ({
  activeStep,
  confirmMembers,
}) => {
  const names = confirmMembers.map((member) => member.name);
  return (
    <Box
      sx={{
        width: "100%",
      }}
    >
      {/* activeStep에 따라 진행도 차이 */}
      <Stepper activeStep={activeStep} alternativeLabel>
        {names.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
};
export default HorizontalLinearAlternativeLabelStepper;
