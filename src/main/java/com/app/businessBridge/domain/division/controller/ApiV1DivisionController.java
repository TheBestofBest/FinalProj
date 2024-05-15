package com.app.businessBridge.domain.division.controller;

import com.app.businessBridge.domain.division.entity.Division;
import com.app.businessBridge.domain.division.request.DivisionRequest;
import com.app.businessBridge.domain.division.response.DivisionResponse;
import com.app.businessBridge.domain.division.service.DivisionService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/divisions")
public class ApiV1DivisionController {
    private final DivisionService divisionService;

    // 소속 등록
    @PostMapping("")
    public RsData post(@Valid @RequestBody DivisionRequest.CreateRequest createRequest,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.divisionService.create(createRequest.getCode(),
                createRequest.getName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

    // 소속 다건 조회
    @GetMapping("")
    public RsData<DivisionResponse.GetDivisions> getAll() {
        RsData<List<Division>> rsData = this.divisionService.findAll();

        return RsData.of(rsData.getRsCode(), rsData.getMsg(),
                new DivisionResponse.GetDivisions(rsData.getData()));
    }

    // 소속 수정
    @PatchMapping("")
    public RsData<DivisionResponse.PatchedDivision> patch(@Valid @RequestBody DivisionRequest.UpdateRequest updateRequest,
                                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData<Division> rsData = this.divisionService.update(updateRequest.getId(), updateRequest.getCode(),
                updateRequest.getName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DivisionResponse.PatchedDivision(rsData.getData()));
    }

    // 소속 삭제
    @DeleteMapping("")
    public RsData delete(@Valid @RequestBody DivisionRequest.DeleteRequest deleteRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.divisionService.delete(deleteRequest.getId());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

}
