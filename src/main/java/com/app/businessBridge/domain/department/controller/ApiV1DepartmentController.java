package com.app.businessBridge.domain.department.controller;

import com.app.businessBridge.domain.department.DTO.DepartmentDTO;
import com.app.businessBridge.domain.department.request.PatchDepartmentRequest;
import com.app.businessBridge.domain.department.request.PostDepartmentRequest;
import com.app.businessBridge.domain.department.response.DepartmentListResponse;
import com.app.businessBridge.domain.department.response.DepartmentResponse;
import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class ApiV1DepartmentController {
    private final DepartmentService departmentService;

    // 부서 다건 조회
    @GetMapping("")
    public RsData<DepartmentListResponse> getAll() {
        RsData<List<DepartmentDTO>> rsData = this.departmentService.readAll();

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DepartmentListResponse(rsData.getData()));
    }

    // 부서 등록
    @PostMapping("")
    public RsData<DepartmentResponse> post(@Valid @RequestBody PostDepartmentRequest postDepartmentRequest) {
        RsData<DepartmentDTO> rsData = this.departmentService.create(postDepartmentRequest.getDepartmentCode(),
                postDepartmentRequest.getDepartmentName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DepartmentResponse(rsData.getData()));
    }

    // 부서 수정
    @PatchMapping("/{id}")
    public RsData<DepartmentResponse> patch(@PathVariable(value = "id") Long id,
                                            @Valid @RequestBody PatchDepartmentRequest patchDepartmentRequest) {
        RsData<DepartmentDTO> rsData = this.departmentService.update(id, patchDepartmentRequest.getDepartmentCode(),
                patchDepartmentRequest.getDepartmentName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DepartmentResponse(rsData.getData()));
    }

    // 부서 삭제
    @DeleteMapping("/{id}")
    public RsData<DepartmentResponse> delete(@PathVariable(value = "id") Long id) {
        RsData<DepartmentDTO> rsData = this.departmentService.delete(id);

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DepartmentResponse(rsData.getData()));
    }
}
