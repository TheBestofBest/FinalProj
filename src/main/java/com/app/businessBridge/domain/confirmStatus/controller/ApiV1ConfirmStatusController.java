package com.app.businessBridge.domain.confirmStatus.controller;

import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.request.CreateConfirmStatusRequest;
import com.app.businessBridge.domain.confirmStatus.request.PatchConfirmStatusRequest;
import com.app.businessBridge.domain.confirmStatus.response.ConfirmStatusesResponse;
import com.app.businessBridge.domain.confirmStatus.response.CreateConfirmStatusResponse;
import com.app.businessBridge.domain.confirmStatus.response.PatchConfirmStatusResponse;
import com.app.businessBridge.domain.confirmStatus.service.ConfirmStatusService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm-statuses")
public class ApiV1ConfirmStatusController {
    private final ConfirmStatusService confirmStatusService;


    // 결재 처리 상태 다건 조회
    @GetMapping("")
    public RsData<ConfirmStatusesResponse> getConfirmStatuses(){
        List<ConfirmStatus> confirmStatuses = this.confirmStatusService.getAll();
        List<ConfirmStatusDTO> confirmStatusDTOS = new ArrayList<>();
        for (ConfirmStatus confirmStatus : confirmStatuses) {
            confirmStatusDTOS.add(new ConfirmStatusDTO(confirmStatus));
        }

        return RsData.of(RsCode.S_01, "Confirm-Status 다건 조회 성공", new ConfirmStatusesResponse(confirmStatusDTOS));
    }
    // 결재 처리 상태 신규 등록 [관리자 권한]
    @PostMapping("")
    public RsData<CreateConfirmStatusResponse> createConfirmStatus(@Valid CreateConfirmStatusRequest createConfirmStatusRequest){
        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.create(createConfirmStatusRequest.getStatusName(),createConfirmStatusRequest.getStatusDescription());
        return RsData.of(
                confirmStatusRsData.getRsCode(),
                confirmStatusRsData.getMsg(),
                new CreateConfirmStatusResponse(new ConfirmStatusDTO(confirmStatusRsData.getData())));
    }
    // 결재 처리 상태 수정 [관리자 권한]
    @PatchMapping("/{ConfirmStatusId}")
    public RsData<PatchConfirmStatusResponse> patchConfirmStatus(@Valid PatchConfirmStatusRequest patchConfirmStatusRequest){
        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.

    }
}
