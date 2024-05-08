package com.app.businessBridge.domain.department.controller;

import com.app.businessBridge.domain.department.entity.Department;
import com.app.businessBridge.domain.department.request.DepartmentRequest;
import com.app.businessBridge.domain.department.response.DepartmentResponse;
import com.app.businessBridge.domain.department.service.DepartmentService;
import com.app.businessBridge.global.RsData.RsCode;
import com.app.businessBridge.global.RsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departments")
public class ApiV1DepartmentController {
    private final DepartmentService departmentService;

    // 부서 등록
    @PostMapping("")
    public RsData post(@Valid @RequestBody DepartmentRequest.CreateRequest createRequest,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.departmentService.create(createRequest.getCode(),
                createRequest.getName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

    // 부서 다건 조회
    @GetMapping("")
    public RsData<DepartmentResponse.GetDepartments> getAll() {
        RsData<List<Department>> rsData = this.departmentService.findAll();

        return RsData.of(rsData.getRsCode(), rsData.getMsg(),
                new DepartmentResponse.GetDepartments(rsData.getData()));
    }

    // 부서 수정
    @PatchMapping("")
    public RsData<DepartmentResponse.PatchedDepartment> patch(@Valid @RequestBody DepartmentRequest.UpdateRequest updateRequest,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData<Department> rsData = this.departmentService.update(updateRequest.getId(), updateRequest.getCode(),
                updateRequest.getName());

        return RsData.of(rsData.getRsCode(), rsData.getMsg(), new DepartmentResponse.PatchedDepartment(rsData.getData()));
    }

    // 부서 삭제
    @DeleteMapping("")
    public RsData delete(@Valid @RequestBody DepartmentRequest.DeleteRequest deleteRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return RsData.of(RsCode.F_10, "알 수 없는 오류로 실패했습니다.");
        }

        RsData rsData = this.departmentService.delete(deleteRequest.getId());

        return RsData.of(rsData.getRsCode(), rsData.getMsg());
    }

}
