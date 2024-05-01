package com.app.businessBridge.domain.confirmStatus.controller;

import com.app.businessBridge.domain.confirmStatus.entity.ConfirmStatus;
import com.app.businessBridge.domain.confirmStatus.request.ConfirmStatusRequest;
import com.app.businessBridge.domain.confirmStatus.response.ConfirmStatusResponse;
import com.app.businessBridge.domain.confirmStatus.service.ConfirmStatusService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/confirm-statuses")
public class ApiV1ConfirmStatusController {
    private final ConfirmStatusService confirmStatusService;


    // 결재 처리 상태 다건 조회
    @GetMapping("")
    public RsData<ConfirmStatusResponse.getConfirmStatuses> getConfirmStatuses(){
        List<ConfirmStatus> confirmStatuses = this.confirmStatusService.getAll();

        return RsData.of(RsCode.S_01, "Confirm-Status 다건 조회 성공", new ConfirmStatusResponse.getConfirmStatuses(confirmStatuses));
    }
    // 결재 처리 상태 신규 등록 [관리자 권한]
    @PostMapping("")
    public RsData<ConfirmStatusResponse.create> createConfirmStatus(@Valid @RequestBody ConfirmStatusRequest.create createConfirmStatusRequest){
        RsData<ConfirmStatus> confirmStatusRsData = this.confirmStatusService.create(createConfirmStatusRequest.getStatusName(),createConfirmStatusRequest.getStatusDescription());
        return RsData.of(
                confirmStatusRsData.getRsCode(),
                confirmStatusRsData.getMsg(),
                new ConfirmStatusResponse.create(confirmStatusRsData.getData()));
    }
    // 결재 처리 상태 수정 [관리자 권한]
    @PatchMapping("/{confirmStatusId}")
    public RsData<ConfirmStatusResponse.patch> patchConfirmStatus(@PathVariable(value = "confirmStatusId") Long confirmStatusId, @Valid @RequestBody ConfirmStatusRequest.patch patchConfirmStatusRequest){
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
                new ConfirmStatusResponse.patch(confirmStatusRsData.getData())
        );
    }

    @DeleteMapping("/{confirmStatusId}")
    public RsData<ConfirmStatusResponse.delete> deleteConfirmStatus(@PathVariable(value = "confirmStatusId") Long confirmStatusId){
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
               new ConfirmStatusResponse.delete(confirmStatusId)
        );
    }
}
