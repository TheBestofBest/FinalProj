package com.app.businessBridge.domain.confirmStatus.controller;

import com.app.businessBridge.domain.confirmStatus.dto.ConfirmStatusDTO;
import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.request.CreateConfirmStatusRequest;
import com.app.businessBridge.domain.confirmStatus.request.PatchConfirmStatusRequest;
import com.app.businessBridge.domain.confirmStatus.response.ConfirmStatusesResponse;
import com.app.businessBridge.domain.confirmStatus.response.CreateConfirmStatusResponse;
import com.app.businessBridge.domain.confirmStatus.response.DeleteConfirmStatusResponse;
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
import java.util.Optional;

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
    public RsData<CreateConfirmStatusResponse> createConfirmStatus(@Valid @RequestBody CreateConfirmStatusRequest createConfirmStatusRequest){
        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.create(createConfirmStatusRequest.getStatusName(),createConfirmStatusRequest.getStatusDescription());
        return RsData.of(
                confirmStatusRsData.getRsCode(),
                confirmStatusRsData.getMsg(),
                new CreateConfirmStatusResponse(new ConfirmStatusDTO(confirmStatusRsData.getData())));
    }
    // 결재 처리 상태 수정 [관리자 권한]
    @PatchMapping("/{ConfirmStatusId}")
    public RsData<PatchConfirmStatusResponse> patchConfirmStatus(@PathVariable(value = "ConfirmStatusId") Long confirmStatusId, @Valid @RequestBody PatchConfirmStatusRequest patchConfirmStatusRequest){
        Optional<ConfirmStatus> optionalConfirmStatus = this.confirmStatusService.getConfirmStatus(confirmStatusId);
        if(optionalConfirmStatus.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "해당 id의 결재 처리 상태는 존재하지 않습니다.",
                    null
            );
        }
        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.updateConfirmStatus(optionalConfirmStatus.get(), patchConfirmStatusRequest.getStatusName(),patchConfirmStatusRequest.getStatusDescription());

        return RsData.of(
                confirmStatusRsData.getRsCode(),
                confirmStatusRsData.getMsg(),
                new PatchConfirmStatusResponse(new ConfirmStatusDTO(confirmStatusRsData.getData()))
        );
    }

    @DeleteMapping("/{ConfirmStatusId}")
    public RsData<DeleteConfirmStatusResponse> deleteConfirmStatus(@PathVariable(value = "ConfirmStatusId") Long confirmStatusId){
        Optional<ConfirmStatus> optionalConfirmStatus = this.confirmStatusService.getConfirmStatus(confirmStatusId);
        if(optionalConfirmStatus.isEmpty()){
            return RsData.of(
                    RsCode.F_04,
                    "해당 id의 결재 처리 상태는 존재하지 않습니다.",
                    null
            );
        }
        this.confirmStatusService.deleteConfirmStatus(optionalConfirmStatus.get());

        return RsData.of(
                RsCode.S_04,
                "%d번 결재 처리 상태가 삭제되었습니다.".formatted(confirmStatusId),
                new DeleteConfirmStatusResponse(confirmStatusId)
        );
    }
}
