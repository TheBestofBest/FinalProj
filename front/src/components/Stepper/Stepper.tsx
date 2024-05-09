import * as React from "react";
import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";

const steps = ["반복문으로", "승인자 이름/직급", "세 번째 승인자"];

export default function HorizontalLinearAlternativeLabelStepper() {
  return (
    <Box
      sx={{
        width: "100%",
      }}
    >
      {/* activeStep에 따라 진행도 차이 */}
      <Stepper activeStep={0} alternativeLabel>
        {steps.map((label) => (
          <Step key={label}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
}
